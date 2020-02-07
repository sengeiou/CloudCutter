package com.huansheng.cloudcutter44.ui.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class UrlImageView extends ImageView {
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    public static final int UNKNOW = 4;
    //子线程不能操作UI，通过Handler设置图片
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    setImageBitmap(bitmap);
                    break;
                case NETWORK_ERROR:
                    //Toast.makeText(getContext(),"网络连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    //Toast.makeText(getContext(),"服务器发生错误",Toast.LENGTH_SHORT).show();
                    break;
                case UNKNOW:
                    //Toast.makeText(getContext(),"未知错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public UrlImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UrlImageView(Context context) {
        super(context);
    }

    public UrlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageURL(final String tpath){
        this.setImageURL(tpath,true);
    }

    //设置网络图片
    public void setImageURL(final String tpath, final boolean needstyle) {
        //开启一个线程用于联网
        new Thread() {
            @Override
            public void run() {
                try {
                    //把传过来的路径转成URL
                    Bitmap bitmap=null;
                    String path=tpath+ (needstyle?ApiConfig.photoStyle():"");
                    String encodepath=URLEncoder.encode(path, "utf-8");
                    encodepath=encodepath.replace("%3A",":");
                    encodepath=encodepath.replace("%2F","/");
                    encodepath=encodepath.replace("%3F","?");
                    encodepath=encodepath.replace("%3D","=");
                    encodepath=encodepath.replace("%2C",",");
                    Log.e("UrlImageView",path);
                    Log.e("UrlImageView_encode",encodepath);
                    if(fileIsExists(encodepath)){

                        String sdCardDir = Environment.getExternalStorageDirectory() + "/DCIM/";
                        File appDir = new File(sdCardDir, "HS");
                        String fileName = digest(encodepath) + ".jpg";
                        File f=new File(appDir,fileName);

                        bitmap=BitmapFactory.decodeFile(f.getAbsolutePath());
                        Message msg2 = Message.obtain();
                        msg2.obj = bitmap;
                        msg2.what = GET_DATA_SUCCESS;
                        handler.sendMessage(msg2);
                        Log.e("UrlImageViewload ",f.getAbsolutePath());
                        return;
                    }


                    //Log.e("UrlImageView","success1");
                    URL url = new URL(encodepath);
                    //Log.e("UrlImageView","success2");
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //Log.e("UrlImageView","success3");
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //Log.e("UrlImageView","success4");
                    //超时时间为10秒
                    connection.setConnectTimeout(10000);
                    //Log.e("UrlImageView","success4.1");
                    //获取返回码
                    int code = connection.getResponseCode();
                    //Log.e("UrlImageView","success4.2");
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        //利用Message把图片发给Handler
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        msg.what = GET_DATA_SUCCESS;
                        handler.sendMessage(msg);
                        inputStream.close();
                        saveMyBitmap(encodepath,bitmap);
                        Log.e("UrlImageView","success5");
                    }else {
                        //服务启发生错误
                        Log.e("UrlImageView error",path+"~"+String.valueOf(code));
                        handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    //Log.e("UrlImageView",e.getMessage());
                    e.printStackTrace();
                    //网络连接错误
                    handler.sendEmptyMessage(NETWORK_ERROR);
                } catch (Exception e) {
                    //Log.e("UrlImageView",e.getMessage());
                    e.printStackTrace();
                    //网络连接错误
                    handler.sendEmptyMessage(NETWORK_ERROR);
                }
            }
        }.start();
    }

    public void saveMyBitmap(String path,Bitmap bitmap) {
        String sdCardDir = Environment.getExternalStorageDirectory() + "/DCIM/";
        File appDir = new File(sdCardDir, "HS");
        if (!appDir.exists()) {//不存在
            appDir.mkdirs();
        }
        String fileName = digest(path) + ".jpg";
        File file = new File(appDir, fileName);
        try {
            Log.e("trysave0",file.getAbsolutePath());
            Log.e("trysave01",file.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(file);
            Log.e("trysave1",file.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Log.e("trysave2",file.getAbsolutePath());
            fos.flush();
            Log.e("trysave3",file.getAbsolutePath());
            fos.close();
            Log.e("trysave4",file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            Log.e("trysave5",file.getAbsolutePath());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("trysave6",e.getMessage());

            e.printStackTrace();
        } catch (Exception e) {
            Log.e("trysave7",file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public String digest(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                int c = b & 0xff; //负数转换成正数
                String result = Integer.toHexString(c); //把十进制的数转换成十六进制的书
                if(result.length()<2){
                    sb.append(0); //让十六进制全部都是两位数
                }
                sb.append(result);
            }
            return sb.toString(); //返回加密后的密文
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }


    public boolean fileIsExists(String path)
    {
        String sdCardDir = Environment.getExternalStorageDirectory() + "/DCIM/";
        File appDir = new File(sdCardDir, "HS");
        String fileName = digest(path) + ".jpg";
        try
        {
            File f=new File(appDir,fileName);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
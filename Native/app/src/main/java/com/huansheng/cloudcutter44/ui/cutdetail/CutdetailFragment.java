package com.huansheng.cloudcutter44.ui.cutdetail;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huansheng.cloudcutter44.AboutMachineActivity;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.DeviceApi;
import com.huansheng.cloudcutter44.ApiProviders.FileDownload;
import com.huansheng.cloudcutter44.ApiProviders.InstApi;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.CanshuActivity;
import com.huansheng.cloudcutter44.CutdetailActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.Mgr.DESUtil;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;
import com.huansheng.cloudcutter44.Mgr.Util;
import com.huansheng.cloudcutter44.MyAccountActivity;
import com.huansheng.cloudcutter44.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CutdetailFragment extends Fragment {

    private CutdetailViewModel mViewModel;
    private WebView cutimg;
    private TextView cy_explain;
    private TextView daoyaname;
    private TextView daoya;
    private TextView sudu;
    private Button back;
    private Button cutnow;
    private int isudu=200;
    private int idaoya=320;
    private String filename="";
    private View adduse;
    private int count;
    private String vip;
    private String machinevip;
    private String deviceid="";
    private String machineid="";

    private EditText tiaoshi;

    private AlertDialog loadingDialog;

    String size="19";


    public static CutdetailFragment newInstance() {
        return new CutdetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cutdetail_fragment, container, false);

        this.cutimg=root.findViewById(R.id.cutimg);
        this.cy_explain=root.findViewById(R.id.cy_explain);
        this.back=root.findViewById(R.id.back);
        this.daoyaname=root.findViewById(R.id.daoyaname);
        this.daoya=root.findViewById(R.id.daoya);
        this.sudu=root.findViewById(R.id.sudu);
        this.cutnow=root.findViewById(R.id.cutnow);;
        this.cutnow.setEnabled(false);
        this.tiaoshi=root.findViewById(R.id.tiaoshi);
        this.tiaoshi.setText(size);
        this.tiaoshi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==false){
                    size=tiaoshi.getText().toString();
                    loadModel();
                }
            }
        });

        this.back.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        this.cutnow.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

//                if(machineid.equals("")){
//
//                    AlertDialog alertDialog5 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
//                            .setTitle(R.string.tishi)//标题
//                            .setMessage(R.string.machineidcannotread)//内容
//                            .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    loadMachine();
//
//                                }
//                            })
//                            .create();
//                    alertDialog5.show();
//
//                    return;
//                }
                if(machineid.equals("")==false&&deviceid.equals("")){

                    AlertDialog alertDialog4 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.machinenoregistry)//内容
                            .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    loadMachine();
                                }
                            })
                            .create();
                    alertDialog4.show();

                    return;
                }
                if(isudu==0||idaoya==0){

                    AlertDialog alertDialog2 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.settishi)//内容
                            .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent2=new Intent(CutdetailActivity.Instance, CanshuActivity.class);
                                    startActivity(intent2);
                                }
                            }).setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    
                                }
                            })
                            .create();
                    alertDialog2.show();

                    return;
                }
                if (count <= 0 && !(vip.equals("Y")||machinevip.equals("Y"))) {

                    AlertDialog alertDialog3 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.csbzqcz)//内容
                            .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MyAccountActivity.ShowType=2;
                                    Intent intent2=new Intent(CutdetailActivity.Instance, MyAccountActivity.class);
                                    startActivity(intent2);
                                }
                            }).setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog3.show();

                    return;

                }


                AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                        .setTitle(R.string.tishi)//标题
                        .setMessage(R.string.querenqiege)//内容
                        .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                loadingDialog = Util.getAlertDialog(CutdetailActivity.Instance); //new AlertDialog.Builder(CutdetailActivity.Instance).create();
                                loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
                                loadingDialog.setCancelable(false);
                                loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                                    @Override
                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                        if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
                                            return true;
                                        return false;
                                    }
                                });
                                loadingDialog.show();
                                Util.getFullScreen(loadingDialog);
                                loadingDialog.setContentView(R.layout.loading_alert);
                                loadingDialog.setCanceledOnTouchOutside(false);


                                cut();
                            }
                        }).setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                alertDialog1.show();

            }
        });

        this.adduse=root.findViewById(R.id.adduse);
        this.adduse.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                String id=getActivity().getIntent().getStringExtra("id");
                //account_id: this.memberInfo.id, model_id: model_id, status: 'A'
                MemberApi memberapi=new MemberApi();
                final Map<String,String> json=new HashMap<String, String>();
                json.put("account_id",MainActivity.account_id);
                json.put("model_id",id);
                json.put("status","A");
                memberapi.addcommon(json,new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle data = msg.getData();
                        String val = data.getString("ret");

                        try {
                            JSONObject obj=new JSONObject(val);
                            if(obj.getString("code").equals("0")){
                                Toast.makeText(CutdetailActivity.Instance,R.string.tianjiaok,Toast.LENGTH_LONG).show();
                            }else{
                                AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                                        .setTitle(R.string.tishi)//标题
                                        .setMessage(R.string.ycz)//内容
                                        .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加取消
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .create();
                                alertDialog1.show();
                            }

                        } catch (Exception e) {
                            //
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onstart","y");
        loadMachine();
        loadModel();
        loadMember();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CutdetailViewModel.class);
        // TODO: Use the ViewModel

    }


    private  void loadModel(){

        final CutdetailFragment that=this;

        final String id=getActivity().getIntent().getStringExtra("id");

        PhoneApi phoneapi=new PhoneApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("id",id);
        phoneapi.modelinfo(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    JSONObject obj=new JSONObject(val);
                    Log.e("showimg",ApiConfig.getApiUrl()+"model/showimg?model_id="+id);
                    that.cutimg.loadUrl(ApiConfig.getApiUrl()+"model/showimg?model_id="+id);
                    that.cy_explain.setText(obj.getString("cy_explain"));

                    that.filename=obj.getString("file");

                    cutnow.setEnabled(true);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        });
    }


    private void loadMachine(){
        final CutdetailFragment that=this;
        Cutter cutter=new Cutter();
        cutter.getMachineCode(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                final String fullcode=data.getString("fullcode");
                if(resultcode==0){
                    String machineid=data.getString("machineid");
                    //machineid="30FFD9054E58383306620943";
                    that.machineid=machineid;
                    DeviceApi api=new DeviceApi();
                    final Map<String,String> json=new HashMap<String, String>();
                    json.put("deviceno",machineid);
                    api.info(json,new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle data = msg.getData();
                            String val = data.getString("ret");
                            Log.e("deviceno",val);

                            try {
                                JSONObject obj=new JSONObject(val);

                                that.machinevip=obj.getString("vip_value");
                                that.deviceid=obj.getString("id");
                            } catch (Exception e) {
                                //


//                                AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
//                                        .setTitle("获取不到机器ID时候的返回")//标题
//                                        .setMessage(fullcode)//内容
//                                        .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加取消
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                            }
//                                        })
//                                        .create();
//                                alertDialog1.show();
//                                e.printStackTrace();
                            }
                        }
                    });
                }else{
//                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
//                            .setTitle("获取不到机器ID时候的返回")//标题
//                            .setMessage(fullcode)//内容
//                            .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加取消
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            })
//                            .create();
//                    alertDialog1.show();


                }
            }
        });
    }

    protected void loadMember(){

        final CutdetailFragment that=this;

        MemberApi memberapi=new MemberApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("id", MainActivity.account_id);
        memberapi.accountinfo(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {

                    JSONObject ret=new JSONObject(val);
                    that.daoya.setText(ret.getString("daoya"));

                    Cutter cutter=new Cutter();
                    cutter.setPressure(Integer.parseInt(ret.getString("daoya")),new Handler());

                    that.sudu.setText(ret.getString("sudu"));

                    String checking=ret.getString("checking");
                    if(checking.equals("1")){
                        that.daoyaname.setText(ret.getString("daoyaname1"));
                    }
                    if(checking.equals("2")){
                        that.daoyaname.setText(ret.getString("daoyaname2"));
                    }
                    if(checking.equals("3")){
                        that.daoyaname.setText(ret.getString("daoyaname3"));
                    }
                    if(checking.equals("4")){
                        that.daoyaname.setText(ret.getString("daoyaname4"));
                    }
                    if(checking.equals("5")){
                        that.daoyaname.setText(ret.getString("daoyaname5"));
                    }


                    that.isudu=Integer.parseInt(ret.getString("sudu"));
                    that.idaoya=Integer.parseInt(ret.getString("daoya"));

                    that.count=Integer.parseInt(ret.getString("cutcount"));
                    that.vip=ret.getString("vip_value");
                } catch (Exception e) {
                    //Log.e("accountinfo",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    protected void cut(){

        //第一步检查机器状态
        //第二步设置速度
        //第三步设置刀压
        //第四步下载文件
        //上传文件
         checkingno=0;
         cancheck=false;

         isstart=false;


        Cutter cutter=new Cutter();
        cutter.recovery(new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                getStatus();
            }
        });

    }

    public void closeLoading(){
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
    public  void setLoadingDialogTitle(int title,int prog){
        TextView titletxt=this.loadingDialog.findViewById(R.id.cutstatus);
        titletxt.setText(title);
        //ProgressBar progress=this.loadingDialog.findViewById(R.id.progressBar1);
        //progress.setProgress(prog);
    }
    public  void setLoadingDialogTitle(String title){
        TextView titletxt=this.loadingDialog.findViewById(R.id.cutstatus);
        titletxt.setText(title);
    }
    protected void getStatus(){

        setLoadingDialogTitle(R.string.jianchakelu,0);
        Cutter cutter=new Cutter();
        cutter.getStatus(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                String status=data.getString("status");
                Log.e("resultcode",String.valueOf(resultcode));
                if(resultcode==0&&status.equals("00")){
                    downloadfile();
                }else {
                    Cutter cutter=new Cutter();
                    cutter.recovery(new Handler() {
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle data = msg.getData();

                            closeLoading();

                            AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                                    .setTitle(R.string.tishi)//标题
                                    .setMessage(R.string.machinerecovery)//内容
                                    .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .create();
                            alertDialog1.show();
                        }
                    });

                }
            }
        });
    }

    protected void setSpeed(){
        setLoadingDialogTitle(R.string.setdaosu,2);
        Cutter cutter=new Cutter();
        cutter.setSpeed(isudu,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    setPressure();
                }else {

                    closeLoading();

                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.setspeedfail)//内容
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog1.show();
                }
            }
        });
    }

    protected void setPressure(){
        setLoadingDialogTitle(R.string.setdaoya,3);
        Cutter cutter=new Cutter();
        cutter.setPressure(idaoya,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    downloadfile();
                }else {

                    closeLoading();
                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.setpressurefail)//内容
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog1.show();
                }
            }
        });
    }

    protected void downloadfile(){
        setLoadingDialogTitle(R.string.fsklwj,4);
        final CutdetailFragment that=this;
        String url=ApiConfig.getUploadPath()+"model/"+this.filename;

        FileDownload api=new FileDownload();
        api.download(url,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("downloadfile1",val);
                if(val=="-1"){
                    Log.e("downloadfileerr",val);

                    closeLoading();
                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.downloadfail)//内容
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog1.show();
                }else {

                    if(filename.split("\\.")[1].equals("blt")){
                        try {
                            val=DESUtil.decrypt(val,ApiConfig.encryptionkey);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    upload(val);
                }
            }
        });
    }

    protected void upload(String filecontent){
        final int filecontenttemr=filecontent.length()/1024;

        Cutter cutter=new Cutter();
        cutter.uploadFile(filecontent,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    //Toast.makeText(CutdetailFragment.this.getContext(),R.string.cutting,Toast.LENGTH_LONG).show();
                    String id=getActivity().getIntent().getStringExtra("id");
                    MemberApi memberApi=new MemberApi();
                    Map<String,String> json=new HashMap<String,String>();
                    json.put("account_id",MainActivity.account_id);
                    json.put("model_id",id);
                    json.put("device_id",deviceid);
                    memberApi.consumecount(json,new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle data = msg.getData();
                            String val = data.getString("ret");

                           Log.i("consumecount",val);
                        }
                    });
                    count--;
                    setLoadingDialogTitle(R.string.cutting,5);
                    checkCutting();
                }else {
                    String filein=String.valueOf((data.getInt("down")-1)*100/filecontenttemr);
                    Log.e("SENDFILE percent",filein);
                    String str=getResources().getString(R.string.fsklwj);
                    str=str+" "+filein+"%";
                    setLoadingDialogTitle(str);
//                    closeLoading();
//                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
//                            .setTitle(R.string.tishi)//标题
//                            .setMessage(R.string.cutfail)//内容
//                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            })
//                            .create();
//                    alertDialog1.show();
//
//                    Util.hideBottomMenu(CutdetailActivity.Instance);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancheck=true;
    }

    int checkingno=0;
    boolean cancheck=false;

    boolean isstart=false;
    protected void checkCutting(){
        if(cancheck==true){
            return;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Cutter cutter=new Cutter();
        cutter.getStatus(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                String status=data.getString("status");
                String fullcode=data.getString("fullcode");
                //setLoadingDialogTitle(String.valueOf(checkingno++)+"～"+resultcode+"～"+status);


                Log.e("resultcode",String.valueOf(resultcode));
                if(status.equals("00")){
                    closeLoading();
                }else {
                    checkCutting();
                }
            }
        });
    }


}

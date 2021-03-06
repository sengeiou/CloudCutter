package com.huansheng.cloudcutter44.Mgr;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

public class SerialManager {

    SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    SerialPort mSerialPort;
    private static SerialManager Instance;

    private SerialManager(){
        Log.e("SerialManager","Start");
        mSerialPortFinder = new SerialPortFinder();
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();

        for(int i=0;i<entryValues.length;i++){
            Log.e("SerialManager List all",entryValues[i]);
        }
            try {
                // 打开/dev/ttyUSB0路径设备的串口
                File device=new File("/dev/ttyS3");

                mSerialPort = new SerialPort(device, 38400, 0);
                //read();
                Log.e("SerialManager","StartRead");
            } catch (Exception e) {
                Log.e("SerialManagerError","error");
                e.printStackTrace();
            }
    }

    public static SerialManager GetInstance(){
        Log.e("SerialManager","no?");
        if(SerialManager.Instance==null){
            SerialManager.Instance=new SerialManager();
        }
        return SerialManager.Instance;
    }
    public void write(final int[] arr, final Handler handler){

        this.write(arr,handler,true,0);
    }

    boolean iswriting=false;


    public void write(final int[] arr, final Handler handler, final boolean haveend,final int needwaitsecond){

        if(this.iswriting==true){
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("ret", "000000000000000000000000");
            msg.setData(data);
            handler.sendMessage(msg);
            return;
        }
        this.iswriting=true;
        final String hexstr=getHexStr(arr,haveend);
        Log.e("COMMOND SEND",hexstr);
        final byte[] writedate=FormatUtil.hexString2Bytes(hexstr);


        Log.e("SENDFILE","0");

        Log.e("SENDFILE","1");


        /* 开启一个线程进行读取 */
        new Thread(new Runnable() {
            @Override
            public void run() {

                final int[] readLen = {0};
                // 写入数据
                try {

                    Log.e("SENDFILE","2");

                    OutputStream out = mSerialPort.getOutputStream();
                    InputStream input = mSerialPort.getInputStream();
//                    byte[] clear = new byte[65535];
//                    input.read(clear);
                    mSerialPort.tcflush();
                    try{
                        byte[] abyteArray = new byte[65535];
                        int k=input.read(abyteArray,0,input.available());
                        if(k>0){
                            String str=FormatUtil.bytes2HexString(abyteArray, k);
                            Log.e("COMMOND BEFOREGET","3.8"+str);
                        }
                    }catch (Exception ek){

                    }

                    Log.e("SENDFILE","3");

                    out.write(writedate);
                    out.flush();
                    //out.close();
                    int i=0;
                    StringBuilder sb=new StringBuilder();
                    while (i<10){

                        Log.e("SENDFILE","3.5"+String.valueOf(i));

                        Thread.sleep(300);
                        String str="";
                        byte[] byteArray = new byte[65535];
                            Log.e("SENDFILE","3.6."+String.valueOf(needwaitsecond));

                        readLen[0]=input.read(byteArray);
                            Log.e("SENDFILE","3.7");
                        str=FormatUtil.bytes2HexString(byteArray, readLen[0]);
                            Log.e("SENDFILE","3.8"+str);
                        if(str.length()>0){
                            sb.append(str);
                            break;
                        }
                        i++;
                    }
                    Log.e("SENDFILE","4");

                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("ret", sb.toString());
                    Log.e("COMMOND RECEIVE",sb.toString());
                    msg.setData(data);

                    Log.e("SENDFILE","5");
                    //if(kKret.isreturn==true){
                        handler.sendMessage(msg);
                    //}

                } catch (Exception e) {
                    Log.e("SENDFILE","6");
                    Log.e("SERIALFAIL",hexstr);
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("ret", "");
                    msg.setData(data);
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }

                iswriting=false;
            }
        }).start();
    }

    private String getHexStr(int[] arr,boolean haveend) {

        if(haveend==true){
            int d=0x00;
            for(int i=0;i<arr.length-1;i++){
                //Log.e("xxx0", String.valueOf(arr[i]));
                d+=arr[i];
            }
            d=d&0xff;
            arr[arr.length-1]=d;
        }

        int endo=haveend?4:2;

        int[] command=new int[arr.length+endo];
        command[0]=0x5a;
        command[1]=0xa5;
        for(int i=0;i<arr.length;i++){
            command[i+2]=arr[i];
        }

        if(haveend){
            command[arr.length+2]=0x0d;
            command[arr.length+3]=0x0a;
        }

        StringBuilder sb=new StringBuilder();
        for(int i=0;i<command.length;i++) {

            int ten = command[i];
            String sixteen = String.format("%02x",ten);
            Log.e("xxx1", String.valueOf(ten)+"~"+sixteen);

            sb.append(sixteen);
        }
        Log.e("xxxa",sb.toString());
        return  sb.toString();
    }


}

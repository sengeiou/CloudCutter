package com.huansheng.cloudcutter44.Mgr;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
            } catch (Exception e) {
                Log.e("SerialManager","error");
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

        String hexstr=getHexStr(arr);
        final byte[] writedate=FormatUtil.hexString2Bytes(hexstr);


        /* 开启一个线程进行读取 */
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream out = mSerialPort.getOutputStream();
                InputStream input = mSerialPort.getInputStream();
                final int[] readLen = {0};
                // 写入数据
                try {
                    out.write(writedate);
                    out.flush();
                    //out.close();
                    byte[] byteArray = new byte[2048];
                    readLen[0]=input.read(byteArray);
                    String str=FormatUtil.bytes2HexString(byteArray, readLen[0]);
                    Log.e("xxxb",str);

                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("ret", str);
                    msg.setData(data);
                    handler.sendMessage(msg);

                } catch (IOException e) {
                    Log.e("xxxerr","err" );
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getHexStr(int[] arr) {

        int d=0x00;
        for(int i=0;i<arr.length;i++){
            d+=arr[i];
        }
        arr[5]=d;

        int[] command=new int[arr.length+4];
        command[0]=0x5a;
        command[1]=0xa5;
        for(int i=0;i<arr.length;i++){
            command[i+2]=arr[i];
        }
        command[arr.length+2]=0x0d;
        command[arr.length+3]=0x0a;

        StringBuilder sb=new StringBuilder();
        for(int i=0;i<command.length;i++) {

            int ten = command[i];
            String sixteen = Integer.toHexString(ten);
            if(sixteen.length()==1){
                sixteen="0"+sixteen;
            }
            sb.append(sixteen);
        }
        Log.e("xxxa",sb.toString());
        return  sb.toString();
    }


}

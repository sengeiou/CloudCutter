package com.huansheng.cloudcutter44.Mgr;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;

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
                read();
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

    private StringBuilder readData = new StringBuilder();
    boolean running=true;
    public void read(){
        final InputStream input = mSerialPort.getInputStream();
        final int[] readLen = {0};
        final Lock lock=new ReentrantLock();
        /* 开启一个线程进行读取 */
        new Thread(new Runnable() {
            @Override
            public void run() {

                int readSize = -1;
                while(running) {
                    try {
                        if((readSize = input.available()) <= 0) {  //get the buffer length before read. if you do not, the read will block

                            //System.out.println("Thread" +  threadName + " sleep?");
                            try {
                                Thread.sleep(1);
                            } catch(Exception e) {
                            }
                            continue;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Thread" +   " break exiting..");
                        break;
                    }
                    //readSize=127;
                    System.out.println("readSize:" + readSize);
                    byte[] byteArray = new byte[readSize];
                    try {
                        readLen[0] = input.read(byteArray);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Thread" +  " break exiting..");
                        break;
                    }

                    for(int i=0;i<byteArray.length;i++){
                        System.out.println("~~~~"+String.valueOf(i)+":"+String.valueOf(byteArray[i]));
                    }

                    lock.lock(); // must lock to copy readData
                    System.out.println(".dataModel.");
                    readData.append( FormatUtil.bytes2HexString(byteArray, readLen[0]));

                    lock.unlock();
                    try {Thread.sleep(200);} catch (InterruptedException e){e.printStackTrace();}
                    System.out.println("readstr:" + readData.toString());
                }

                if(running) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread" +   " success exiting..");
            }
        }).start();
    }

    public void write(final int[] arr){

        String hexstr=getHexStr(arr);
        final byte[] writedate=FormatUtil.hexString2Bytes(hexstr);


        /* 开启一个线程进行读取 */
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream out = mSerialPort.getOutputStream();
                // 写入数据
                try {
                    out.write(writedate);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    Log.e("xxxwww","err" );
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

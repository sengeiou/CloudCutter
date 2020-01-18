package com.huansheng.cloudcutter44.Mgr;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class Cutter {

    public void getSpeed(final Handler handler){
        int[] arr=new int[6];
        arr[0]=0xaa;
        arr[1]=0x00;
        arr[2]=0x10;
        arr[3]=0x00;
        arr[4]=0x00;


        SerialManager serialManager=SerialManager.GetInstance();
        serialManager.write(arr,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                int[] ret=FormatUtil.HexStr2DecArray(val);
                int speed=0;
                int resultcode=1;
                try{

                    resultcode=ret[8];
                    if(ret[8]==0&&ret[4]==0x10){
                        speed=Cutter.GetNumber2(ret[9],ret[10]);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                //5aa5aa001004000000c800860d0a

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("speed",speed);
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);


            }
        });
    }


    public void getPressure(final Handler handler){
        int[] arr=new int[6];
        arr[0]=0xaa;
        arr[1]=0x00;
        arr[2]=0x11;
        arr[3]=0x00;
        arr[4]=0x00;


        SerialManager serialManager=SerialManager.GetInstance();
        serialManager.write(arr,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                int[] ret=FormatUtil.HexStr2DecArray(val);
                //5aa5aa0011040000004001000d0a
                int pressure=0;
                int resultcode=1;
                try{

                    resultcode=ret[8];
                    if(ret[8]==0&&ret[4]==0x11){
                        pressure=Cutter.GetNumber2(ret[9],ret[10]);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("pressure",pressure);
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }

    public void tryCut(final Handler handler){
        int[] arr=new int[6];
        arr[0]=0xbb;
        arr[1]=0x00;
        arr[2]=0x17;
        arr[3]=0x00;
        arr[4]=0x00;

        SerialManager serialManager=SerialManager.GetInstance();
        serialManager.write(arr,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                int[] ret=FormatUtil.HexStr2DecArray(val);
                //5aa5aa001004000000c800860d0a
                int resultcode=1;
                try{
                    resultcode=ret[8];
                }catch (Exception ex){
                    ex.printStackTrace();
                }


                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }


    public void setSpeed(int speed,final Handler handler){
        int[] arr=new int[7];
        int[] speedbyt=convertNumber(speed);
        arr[0] = (0xbb);
        arr[1] = (0x00);
        arr[2] = (0x10);
        arr[3] = (0x02);
        arr[4] = (0x00);
        arr[5] = (speedbyt[0]);
        arr[6] = (speedbyt[1]);

        SerialManager serialManager=SerialManager.GetInstance();
        serialManager.write(arr,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                int[] ret=FormatUtil.HexStr2DecArray(val);
                //5aa5aa001004000000c800860d0a
                int resultcode=1;
                try{
                    resultcode=ret[8];
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }


    public void setPressure(int pressure,final Handler handler){
        int[] arr=new int[7];
        int[] pressurebyt=convertNumber(pressure);
        arr[0] = (0xbb);
        arr[1] = (0x00);
        arr[2] = (0x11);
        arr[3] = (0x02);
        arr[4] = (0x00);
        arr[5] = (pressurebyt[0]);
        arr[6] = (pressurebyt[1]);

        SerialManager serialManager=SerialManager.GetInstance();
        serialManager.write(arr,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                int[] ret=FormatUtil.HexStr2DecArray(val);
                //5aa5aa001004000000c800860d0a
                int resultcode=1;
                try{
                    resultcode=ret[8];
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }

    uploadFile(){

    }

    public static int GetNumber2(int d1, int d2) {
        String d2hex=String.format("%02x", d2);
        String d1hex=String.format("%02x", d1);
        return Integer.parseInt(d2hex+d1hex, 16);
    }


    public static int[] convertNumber(int num ) {
        String d1hex=String.format("%04x", num);
        int[] ret=new int[2];
        ret[1]=Integer.parseInt(d1hex.substring(0,1), 16);
        ret[0]=Integer.parseInt(d1hex.substring(2,3), 16);
        return ret;
    }
}

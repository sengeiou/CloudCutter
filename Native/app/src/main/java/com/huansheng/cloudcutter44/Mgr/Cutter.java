package com.huansheng.cloudcutter44.Mgr;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;


public class Cutter {

    public static void Debug(){
        String machinestatusString=FormatUtil.decoderesultcode(1);
        Log.e("machine allstr",machinestatusString);
        String xianwei=machinestatusString.substring(5,6);
        String status=machinestatusString.substring(6,8);

        Log.e("machine xianwei",xianwei);
        Log.e("machine status",status);
    }

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




    public void getGear(final Handler handler){
        int[] arr=new int[6];
        arr[0]=0xaa;
        arr[1]=0x00;
        arr[2]=0x12;
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
                int x=0;
                int y=0;
                int resultcode=1;
                try{

                    resultcode=ret[8];
                    if(ret[8]==0&&ret[4]==0x12){
                        x=Cutter.GetNumber2(ret[9],ret[10]);
                        y=Cutter.GetNumber2(ret[11],ret[12]);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("x",x);
                retdata.putInt("y",y);
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }



    public void getWidth(final Handler handler){
        int[] arr=new int[6];
        arr[0]=0xaa;
        arr[1]=0x00;
        arr[2]=0x19;
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
                int width=0;
                int resultcode=1;
                try{

                    resultcode=ret[8];
                    if(ret[8]==0&&ret[4]==0x19){
                        width=Cutter.GetNumber2(ret[9],ret[10]);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("width",width);
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }



    public void getSpacing(final Handler handler){
        int[] arr=new int[6];
        arr[0]=0xaa;
        arr[1]=0x00;
        arr[2]=0x18;
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
                int spacing=0;
                int resultcode=1;
                try{

                    resultcode=ret[8];
                    if(ret[8]==0&&ret[4]==0x18){
                        spacing=ret[9];
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("spacing",spacing);
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }

    public void getVersion(final Handler handler){
        int[] arr=new int[6];
        arr[0]=0xaa;
        arr[1]=0x00;
        arr[2]=0x21;
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
                String version="";
                int resultcode=1;
                try{

                    resultcode=ret[8];
                    if(ret[8]==0&&ret[4]==0x21){
                        version=Cutter.GetString(Arrays.copyOfRange( ret,9,26));
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putString("version",version);
                retdata.putInt("resultcode",resultcode);
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }

    public void getStatus(final Handler handler){
        int[] arr=new int[6];
        arr[0]=0xaa;
        arr[1]=0x00;
        arr[2]=0x20;
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

                String xianwei="0";
                String status="00";
                int resultcode=1;
                try{
                    resultcode=ret[8];
                    int machinestatus=ret[7];
                    String machinestatusString=FormatUtil.decoderesultcode(machinestatus);
                    Log.e("machine allstr",machinestatusString);
                    xianwei=machinestatusString.substring(5,6);
                    status=machinestatusString.substring(6,8);

                    Log.e("machine xianwei",xianwei);
                    Log.e("machine status",status);

                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putInt("resultcode",resultcode);
                retdata.putString("xianwei",xianwei);
                retdata.putString("status",status);
                retdata.putString("fullcode",val);
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
        int[] arr=new int[8];
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

    public void setGear(int x, int y, final Handler handler) {
        int[] arr=new int[10];
        int[] xbyt=convertNumber(x);
        int[] ybyt=convertNumber(y);
        arr[0] = (0xbb);
        arr[1] = (0x00);
        arr[2] = (0x12);
        arr[3] = (0x04);
        arr[4] = (0x00);
        arr[5] = (xbyt[0]);
        arr[6] = (xbyt[1]);
        arr[7] = (ybyt[0]);
        arr[8] = (ybyt[1]);

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
        int[] arr=new int[8];
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

    public void getMachineCode(final Handler handler){
        int[] arr=new int[6];
        arr[0] = (0xaa);
        arr[1] = (0x00);
        arr[2] = (0x13);
        arr[3] = (0x00);
        arr[4] = (0x00);

        SerialManager serialManager=SerialManager.GetInstance();
        serialManager.write(arr,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                int[] ret=FormatUtil.HexStr2DecArray(val);

                int resultcode=1;
                StringBuilder machinecode=new StringBuilder();
                try{

                    resultcode=ret[8];
                    if(ret[8]==0&&ret[4]==0x13){
                        for(int i=9;i<21;i++){
                            String x=String.format("%02x", ret[i]);
                            machinecode.append(x);
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Message retmsg = new Message();
                Bundle retdata = new Bundle();
                retdata.putString("fullcode",val);
                retdata.putInt("resultcode",resultcode);
                retdata.putString("machineid",machinecode.toString().toUpperCase());
                retmsg.setData(retdata);
                handler.sendMessage(retmsg);
            }
        });
    }

    public void setWidth(int width, final Handler handler) {
        int[] arr=new int[8];
        int[] widthbyt=convertNumber(width);
        arr[0] = (0xbb);
        arr[1] = (0x00);
        arr[2] = (0x19);
        arr[3] = (0x02);
        arr[4] = (0x00);
        arr[5] = (widthbyt[0]);
        arr[6] = (widthbyt[1]);

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
    public void setSpacing(int spacing, final Handler handler) {
        int[] arr=new int[7];
        arr[0] = (0xbb);
        arr[1] = (0x00);
        arr[2] = (0x18);
        arr[3] = (0x01);
        arr[4] = (0x00);
        arr[5] = (spacing);

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

    public void reset(int mode, final Handler handler) {
        int[] arr=new int[7];
        arr[0] = (0xbb);
        arr[1] = (0x00);
        arr[2] = (0x16);
        arr[3] = (0x01);
        arr[4] = (0x00);
        arr[5] = (mode);

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

    public void uploadFile(String filecontent,final Handler handler){
        String filename="test";

        ArrayList<Integer> arr=new ArrayList<Integer>();
        ArrayList<Integer> filenamebyte=Cutter.ConvertString(filename,16);
        int[] filecontentlengthbyte=convertNumber2(filecontent.length());
        arr.add (0xcc);
        arr.add (0x01);
        arr.add (0x30);
        arr.add (0x14);
        arr.add (0x00);
        arr.add (filecontentlengthbyte[0]);
        arr.add (filecontentlengthbyte[1]);
        arr.add (filecontentlengthbyte[2]);
        arr.add (filecontentlengthbyte[3]);
        arr.addAll (filenamebyte);
        arr.add (0x00);

        int[] readarr= Cutter.Arrlist2Arr(arr);

        final int[] filecontentbyte =Cutter.Arrlist2Arr( Cutter.ConvertString(filecontent, filecontent.length()));

        SerialManager serialManager=SerialManager.GetInstance();
        serialManager.write(readarr,new Handler(){
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

                if(resultcode==0){
                    sendfile(0x02,filecontentbyte,handler);
                }else{
                    Message retmsg = new Message();
                    Bundle retdata = new Bundle();
                    retdata.putInt("resultcode",resultcode);
                    retmsg.setData(retdata);
                    handler.sendMessage(retmsg);
                }

            }
        });
    }

    public void sendfile(int ci,int[] filecontentbyte,final Handler handler){
        final int[] orgfilecontentbyte=filecontentbyte;
        if (filecontentbyte.length <= 1024) {
            ci = 0x00;
        }
        int[] filechunlk=Cutter.ArraySlice(filecontentbyte,0,1024);
        Log.e("filechunlk",String.valueOf( filechunlk.length));
        filecontentbyte=Cutter.ArraySlice(filecontentbyte,1024,0);
        Log.e("filechunlk rm",String.valueOf( filecontentbyte.length));

        int[] filechunlkbyt=convertNumber(filechunlk.length);
        ArrayList<Integer> filedata=new ArrayList<Integer>();
        filedata.add(0xcc);
        filedata.add(ci);
        filedata.add(0x30);
        filedata.add(filechunlkbyt[0]);
        filedata.add(filechunlkbyt[1]);
        filedata.addAll(Cutter.Arr2Arrlist(filechunlk));
        filedata.add (0x00);

        int[] readarr= Cutter.Arrlist2Arr(filedata);

        String sendfile=getHexStr(readarr,false);
        Log.e("SENDFILE send",sendfile);

        final int vci=ci;
        final int[] finalfilecontentbyte=filecontentbyte;

        SerialManager serialManager=SerialManager.GetInstance();
        serialManager.write(readarr,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("SENDFILE read",val);

//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

//                int[] ret=FormatUtil.HexStr2DecArray(val);
//                //5aa5aa001004000000c800860d0a
//                int resultcode=1;
//                try{
//                    resultcode=ret[8];
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//                if(resultcode!=0){
//                    Log.e("SENDFILE resend",String.valueOf(vci));
//                    sendfile(vci,orgfilecontentbyte,handler);
//                    return;
//                }
                if(vci==0x00){
                    Message retmsg = new Message();
                    Bundle retdata = new Bundle();
                    retdata.putInt("resultcode",0);
                    retmsg.setData(retdata);
                    handler.sendMessage(retmsg);
                }else{
                    Log.e("SENDFILE INSEND",String.valueOf(vci));
                    Message retmsg = new Message();
                    Bundle retdata = new Bundle();
                    retdata.putInt("resultcode",2);
                    retdata.putInt("down",vci);
                    retmsg.setData(retdata);
                    handler.sendMessage(retmsg);
                    sendfile(vci+1,finalfilecontentbyte,handler);
                }



            }
        },false);

    }

    public static int GetNumber2(int d1, int d2) {
        String d2hex=String.format("%02x", d2);
        String d1hex=String.format("%02x", d1);
        return Integer.parseInt(d2hex+d1hex, 16);
    }


    public static int[] convertNumber(int num ) {
        String d1hex=String.format("%04x", num);
        Log.e("convertNumber 1", String.valueOf(num));
        Log.e("convertNumber 2", d1hex);
        Log.e("convertNumber 3", d1hex.substring(0,2));
        Log.e("convertNumber 4", d1hex.substring(2,4));
        int[] ret=new int[2];
        ret[1]=Integer.parseInt(d1hex.substring(0,2), 16);
        ret[0]=Integer.parseInt(d1hex.substring(2,4), 16);
        Log.e("convertNumber 5", String.valueOf(ret[0]));
        Log.e("convertNumber 6", String.valueOf(ret[1]));
        return ret;
    }

    public static int[] convertNumber2(int num ) {
        String d1hex=String.format("%08x", num);
        int[] ret=new int[4];
        ret[3]=Integer.parseInt(d1hex.substring(0,2), 16);
        ret[2]=Integer.parseInt(d1hex.substring(2,4), 16);
        ret[1]=Integer.parseInt(d1hex.substring(4,6), 16);
        ret[0]=Integer.parseInt(d1hex.substring(6,8), 16);
        return ret;
    }
    public static ArrayList<Integer> ConvertString(String name, int num) {
        ArrayList<Integer> ret =new ArrayList<Integer>();
        char[] namearr=name.toCharArray();
        for (int i = 0; i < num; i++) {
            if (namearr.length > i) {
                ret.add((int)namearr[i]);
            } else {
                ret.add(0x00);
            }
        }
        return ret;
    }
    public  static ArrayList<Integer>  Arr2Arrlist(int[] arr){

        ArrayList<Integer> readarr=new ArrayList<Integer>();
        for(int i=0;i<arr.length;i++){
            readarr.add(arr[i]);
        }
        return readarr;
    }
    public  static int[]  Arrlist2Arr(ArrayList<Integer> arr){

        int[] readarr=new int[arr.size()];
        for(int i=0;i<arr.size();i++){
            readarr[i]=arr.get(i);
        }
        return readarr;
    }

    public static  int[] ArraySlice(int[] arr,int start,int end){
        if(end==0){
            end=arr.length+start;
        }
        if(start>arr.length){
            start=arr.length;
        }
        if(end>arr.length){
            end=arr.length;
        }
        int[] ret=new int[end-start];
        for(int i=0;i<ret.length;i++){
            ret[i]=arr[start+i];
        }
        return ret;
    }



    public static String GetString(int[] data) {
        String ret="";
        for(int i=0;i<data.length;i++){
            ret+=(char)data[i];
        }
        return ret;
    }

    private String getHexStr(int[] arr,boolean haveend) {

        int d=0x00;
        for(int i=0;i<arr.length-1;i++){
            Log.e("xxx0", String.valueOf(arr[i]));
            d+=arr[i];
        }
        d=d&0xff;
        arr[arr.length-1]=d;

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

package com.huansheng.cloudcutter44.Mgr;


import android.util.Log;

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FormatUtil {
    public static byte[] hexString2Bytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }
        //Log.d("hexString2Bytes1",str);

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            //Log.d("hexString2Bytes1",subStr);
            //Log.d("hexString2Bytes2",String.valueOf( Integer.parseInt(subStr, 16)));
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
            //Log.d("hexString2Bytes3",String.valueOf( bytes[i]));
        }

        return bytes;
    }

    public static String bytes2HexString(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    public static String bytes2HexString(byte[] bytes, int len) {
        StringBuilder buf = new StringBuilder(len);
        int i = 0;
        for(byte b : bytes) { // 使用String的format方法进行转换
            i++;
            if(i > len) {
                break;
            }
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    /**
     *
     * @param bRefArr
     * @param offset
     * @param len
     * @param reverse false:低字节
     * @return
     */
    public static int ByteArrayToInt(byte[] bRefArr,int offset,int len, boolean reverse){
        int iOutcome = 0;
        byte bLoop;

        if(reverse){
            for (int i = 0; i < len; i++) {
                bLoop = bRefArr[len+offset-i-1];
                iOutcome += (bLoop & 0xFF) << (8 * i);
            }
        }
        else{ //低字节
            for (int i = 0; i < len; i++) {
                bLoop = bRefArr[offset+i];
                iOutcome += (bLoop & 0xFF) << (8 * i);
            }
        }
        return iOutcome;
    }

    public static int[] HexStr2DecArray(String str)
    {
        try{

            int[] ret = new int[str.length() / 2];
            for(int i = 0; i < str.length() / 2; i++) {
                String subStr = str.substring(i * 2, i * 2 + 2);
                ret[i] = Integer.parseInt(subStr,16);
            }
            return ret;
        }catch (Exception e){
            return  new int[0];
        }
    }


    public static String decoderesultcode(int dec) {

        String hexString = Integer.toBinaryString(dec);
        Log.e("machine hexString",hexString);
        Log.e("machine hexStrlen",String.valueOf(hexString.length()));
        for (int i = 0; i < hexString.length()&&hexString.length()<8; i++) {
            Log.e("machine hexString"+String.valueOf(i),hexString);
            hexString="0"+hexString;
        }
        return hexString;
    }

    public static String URLEncode(String url){
        String encodepath= url;//+ApiConfig.photoStyle();
        try {
            encodepath = URLEncoder.encode(encodepath, "utf-8");
            encodepath=encodepath.replace("%3A",":");
            encodepath=encodepath.replace("%2F","/");
            encodepath=encodepath.replace("%3F","?");
            encodepath=encodepath.replace("%3D","=");
            encodepath=encodepath.replace("%2C",",");
            Log.e("URLEncode",encodepath);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodepath;
    }
}


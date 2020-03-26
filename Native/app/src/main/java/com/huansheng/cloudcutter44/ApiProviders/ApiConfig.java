package com.huansheng.cloudcutter44.ApiProviders;

public class ApiConfig {
    public  static String encryptionkey="abcd1234";
    public static String getApiUrl() {
        return "https://api.hsyunqiemo.com/api/";
    }
    public static String getUploadPath() {
        return "https://oss.hsyunqiemo.com/";
    }
    public static String getFileUploadAPI() {
        return "https://api.hsyunqiemo.com/fileupload";
    }

    public static String getLogUrl() {
        return "https://api.hsyunqiemo.com/Users/logs/";
    }
    public static String photoStyle(){
        return "?x-oss-process=image/resize,p_50/quality,q_100";
    }
    public static String photoStyle2(String size){
        return "?x-oss-process=image/resize,p_"+size+"/quality,q_100";
    }
}

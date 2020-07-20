package com.huansheng.cloudcutter44.ApiProviders;

public class ApiConfig {
    public  static String encryptionkey="abcd1234";
    public static String getApiUrl() {
        return "https://app.mobile-films.com/api/";
    }
    public static String getUploadPath() {
        return "https://mobile-films.oss-accelerate.aliyuncs.com/";
    }
    public static String getFileUploadAPI() {
        return "https://app.mobile-films.com/fileupload";
    }

    public static String getLogUrl() {
        return "https://app.mobile-films.com/users/logs/";
    }
    public static String photoStyle(){
        return "?x-oss-process=image/resize,p_50/quality,q_100";
    }
    public static String photoStyle2(String size){
        return "?x-oss-process=image/resize,p_"+size+"/quality,q_100";
    }
}

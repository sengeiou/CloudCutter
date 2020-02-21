package com.huansheng.cloudcutter44.ApiProviders;

public class ApiConfig {
    public static String getApiUrl() {
        return "https://cmsdev.app-link.org/alucard263096/cloudcutter/api/";
    }
    public static String getUploadPath() {
        return "https://alioss.app-link.org/alucard263096/cloudcutter/";
    }
    public static String getFileUploadAPI() {
        return "https://cmsdev.app-link.org/alucard263096/cloudcutter/fileupload";
    }

    public static String getLogUrl() {
        return "https://cmsdev.app-link.org/Users/alucard263096/cloudcutter/logs/";
    }
    public static String photoStyle(){
        return "?x-oss-process=image/resize,p_50/quality,q_100";
    }
    public static String photoStyle2(){
        return "?x-oss-process=image/resize,p_50/quality,q_100";
    }
}

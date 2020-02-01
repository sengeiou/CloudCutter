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
        return "?x-oss-process=image/auto-orient,1/resize,p_31/quality,q_100";
    }
}

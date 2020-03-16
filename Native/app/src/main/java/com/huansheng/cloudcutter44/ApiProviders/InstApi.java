package com.huansheng.cloudcutter44.ApiProviders;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class InstApi extends ApiBase {

    public void miniappupdate(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"inst/miniappupdate",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void qrcode(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"inst/qrcode",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }

    public String checknetwork() {
        Map<String, String> json=new HashMap<>();
        String ret=submitPostData(ApiConfig.getApiUrl()+"inst/checknetwork",json);
        Log.e("checknetwork",ret);
        return ret;
    }
}

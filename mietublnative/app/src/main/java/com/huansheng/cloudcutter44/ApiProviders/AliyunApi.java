package com.huansheng.cloudcutter44.ApiProviders;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class AliyunApi extends ApiBase {

    public void phoneverifycode(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"aliyun/phoneverifycode",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }

    public void emailverifycode(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"aliyun/emailverifycode",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void emailverifycodes(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"aliyun/emailverifycodes",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void verifycode(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"aliyun/verifycode",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
}

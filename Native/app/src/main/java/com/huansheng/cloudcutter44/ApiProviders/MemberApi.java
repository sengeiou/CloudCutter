package com.huansheng.cloudcutter44.ApiProviders;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Map;

public class MemberApi extends ApiBase {

    public void login(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/login",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void accountinfo(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/accountinfo",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void updates(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/updates",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void setmorendaoya(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/setmorendaoya",json);
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

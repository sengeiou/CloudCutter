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
    public void checkpws(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/checkpws",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void addcommon(final Map<String, String> json, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/addcommon",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }

    public void commonlist(final Map<String, String> json,final  Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/commonlist",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }

    public void deletecommon(final Map<String, String> json,final  Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/deletecommon",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }

    public void cutlist(final Map<String, String> json,final  Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/cutlist",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void buyrecordlist(final Map<String, String> json,final  Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/buyrecordlist",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void rechargelist(final Map<String, String> json,final  Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/rechargelist",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }
    public void genscanlogin(final Map<String, String> json,final  Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/genscanlogin",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };

        new Thread(networkTask).start();
    }

    public void consumecount(final Map<String, String> json,final  Handler handler){
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/consumecount",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };
        new Thread(networkTask).start();
    }

    public String checkscanlogin(final Map<String, String> json) {

        String ret=submitPostData(ApiConfig.getApiUrl()+"member/checkscanlogin",json);
        return ret;
    }
    public void areacodelist(final Map<String, String> json,final  Handler handler){
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/areacodelist",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };
        new Thread(networkTask).start();
    }
    public void checkcanreg(final Map<String, String> json,final  Handler handler){
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/checkcanreg",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };
        new Thread(networkTask).start();
    }
    public void adduser(final Map<String, String> json,final  Handler handler){
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/adduser",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };
        new Thread(networkTask).start();
    }
    public void checkmoileemail(final Map<String, String> json,final  Handler handler){
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/checkmoileemail",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };
        new Thread(networkTask).start();
    }
    public void forgetpwd(final Map<String, String> json,final  Handler handler){
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/forgetpwd",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };
        new Thread(networkTask).start();
    }
    public void reset(final Map<String, String> json,final  Handler handler){
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/reset",json);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("ret", ret);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };
        new Thread(networkTask).start();
    }
    public void allocationrecordlist(final Map<String, String> json,final  Handler handler){
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=submitPostData(ApiConfig.getApiUrl()+"member/allocationrecordlist",json);
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

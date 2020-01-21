package com.huansheng.cloudcutter44.ApiProviders;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

public class FileDownload extends ApiBase {
    public void download(final String url, final Handler handler) {
        Runnable networkTask = new Runnable() {

            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作

                String ret=getData(url);
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

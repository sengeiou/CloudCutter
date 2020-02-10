package com.huansheng.cloudcutter44.ApiProviders;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Map;

public class OrderApi extends ApiBase {

    public String check(final Map<String, String> json) {
        String ret=submitPostData(ApiConfig.getApiUrl()+"order/check",json);
        return ret;
    }
}

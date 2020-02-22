package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.InstApi;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ApiProviders.OrderApi;
import com.huansheng.cloudcutter44.ApiProviders.WechatApi;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanPayActivity extends AppCompatActivity {

    TextView price;
    TextView account_subject;
    SimpleDraweeView paycode;

    String recharge_id;

    OrderCheckThread ordercheck;
    String orderno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_pay);
        setTitle(R.string.nowchong);

        this.price=findViewById(R.id.price);
        this.account_subject=findViewById(R.id.account_subject);
        this.paycode=findViewById(R.id.paycode);


        Intent intent = getIntent();
        price.setText("¥"+intent.getStringExtra("price"));
        account_subject.setText(intent.getStringExtra("account_subject"));
        recharge_id=intent.getStringExtra("recharge_id");

        WechatApi wechatApi = new WechatApi();

        final Map<String, String> json = new HashMap<String, String>();
        json.put("account_id", MainActivity.account_id);
        json.put("account_subject", intent.getStringExtra("account_subject"));
        json.put("recharge_id", recharge_id);
        wechatApi.payqrcode(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {

                    JSONObject obj=new JSONObject(val);
                    final String qrcode=obj.getString("return");
                    orderno=obj.getString("result");
                    Log.e("qrcode",qrcode);
                    //paycode.setImageURL(qrcode,false);

                    final String name=MainActivity.account_id+"_"+String.valueOf(System.currentTimeMillis());


                    InstApi api=new InstApi();
                    Map<String,String> json=new HashMap<String,String>();
                    json.put("str",qrcode);
                    json.put("name",name);
                    api.qrcode(json,new Handler(){
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle data = msg.getData();
                            String url= ApiConfig.getLogUrl()+name+".png";
                            Log.e("qrcode",url);
                            paycode.setImageURI(FormatUtil.URLEncode(url));
                        }
                    });


                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
        ordercheck=new OrderCheckThread();
        (new Thread(ordercheck)).start();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(ordercheck!=null){
                    ordercheck.flag=false;
                }
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class OrderCheckThread implements Runnable{

        boolean flag=false;

        @Override
        public void run() {
            // 处理耗时逻辑
            this.flag=true;
            while (this.flag){

                if(ScanPayActivity.this.orderno==null){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                OrderApi orderApi = new OrderApi();

                final Map<String, String> json = new HashMap<String, String>();
                json.put("orderno", ScanPayActivity.this.orderno);
                String val= orderApi.check(json);
                try {
                    Log.e("ordercheck",val);
                    JSONObject obj=new JSONObject(val);
                    String status=obj.getString("return");
                    if(status.equals("A")){
                        if(flag==true){
                            flag=false;
                            MyAccountActivity.ShowType=0;
                            MyAccountActivity.ShowSUCCESS=1;
                            ScanPayActivity.this.finish();
                        }
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

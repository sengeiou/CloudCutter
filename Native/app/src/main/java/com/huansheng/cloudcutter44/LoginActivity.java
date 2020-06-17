package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.InstApi;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ApiProviders.OrderApi;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;
import com.huansheng.cloudcutter44.Mgr.SerialManager;
import com.huansheng.cloudcutter44.Mgr.UpdateManager;
import com.huansheng.cloudcutter44.Mgr.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login;
    Button register;
    EditText account;
    EditText password;
    TextView forget;
    Spinner areacode;
    public static boolean Show=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.huanyin);
        //;

        this.login=findViewById(R.id.login);
        this.account=findViewById(R.id.account);
        this.password=findViewById(R.id.password);
        this.register=findViewById(R.id.register);
        this.areacode = findViewById(R.id.areacode);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent5=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent5);
            }
        });

        loadAreaCode();

        final LoginActivity that=this;

        forget=findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6=new Intent(LoginActivity.this, ForgetpwdActivity.class);
                startActivity(intent6);
            }
        });


        this.login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("SerialManager","1");
                SerialManager serail=SerialManager.GetInstance();
//                Log.e("SerialManager","2");

                MemberApi memberapi=new MemberApi();
                final Map<String,String> json=new HashMap<String, String>();
                String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
                json.put("quhao", areacode);
                json.put("jiqihao",MainActivity.machineid);
                json.put("account", String.valueOf(that.account.getText()));
                json.put("password", String.valueOf(that.password.getText()));
                json.put("notoken", "1");

                Log.e(MainActivity.machineid,"2999999999");


                memberapi.login(json,new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle data = msg.getData();
                        String val = data.getString("ret");
                        try {
                            JSONObject ret=new JSONObject(val);
//                            Log.e(val,"3000000000000000");
                            if(ret.getString("code").equals("0")){
                                LoginActivity.Show=false;
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("account_id",ret.getString("result"));
                                editor.commit();
                                MainActivity.account_id=ret.getString("result");

                                if(ordercheck!=null){
                                    ordercheck.flag=false;
                                }

                                that.finish();
                            }
                            if (ret.getString("code").equals("2")){
                                Toast.makeText(that, that.getApplication().getString(R.string.sbbd),Toast.LENGTH_LONG  ).show();
                            }
                            if (ret.getString("code").equals("-1")){
                                Toast.makeText(that, that.getApplication().getString(R.string.mimacuo),Toast.LENGTH_LONG  ).show();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        scanlogin=findViewById(R.id.scanlogin);

        MemberApi memberApi=new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        json.put("bianhao",MainActivity.machineid);
        memberApi.genscanlogin(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("genscanlogin",val);
                try {

                    JSONObject obj=new JSONObject(val);
                    code=obj.getString("return");
                    String qrcode="login:"+code;
                    Log.e("qrcode",qrcode);

                    final String name=code;


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
                            scanlogin.setImageURI(FormatUtil.URLEncode(url));
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        UpdateManager mUpdateManager = new UpdateManager(this);
        mUpdateManager.checkUpdateInfo();


        ordercheck=new LoginCheckThread();
        (new Thread(ordercheck)).start();

    }


    SimpleDraweeView scanlogin;
    String code;

    LoginCheckThread ordercheck;


    @Override
    protected void onResume() {
        super.onResume();
        Util.hideBottomMenu(this);
    }

    public void loadAreaCode(){
        MemberApi memberApi = new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        memberApi.areacodelist(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    List<CharSequence> eduList = new ArrayList<CharSequence>();
                    JSONArray list = new JSONArray(val);
                    for (int i = 0; i < list.length(); i++) {
                        eduList.add(list.getJSONObject(i).getString("areacode"));
                    }
                    ArrayAdapter<CharSequence> eduAdapter = new ArrayAdapter<CharSequence>(LoginActivity.this,android.R.layout.simple_spinner_item,eduList);
                    eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areacode.setAdapter(eduAdapter);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public class LoginCheckThread implements Runnable{

        boolean flag=false;

        @Override
        public void run() {
            // 处理耗时逻辑
            this.flag=true;
            while (this.flag){

                if(LoginActivity.this.code==null){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                MemberApi orderApi = new MemberApi();

                final Map<String, String> json = new HashMap<String, String>();
                json.put("code", LoginActivity.this.code);
                String val= orderApi.checkscanlogin(json);
                try {
                    Log.e("scanlogincheck",val);
                    JSONObject obj=new JSONObject(val);
                    String account_id=obj.getString("return");
                    if(!account_id.equals("0")){
                        if(flag==true){
                            flag=false;
                            LoginActivity.Show=false;
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("account_id",account_id);
                            editor.commit();
                            MainActivity.account_id=account_id;
                            LoginActivity.this.finish();
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

    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            //moveTaskToBack(true);
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}

package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.huansheng.cloudcutter44.ApiProviders.AliyunApi;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.Mgr.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TabLayout tabhot;
    private TabItem t0;
    private TabItem t1;
    private View t0v;
    private View t1v;

    View dianhua;
    View dianhua2;
    EditText jxs;
    View youjian;
    View youjian2;
    EditText email;

    Spinner areacode;

    Button sendverifycode;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.register);


        this.dianhua = findViewById(R.id.dianhua);
        this.dianhua2 = findViewById(R.id.dianhua2);
        this.youjian = findViewById(R.id.youjian);
        this.youjian2 = findViewById(R.id.youjian2);
        this.email = findViewById(R.id.email);
        this.jxs = findViewById(R.id.jxs);
        this.areacode = findViewById(R.id.areacode);

        this.tabhot = findViewById(R.id.tabhot);
        this.t0 = findViewById(R.id.t0);
        this.t1 = findViewById(R.id.t1);
        this.t0v = findViewById(R.id.t0v);
        this.t1v = findViewById(R.id.t1v);

        this.tabhot.getTabAt(0).select();
        setTabVisable(0);

        this.tabhot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

//                Toast toast=Toast.makeText(getContext(),"Toast提示消息"+tab.getPosition(),Toast.LENGTH_SHORT    );
//                toast.show();

                setTabVisable(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        loadAreaCode();

        sendverifycode=findViewById(R.id.sendverifycode);
        sendverifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=tabhot.getSelectedTabPosition();
                if(position==0){
                    sendVerify0();
                }else{
                    sendVerify1();
                }
            }
        });
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    public void sendVerify1(){
        final String email=((EditText)findViewById(R.id.email)).getText().toString();
        MemberApi memberApi = new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
        json.put("quhao",areacode);
        json.put("email",email);
        memberApi.checkcanreg(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String val = data.getString("ret");
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {
                        sendemailcode(email);
                    }else{
                        Toast.makeText(RegisterActivity.this, R.string.emailuse,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public void sendVerify0(){
        String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
        final String mobile=((EditText)findViewById(R.id.mobile)).getText().toString();
        final String codemobile=areacode+mobile;
        MemberApi memberApi = new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        json.put("mobile",codemobile);
        memberApi.checkcanreg(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String val = data.getString("ret");
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {
                        sendsmscode(codemobile);
                    }else{
                        Toast.makeText(RegisterActivity.this, R.string.mobileuse,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public void sendemailcode(String email){

        AliyunApi aliyunapi = new AliyunApi();
        final Map<String, String> json = new HashMap<String, String>();
        json.put("email",email);
        json.put("type","register");
        aliyunapi.emailverifycode(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String val = data.getString("ret");
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {
                        senddaoshu();
                        Toast.makeText(RegisterActivity.this, R.string.verifycodesent,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, R.string.sendverifycodefail,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public void sendsmscode(String mobile){

        AliyunApi aliyunapi = new AliyunApi();
        final Map<String, String> json = new HashMap<String, String>();
        String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
        json.put("quhao",areacode);
        json.put("mobile",mobile);
        json.put("type","register");
        aliyunapi.phoneverifycode(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String val = data.getString("ret");
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {
                        senddaoshu();
                        Toast.makeText(RegisterActivity.this, R.string.verifycodesent,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, R.string.sendverifycodefail,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public void senddaoshu(){
        final Button button=findViewById(R.id.sendverifycode);
        button.setEnabled(false);
        button.setBackgroundResource(R.color.gray);

        final Handler handler=new Handler(){
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int val = data.getInt("ret");
                if(val>0){

                    String reminderstr=String.valueOf(val)+ getResources().getString(R.string.sendyanzhengma);
                    button.setText(reminderstr);
                }else{

                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.primary);
                    button.setText(R.string.getyanzhengma);
                }
            }
        };


        new Thread(new Runnable() {
            @Override
            public void run() {

                int count=60;
                while (count>0){
                    try {

                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putInt("ret",count);
                        msg.setData(data);
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count--;
                }

                Message msg = new Message();
                Bundle data = new Bundle();
                data.putInt("ret",count);
                msg.setData(data);
                handler.sendMessage(msg);


            }
        }).start();
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
                    ArrayAdapter<CharSequence> eduAdapter = new ArrayAdapter<CharSequence>(RegisterActivity.this,android.R.layout.simple_spinner_item,eduList);
                    eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areacode.setAdapter(eduAdapter);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTabVisable(int position){
        this.dianhua.setVisibility(position==0?View.VISIBLE:View.GONE);
        this.dianhua2.setVisibility(position==0?View.VISIBLE:View.GONE);

        this.youjian.setVisibility(position==1?View.VISIBLE:View.GONE);
        this.youjian2.setVisibility(position==1?View.VISIBLE:View.GONE);
        this.email.setVisibility(position==1?View.VISIBLE:View.GONE);
    }

    public void addUser(){

        String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
        final String mobile=((EditText)findViewById(R.id.mobile)).getText().toString();
        final String codemobile=areacode+mobile;
        final String email=((EditText)findViewById(R.id.email)).getText().toString();
        final String password=((EditText)findViewById(R.id.password)).getText().toString();
        final String jxs=((EditText)findViewById(R.id.jxs)).getText().toString();
        final String username=((EditText)findViewById(R.id.username)).getText().toString();
        final String verifycode=((EditText)findViewById(R.id.yanzhenma)).getText().toString();

        Log.e("有没有填啊",jxs);
        if (jxs.trim().length()==0){
            Toast.makeText(RegisterActivity.this, R.string.qtjxs,Toast.LENGTH_LONG).show();
            return;
        }

        if(username.trim().length()>0){

            if(password.length()>=6){


                MemberApi memberapi = new MemberApi();
                final Map<String, String> json2 = new HashMap<String, String>();
                json2.put("name",username);
                json2.put("jxs",jxs);
                memberapi.checkcanreg(json2, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Bundle data = msg.getData();
                        String vkk = data.getString("ret");
                        String code="";
                        try {
                            JSONObject ret = new JSONObject(vkk);
                            if (ret.getString("code").equals("0")) {
                                code="0";
                            }
                            else if (ret.getString("code").equals("5")){
                                Toast.makeText(RegisterActivity.this, R.string.jxsbcz,Toast.LENGTH_LONG).show();
                                return;
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, R.string.loginnameisused,Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                        int p=tabhot.getSelectedTabPosition();
                        AliyunApi aliyunapi = new AliyunApi();
                        final Map<String, String> json = new HashMap<String, String>();
                        String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
                        json.put("type","register");
                        json.put("quhao",areacode);
                        json.put("verifycode",verifycode);


                        if(p==0){
                            json.put("mobile",codemobile);
                            aliyunapi.verifycode(json, new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    Bundle data = msg.getData();
                                    String val = data.getString("ret");
                                    try {
                                        JSONObject ret = new JSONObject(val);
                                        if (ret.getString("code").equals("0")) {
                                            realadduser();
                                        }else{

                                            Toast.makeText(RegisterActivity.this, R.string.verifyincorrect,Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else{
                            json.put("email",email);
                            aliyunapi.emailverifycodes(json, new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    Bundle data = msg.getData();
                                    String val = data.getString("ret");
                                    try {
                                        JSONObject ret = new JSONObject(val);
                                        if (ret.getString("code").equals("0")) {

                                            realadduser();
                                        }else{

                                            Toast.makeText(RegisterActivity.this, R.string.verifyincorrect,Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }
                                }
                            });
                        }



                    }
                });



            }else{

                Toast.makeText(RegisterActivity.this, R.string.passwordshort,Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(RegisterActivity.this, R.string.loginnameempty,Toast.LENGTH_LONG).show();
        }


    }

    public  void realadduser(){

        String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
        final String codemobile=((EditText)findViewById(R.id.mobile)).getText().toString();
//        final String codemobile=areacode+mobile;
        final String email=((EditText)findViewById(R.id.email)).getText().toString();
        final String jxs=((EditText)findViewById(R.id.jxs)).getText().toString();
        final String password=((EditText)findViewById(R.id.password)).getText().toString();
        final String username=((EditText)findViewById(R.id.username)).getText().toString();
        final String verifycode=((EditText)findViewById(R.id.yanzhenma)).getText().toString();


        MemberApi memberApi = new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        json.put("email",email);
        json.put("mobile",codemobile);
        json.put("jxs",jxs);
        json.put("name",username);
        json.put("quhao",areacode);
        json.put("password",password);
        json.put("status","A");
        json.put("power","A");
        json.put("jiqihao",MainActivity.machineid);
        memberApi.adduser(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("有没有填啊",jxs);
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {

                        Toast.makeText(RegisterActivity.this, R.string.registrysuccess,Toast.LENGTH_LONG).show();
                        finish();
                    }else{

                        Toast.makeText(RegisterActivity.this, ret.getString("result"),Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

}

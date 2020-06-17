package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgetpwdActivity extends AppCompatActivity {
    TabLayout tabhot;
    private TabItem t0;
    private TabItem t1;
    private View t0v;
    private View t1v;

    View dianhua;
    View dianhua2;
    View youjian;
    EditText email;

    Spinner areacode;

    Button sendverifycode;
    Button next;
    Button done;

    View s1;
    View s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        setTitle(R.string.wangjimima);


        this.s1 = findViewById(R.id.s1);
        this.s2 = findViewById(R.id.s2);
        this.s2.setVisibility(View.GONE);

        this.dianhua = findViewById(R.id.dianhua);
        this.dianhua2 = findViewById(R.id.dianhua2);
        this.youjian = findViewById(R.id.youjian);
        this.email = findViewById(R.id.email);
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
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xiayibu();
            }
        });
        done=findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shezhi();
            }
        });
    }


    public void sendVerify1(){
        final String email=((EditText)findViewById(R.id.email)).getText().toString();
        MemberApi memberApi = new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        json.put("email",email);
        memberApi.checkmoileemail(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String val = data.getString("ret");
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {
                        sendemailcode(email);
                    }else{
                        Toast.makeText(ForgetpwdActivity.this, R.string.emailuse,Toast.LENGTH_LONG).show();
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
        json.put("quhao",areacode);
        json.put("mobile",mobile);
        memberApi.checkmoileemail(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String val = data.getString("ret");
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {
                        sendsmscode(codemobile);
                    }else{
                        Toast.makeText(ForgetpwdActivity.this, R.string.mobilenoreg,Toast.LENGTH_LONG).show();
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
        json.put("type","reset");
        aliyunapi.emailverifycode(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String val = data.getString("ret");
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {
                        //senddaoshu();
                        Toast.makeText(ForgetpwdActivity.this, R.string.verifycodesent,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ForgetpwdActivity.this, R.string.sendverifycodefail,Toast.LENGTH_LONG).show();
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
        json.put("mobile",mobile);
        json.put("type","reset");
        aliyunapi.phoneverifycode(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String val = data.getString("ret");
                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {
                        senddaoshu();
                        Toast.makeText(ForgetpwdActivity.this, R.string.verifycodesent,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ForgetpwdActivity.this, R.string.sendverifycodefail,Toast.LENGTH_LONG).show();
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

    public void setTabVisable(int position){
        this.dianhua.setVisibility(position==0?View.VISIBLE:View.GONE);
        this.dianhua2.setVisibility(position==0?View.VISIBLE:View.GONE);

        this.youjian.setVisibility(position==1?View.VISIBLE:View.GONE);
        this.email.setVisibility(position==1?View.VISIBLE:View.GONE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                    ArrayAdapter<CharSequence> eduAdapter = new ArrayAdapter<CharSequence>(ForgetpwdActivity.this,android.R.layout.simple_spinner_item,eduList);
                    eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areacode.setAdapter(eduAdapter);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public void xiayibu(){

        String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
        final String mobile=((EditText)findViewById(R.id.mobile)).getText().toString();
        final String codemobile=areacode+mobile;
        final String email=((EditText)findViewById(R.id.email)).getText().toString();
        final String verifycode=((EditText)findViewById(R.id.yanzhenma)).getText().toString();

        int p=tabhot.getSelectedTabPosition();
        AliyunApi aliyunapi = new AliyunApi();
        final Map<String, String> json = new HashMap<String, String>();
        json.put("type","reset");
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
                            s1.setVisibility(View.GONE);
                            s2.setVisibility(View.VISIBLE);
                        }else{

                            Toast.makeText(ForgetpwdActivity.this, R.string.verifyincorrect,Toast.LENGTH_LONG).show();
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
                            s1.setVisibility(View.GONE);
                            s2.setVisibility(View.VISIBLE);
                        }else{

                            Toast.makeText(ForgetpwdActivity.this, R.string.verifyincorrect,Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });
        }

    }
    public void shezhi(){
        String areacode=((Spinner)findViewById(R.id.areacode)).getSelectedItem().toString();
        final String mobile=((EditText)findViewById(R.id.mobile)).getText().toString();
        final String codemobile=areacode+mobile;
        final String email=((EditText)findViewById(R.id.email)).getText().toString();
        final String password=((EditText)findViewById(R.id.password)).getText().toString();



        if(password.length()<6){
            Toast.makeText(ForgetpwdActivity.this, R.string.passwordshort,Toast.LENGTH_LONG).show();
        }

        MemberApi memberApi = new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        json.put("password",password);

        int p=tabhot.getSelectedTabPosition();
        if(p==0){
            json.put("quhao",areacode);
            json.put("mobile",mobile);
        }else{
            json.put("email",email);
        }
        memberApi.forgetpwd(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    JSONObject ret = new JSONObject(val);
                    if (ret.getString("code").equals("0")) {

                        Toast.makeText(ForgetpwdActivity.this, R.string.resetsuccess,Toast.LENGTH_LONG).show();
                        finish();
                    }else{

                        Toast.makeText(ForgetpwdActivity.this, ret.getString("result"),Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }
}

package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText account;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.huanyin);

        this.login=findViewById(R.id.login);
        this.account=findViewById(R.id.account);
        this.password=findViewById(R.id.password);

        final LoginActivity that=this;

        this.login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemberApi memberapi=new MemberApi();
                final Map<String,String> json=new HashMap<String, String>();
                json.put("account", String.valueOf(that.account.getText()));
                json.put("password", String.valueOf(that.password.getText()));
                memberapi.login(json,new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle data = msg.getData();
                        String val = data.getString("ret");

                        try {

                            JSONObject ret=new JSONObject(val);
                            if(ret.getString("code").equals("0")){
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("account_id",ret.getString("result"));
                                editor.commit();
                                that.finish();
                            }else{
                                Toast.makeText(that, that.getApplication().getString(R.string.mimacuo),Toast.LENGTH_LONG  ).show();
                            }

                        } catch (Exception e) {
                            Log.e("modellist2",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


    }
}

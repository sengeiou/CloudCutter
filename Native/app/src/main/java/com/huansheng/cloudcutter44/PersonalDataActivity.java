package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ui.mine.MineFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PersonalDataActivity extends AppCompatActivity {

    EditText name;
    EditText mobile;
    EditText email;
    EditText address;
    EditText account;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        this.name=findViewById(R.id.name);
        this.mobile=findViewById(R.id.mobile);
        this.email=findViewById(R.id.email);
        this.address=findViewById(R.id.address);
        this.update=findViewById(R.id.update);
        this.account=findViewById(R.id.account);

        this.update.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name=PersonalDataActivity.this.name.getText().toString();
                String mobile=PersonalDataActivity.this.mobile.getText().toString();
                String email=PersonalDataActivity.this.email.getText().toString();
                String address=PersonalDataActivity.this.address.getText().toString();

                Map<String,String> json=new HashMap<String, String>();
                json.put("id",MainActivity.account_id);
                json.put("name",name);
                json.put("mobile",mobile);
                json.put("email",email);
                json.put("address",address);
                MemberApi api=new MemberApi();
                api.updates(json,new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        PersonalDataActivity.this.finish();
                    }
                });
            }
        });
        loadMember();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void loadMember(){


        MemberApi memberapi=new MemberApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("id", MainActivity.account_id);
        memberapi.accountinfo(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("accountinfo",val);
                try {

                    JSONObject ret=new JSONObject(val);
                    PersonalDataActivity.this.name.setText(ret.getString("name"));
                    PersonalDataActivity.this.mobile.setText(ret.getString("mobile"));
                    PersonalDataActivity.this.email.setText(ret.getString("email"));
                    PersonalDataActivity.this.address.setText(ret.getString("address"));
                    PersonalDataActivity.this.account.setText(ret.getString("account"));

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }
}

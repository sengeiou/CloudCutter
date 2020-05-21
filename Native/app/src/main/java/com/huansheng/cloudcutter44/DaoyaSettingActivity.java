package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.Mgr.Util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DaoyaSettingActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {

    EditText daoyaname1;
    EditText daoyaname2;
    EditText daoyaname3;
    EditText daoyaname4;
    EditText daoyaname5;


    EditText daoya1;
    EditText daoya2;
    EditText daoya3;
    EditText daoya4;
    EditText daoya5;

    EditText focus;

    ImageView check1;
    ImageView check2;
    ImageView check3;
    ImageView check4;
    ImageView check5;

    ImageView nocheck1;
    ImageView nocheck2;
    ImageView nocheck3;
    ImageView nocheck4;
    ImageView nocheck5;

    View t1;
    View tf1;
    View t2;
    View tf2;
    View t3;
    View tf3;
    View t4;
    View tf4;
    View t5;
    View tf5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daoya_setting);
        setTitle(R.string.setdaoya);

        this.daoyaname1=findViewById(R.id.daoyaname1);
        this.daoyaname2=findViewById(R.id.daoyaname2);
        this.daoyaname3=findViewById(R.id.daoyaname3);
        this.daoyaname4=findViewById(R.id.daoyaname4);
        this.daoyaname5=findViewById(R.id.daoyaname5);

        this.daoyaname1.setOnFocusChangeListener(this);
        this.daoyaname2.setOnFocusChangeListener(this);
        this.daoyaname3.setOnFocusChangeListener(this);
        this.daoyaname4.setOnFocusChangeListener(this);
        this.daoyaname5.setOnFocusChangeListener(this);


        this.daoya1=findViewById(R.id.daoya1);
        this.daoya2=findViewById(R.id.daoya2);
        this.daoya3=findViewById(R.id.daoya3);
        this.daoya4=findViewById(R.id.daoya4);
        this.daoya5=findViewById(R.id.daoya5);

        this.daoya1.setOnFocusChangeListener(this);
        this.daoya2.setOnFocusChangeListener(this);
        this.daoya3.setOnFocusChangeListener(this);
        this.daoya4.setOnFocusChangeListener(this);
        this.daoya5.setOnFocusChangeListener(this);

        this.check1=findViewById(R.id.check1);
        this.check2=findViewById(R.id.check2);
        this.check3=findViewById(R.id.check3);
        this.check4=findViewById(R.id.check4);
        this.check5=findViewById(R.id.check5);

        this.nocheck1=findViewById(R.id.nocheck1);
        this.nocheck2=findViewById(R.id.nocheck2);
        this.nocheck3=findViewById(R.id.nocheck3);
        this.nocheck4=findViewById(R.id.nocheck4);
        this.nocheck5=findViewById(R.id.nocheck5);

        this.check1.setOnClickListener(this);
        this.check2.setOnClickListener(this);
        this.check3.setOnClickListener(this);
        this.check4.setOnClickListener(this);
        this.check5.setOnClickListener(this);
        this.nocheck1.setOnClickListener(this);
        this.nocheck2.setOnClickListener(this);
        this.nocheck3.setOnClickListener(this);
        this.nocheck4.setOnClickListener(this);
        this.nocheck5.setOnClickListener(this);



        this.t1=findViewById(R.id.t1);
        this.tf1=findViewById(R.id.tf1);
        this.t2=findViewById(R.id.t2);
        this.tf2=findViewById(R.id.tf2);
        this.t3=findViewById(R.id.t3);
        this.tf3=findViewById(R.id.tf3);
        this.t4=findViewById(R.id.t4);
        this.tf4=findViewById(R.id.tf4);
        this.t5=findViewById(R.id.t5);
        this.tf5=findViewById(R.id.tf5);
        this.t1.setOnClickListener(this);
        this.tf1.setOnClickListener(this);
        this.t2.setOnClickListener(this);
        this.tf2.setOnClickListener(this);
        this.t3.setOnClickListener(this);
        this.tf3.setOnClickListener(this);
        this.t4.setOnClickListener(this);
        this.tf4.setOnClickListener(this);
        this.t5.setOnClickListener(this);
        this.tf5.setOnClickListener(this);


        this.focus=findViewById(R.id.focus);

        loadMember();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.focus.requestFocus();
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.hideBottomMenu(this);
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
                    DaoyaSettingActivity.this.daoyaname1.setText(ret.getString("daoyaname1"));
                    DaoyaSettingActivity.this.daoyaname2.setText(ret.getString("daoyaname2"));
                    DaoyaSettingActivity.this.daoyaname3.setText(ret.getString("daoyaname3"));
                    DaoyaSettingActivity.this.daoyaname4.setText(ret.getString("daoyaname4"));
                    DaoyaSettingActivity.this.daoyaname5.setText(ret.getString("daoyaname5"));

                    DaoyaSettingActivity.this.daoya1.setText(ret.getString("daoya1"));
                    DaoyaSettingActivity.this.daoya2.setText(ret.getString("daoya2"));
                    DaoyaSettingActivity.this.daoya3.setText(ret.getString("daoya3"));
                    DaoyaSettingActivity.this.daoya4.setText(ret.getString("daoya4"));
                    DaoyaSettingActivity.this.daoya5.setText(ret.getString("daoya5"));

                    makeCheck(Integer.parseInt(ret.getString("checking")));

                } catch (Exception e) {

                    Log.e("makeCheck","err");
                    e.printStackTrace();
                }
            }
        });
    }

    public void makeCheck(int check){

        Log.e("makeCheck",String.valueOf(check));
        this.check1.setVisibility(check==1?View.VISIBLE:View.GONE);
        this.nocheck1.setVisibility(check!=1?View.VISIBLE:View.GONE);

        this.check2.setVisibility(check==2?View.VISIBLE:View.GONE);
        this.nocheck2.setVisibility(check!=2?View.VISIBLE:View.GONE);

        this.check3.setVisibility(check==3?View.VISIBLE:View.GONE);
        this.nocheck3.setVisibility(check!=3?View.VISIBLE:View.GONE);

        this.check4.setVisibility(check==4?View.VISIBLE:View.GONE);
        this.nocheck4.setVisibility(check!=4?View.VISIBLE:View.GONE);

        this.check5.setVisibility(check==5?View.VISIBLE:View.GONE);
        this.nocheck5.setVisibility(check!=5?View.VISIBLE:View.GONE);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        try{

            if(b==true){
                return;
            }
            MemberApi api=new MemberApi();
            Map<String,String> json=new HashMap<String,String>();
            json.put("id",MainActivity.account_id);
            EditText editText=findViewById(view.getId());
            String val=editText.getText().toString();
            // Log.e("auauauu",val);



            if(view.getId()==R.id.daoyaname1){
                json.put("type","K");
                json.put("daoya",val);
                json.put("fenlei","1");
                api.setmorendaoya(json,new Handler(){
                });
            }


            if(view.getId()==R.id.daoyaname2){
                json.put("type","K");
                json.put("daoya",val);
                json.put("fenlei","2");
                api.setmorendaoya(json,new Handler(){

                });
            }
            if(view.getId()==R.id.daoyaname3){
                json.put("type","K");
                json.put("daoya",val);
                json.put("fenlei","3");
                api.setmorendaoya(json,new Handler(){

                });
            }
            if(view.getId()==R.id.daoyaname4){
                json.put("type","K");
                json.put("daoya",val);
                json.put("fenlei","4");
                api.setmorendaoya(json,new Handler(){

                });
            }
            if(view.getId()==R.id.daoyaname5){
                json.put("type","K");
                json.put("daoya",val);
                json.put("fenlei","5");
                api.setmorendaoya(json,new Handler(){

                });
            }

            int ival=Integer.parseInt(val);
            if(ival>500){
                val="500";
                ival=500;
                editText.setText(val);
            }

            if(view.getId()==R.id.daoya1){
                json.put("type","Y");
                json.put("daoya",val);
                json.put("fenlei","1");
                api.setmorendaoya(json,new Handler(){

                });
            }
            if(view.getId()==R.id.daoya2){
                json.put("type","Y");
                json.put("daoya",val);
                json.put("fenlei","2");
                api.setmorendaoya(json,new Handler(){

                });
            }
            if(view.getId()==R.id.daoya3){
                json.put("type","Y");
                json.put("daoya",val);
                json.put("fenlei","3");
                api.setmorendaoya(json,new Handler(){

                });
            }
            if(view.getId()==R.id.daoya4){
                json.put("type","Y");
                json.put("daoya",val);
                json.put("fenlei","4");
                api.setmorendaoya(json,new Handler(){

                });
            }
            if(view.getId()==R.id.daoya5){
                json.put("type","Y");
                json.put("daoya",val);
                json.put("fenlei","5");
                api.setmorendaoya(json,new Handler(){

                });
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        try {

            Log.e("onClick","~");
            MemberApi api=new MemberApi();
            Map<String,String> json=new HashMap<String,String>();
            json.put("id",MainActivity.account_id);
            if(view.getId()==R.id.nocheck1||view.getId()==R.id.t1||view.getId()==R.id.tf1){
                //daoya: num,
                //      checking: idx
                json.put("daoya",this.daoya1.getText().toString());
                json.put("checking","1");
                api.setmorendaoya(json,new Handler(){

                });
                makeCheck(1);
            }
            if(view.getId()==R.id.nocheck2||view.getId()==R.id.t2||view.getId()==R.id.tf2){
                json.put("daoya",this.daoya2.getText().toString());
                json.put("checking","2");
                api.setmorendaoya(json,new Handler(){

                });
                makeCheck(2);
            }
            if(view.getId()==R.id.nocheck3||view.getId()==R.id.t3||view.getId()==R.id.tf3){
                json.put("daoya",this.daoya3.getText().toString());
                json.put("checking","3");
                api.setmorendaoya(json,new Handler(){

                });
                makeCheck(3);
            }
            if(view.getId()==R.id.nocheck4||view.getId()==R.id.t4||view.getId()==R.id.tf4){
                json.put("daoya",this.daoya4.getText().toString());
                json.put("checking","4");
                api.setmorendaoya(json,new Handler(){

                });
                makeCheck(4);
            }
            if(view.getId()==R.id.nocheck5||view.getId()==R.id.t5||view.getId()==R.id.tf5){
                json.put("daoya",this.daoya5.getText().toString());
                json.put("checking","5");
                api.setmorendaoya(json,new Handler(){

                });
                makeCheck(5);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

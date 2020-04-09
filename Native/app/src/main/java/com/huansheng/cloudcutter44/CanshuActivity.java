package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.Mgr.Util;
import com.huansheng.cloudcutter44.ui.cutdetail.CutdetailFragment;
import com.huansheng.cloudcutter44.ui.home.HomeFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CanshuActivity extends AppCompatActivity {

    TextView daoyaname;
    TextView daoya;
    View setdaoya;
    View xitongcanshu;

    EditText sudu;

    boolean loaded=false;
    EditText focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canshu);

        setTitle(R.string.setcanshu);

        this.daoyaname=findViewById(R.id.daoyaname);
        this.daoya=findViewById(R.id.daoya);
        TextView setsudu=findViewById(R.id.setsudu);
        setsudu.setText(this.getString(R.string.setsudu)+"(0-800，"+this.getString(R.string.moren)+"200)");


        this.sudu=findViewById(R.id.sudu);
        this.sudu.setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                try{

                    if(b==true){
                        return;
                    }
                    final int ival=Integer.parseInt(CanshuActivity.this.sudu.getText().toString());
                    if(0<ival&&ival<=800){
                        MemberApi memberapi=new MemberApi();
                        final Map<String,String> json=new HashMap<String, String>();
                        json.put("type", "Q");
                        json.put("id", MainActivity.account_id);
                        json.put("sudu", String.valueOf(ival));
                        memberapi.setmorendaoya(json,new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                Bundle data = msg.getData();
                                String val = data.getString("ret");
                                Log.e("setmorendaoya",val);
                                Cutter cutter=new Cutter();
                                cutter.setSpeed(ival,new Handler());
                            }
                        });
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });



        //loadMember();
        //getGear();
        this.setdaoya=findViewById(R.id.setdaoya);
        this.setdaoya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focus.requestFocus();
                Intent intent=new Intent(CanshuActivity.this, DaoyaSettingActivity.class);
                //执行意图  
                startActivity(intent);
            }
        });
        this.focus=findViewById(R.id.focus);



        this.xitongcanshu=findViewById(R.id.xitongcanshu);
        this.xitongcanshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focus.requestFocus();

                final EditText inputServer = new EditText(CanshuActivity.this);
                inputServer.setInputType(129);
                inputServer.setHint(R.string.shumima);
                inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                //inputServer.setBackgroundResource(R.drawable.shape_corner);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//                lp.setMargins(120, 120, 140, 0);
//                inputServer.setLayoutParams(lp);


                AlertDialog.Builder builder = new AlertDialog.Builder(CanshuActivity.this);
                builder.setTitle(R.string.szcsyzmm).setView(inputServer)
                        .setNegativeButton(R.string.quxiao, null);
                builder.setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password=inputServer.getText().toString();

                        MemberApi memberapi=new MemberApi();
                        final Map<String,String> json=new HashMap<String, String>();
                        json.put("account_id", MainActivity.account_id);
                        json.put("password", password);
                        memberapi.checkpws(json,new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                Bundle data = msg.getData();
                                String val = data.getString("ret");

                                try {

                                    JSONObject ret=new JSONObject(val);
                                    if(ret.getString("code").equals("0")){
                                        Intent intent2=new Intent(CanshuActivity.this, CanshuMoreActivity.class);
                                        startActivity(intent2);
                                    }else{
                                        Toast.makeText(CanshuActivity.this, MainActivity.Instance.getApplication().getString(R.string.mimacuo),Toast.LENGTH_LONG  ).show();
                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }
                        });


                    }
                });
                builder.show();
            }
        });


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
    protected void onStart() {
        super.onStart();
        loadMember();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadMember();
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
                    String checking=ret.getString("checking");
                    if(checking.equals("1")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname1"));
                    }else if(checking.equals("2")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname2"));
                    }else if(checking.equals("3")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname3"));

                    }else if(checking.equals("4")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname4"));

                    }else if(checking.equals("5")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname5"));

                    }
                    CanshuActivity.this.daoya.setText(ret.getString("daoya"));
                    CanshuActivity.this.sudu.setText(ret.getString("sudu"));

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }


}

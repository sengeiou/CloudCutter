package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

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

    EditText sudu;
    EditText x;
    EditText y;

    EditText width;
    Switch spacing;
    TextView banbenhao;
    boolean loaded=false;
    View reset;
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

        TextView namewidth=findViewById(R.id.namewidth);
        namewidth.setText(this.getString(R.string.fukuan)+"(0-1300mm)");//"+this.getString(R.string.moren)+"190mm)


        TextView namegearx=findViewById(R.id.namegearx);
        namegearx.setText(this.getString(R.string.chulunbi)+" X");

        TextView namegeary=findViewById(R.id.namegeary);
        namegeary.setText(this.getString(R.string.chulunbi)+" Y");

        this.sudu=findViewById(R.id.sudu);
        this.sudu.setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                try{

                    if(b==true){
                        return;
                    }
                    int ival=Integer.parseInt(CanshuActivity.this.sudu.getText().toString());
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

                            }
                        });
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        this.x=findViewById(R.id.x);
        this.y=findViewById(R.id.y);
        this.x.setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                try{
                    if(b==true){
                        return;
                    }
                    int x=Integer.parseInt(CanshuActivity.this.x.getText().toString());
                    int y=Integer.parseInt(CanshuActivity.this.y.getText().toString());
                    if(0<x&&x<=4000&&0<y&&y<=4000){
                        Cutter cutter=new Cutter();
                        cutter.setGear(x,y,new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                            }
                        });
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        this.y.setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                try{
                    if(b==true){
                        return;
                    }
                    int x=Integer.parseInt(CanshuActivity.this.x.getText().toString());
                    int y=Integer.parseInt(CanshuActivity.this.y.getText().toString());
                    if(0<x&&x<=4000&&0<y&&y<=4000){
                        Cutter cutter=new Cutter();
                        cutter.setGear(x,y,new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                            }
                        });
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        this.width=findViewById(R.id.width);
        this.width.setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                try{
                    if(b==true){
                        return;
                    }
                    int width=Integer.parseInt(CanshuActivity.this.width.getText().toString());
                    if(0<width&&width<=800){
                        Cutter cutter=new Cutter();
                        cutter.setWidth(width,new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                            }
                        });
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        this.spacing=findViewById(R.id.spacing);
        this.spacing.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(loaded==true){
                        Cutter cutter=new Cutter();
                        cutter.setSpacing(b?1:0,new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                            }
                        });
                    }
            }
        });
        this.banbenhao=findViewById(R.id.banbenhao);
        this.reset=findViewById(R.id.reset);
        this.reset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                AlertDialog alertDialog1 = new AlertDialog.Builder(CanshuActivity.this)
                        .setTitle(R.string.tishi)//标题
                        .setMessage(R.string.querencz)//内容
                        .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Cutter cutter=new Cutter();
                                cutter.reset(2,new Handler() {
                                    @Override
                                    public void handleMessage(Message msg) {
                                        Bundle data = msg.getData();
                                        int resultcode=data.getInt("resultcode");
                                        if(resultcode==0){
                                            getGear();
                                        }
                                    }
                                });
                            }
                        }).setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                alertDialog1.show();



            }
        });

        //loadMember();
        //getGear();
        this.setdaoya=findViewById(R.id.setdaoya);
        this.setdaoya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CanshuActivity.this, DaoyaSettingActivity.class);
                //执行意图  
                startActivity(intent);
            }
        });
        this.focus=findViewById(R.id.focus);


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
        super.onRestart();
        loadMember();
        getGear();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadMember();
        getGear();
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

    private void getGear(){
        Cutter cutter=new Cutter();
        cutter.getGear(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int x=data.getInt("x");
                int y=data.getInt("y");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    CanshuActivity.this.x.setText(String.valueOf( x));
                    CanshuActivity.this.y.setText(String.valueOf( y));
                }else{
                    CanshuActivity.this.x.setText("-");
                    CanshuActivity.this.y.setText("-");
                }
                getWidth();
            }
        });
    }
    private void getWidth(){
        Cutter cutter=new Cutter();
        cutter.getWidth(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int width=data.getInt("width");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    CanshuActivity.this.width.setText(String.valueOf( width));
                }else{
                    CanshuActivity.this.width.setText("-");
                }
                getSpacing();
            }
        });
    }
    private void getSpacing(){
        Cutter cutter=new Cutter();
        cutter.getSpacing(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int spacing=data.getInt("spacing");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    CanshuActivity.this.spacing.setChecked(spacing==1);
                }
                loaded=true;
                getVersion();
            }
        });
    }
    private void getVersion(){
        Cutter cutter=new Cutter();
        cutter.getVersion(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String version=data.getString("version");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    CanshuActivity.this.banbenhao.setText(( version));
                }
            }
        });
    }

}

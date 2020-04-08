package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CanshuMoreActivity extends AppCompatActivity {


    EditText x;
    EditText y;

    EditText width;
    Switch spacing;
    boolean loaded=false;
    View reset;
    EditText focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canshu_more);

        setTitle(R.string.xitong);



        TextView namewidth=findViewById(R.id.namewidth);
        namewidth.setText(this.getString(R.string.fukuan)+"(0-1300mm)");//"+this.getString(R.string.moren)+"190mm)


        TextView namegearx=findViewById(R.id.namegearx);
        namegearx.setText(this.getString(R.string.chulunbi)+" X");

        TextView namegeary=findViewById(R.id.namegeary);
        namegeary.setText(this.getString(R.string.chulunbi)+" Y");



        this.x=findViewById(R.id.x);
        this.y=findViewById(R.id.y);
        this.x.setOnFocusChangeListener(new EditText.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                try{
                    if(b==true){
                        return;
                    }
                    int x=Integer.parseInt(CanshuMoreActivity.this.x.getText().toString());
                    int y=Integer.parseInt(CanshuMoreActivity.this.y.getText().toString());
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
                    int x=Integer.parseInt(CanshuMoreActivity.this.x.getText().toString());
                    int y=Integer.parseInt(CanshuMoreActivity.this.y.getText().toString());
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
                    int width=Integer.parseInt(CanshuMoreActivity.this.width.getText().toString());
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
        this.reset=findViewById(R.id.reset);
        this.reset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                AlertDialog alertDialog1 = new AlertDialog.Builder(CanshuMoreActivity.this)
                        .setTitle(R.string.tishi)//标题
                        .setMessage(R.string.querencz)//内容
                        .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Cutter cutter=new Cutter();
                                cutter.reset(1,new Handler() {
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
        super.onStart();
        getGear();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getGear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.hideBottomMenu(this);
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
                    CanshuMoreActivity.this.x.setText(String.valueOf( x));
                    CanshuMoreActivity.this.y.setText(String.valueOf( y));
                }else{
                    CanshuMoreActivity.this.x.setText("-");
                    CanshuMoreActivity.this.y.setText("-");
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
                    CanshuMoreActivity.this.width.setText(String.valueOf( width));
                }else{
                    CanshuMoreActivity.this.width.setText("-");
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
                    CanshuMoreActivity.this.spacing.setChecked(spacing==1);
                }
                loaded=true;
            }
        });
    }

}

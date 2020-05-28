package com.huansheng.cloudcutter44;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huansheng.cloudcutter44.ApiProviders.DeviceApi;
import com.huansheng.cloudcutter44.ApiProviders.InstApi;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;
import com.huansheng.cloudcutter44.Mgr.SerialManager;
import com.huansheng.cloudcutter44.Mgr.UpdateManager;
import com.huansheng.cloudcutter44.Mgr.Util;
import com.huansheng.cloudcutter44.ui.cutdetail.CutdetailFragment;

import org.json.JSONObject;

import java.io.Console;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    public  static MainActivity Instance;

    public static String account_id="0";
    public static String lastlang="";

    public static String machineid="";


    private UpdateManager mUpdateManager;

    public static String LangCode="chn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        int color=Color.parseColor("#ffffff");
        Log.e("firstlang","aaa");
        resetLanguage();
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); //bellow setSupportActionBar(toolbar);
        //getSupportActionBar().setCustomView(R.layout.titlebar);

        super.onCreate(savedInstanceState);

        //Cutter.Debug();

        Fresco.initialize(this);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_product,R.id.navigation_chart, R.id.navigation_mine)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //this.checkLogin();

        SerialManager serialManager=SerialManager.GetInstance();


        MainActivity.Instance=this;


        //resetLanguage();
        //this.checkLogin();
        this.checkWifi();
    }


    protected void loginCheck(){

        final Handler handler=new Handler(){
            public void handleMessage(Message msg) {

                loadMachine();
            }
        };



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Util.isWifiConnected(MainActivity.Instance)==false){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("nodata3","inloop");
                }


                InstApi instApi=new InstApi();
                while(instApi.checknetwork().equals("1")==false){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("nodata4","inloop");
                }

                Message msg = new Message();
                Bundle data = new Bundle();
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }).start();

    }



    protected void checkWifi(){


        final Handler handler=new Handler(){
            public void handleMessage(Message msg) {
                if(Util.isWifiConnected(MainActivity.this)==false){
                    AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.wifinoconnect)//内容
                            .setPositiveButton(R.string.checknetwork, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Util.GotoWifiSetting(MainActivity.this);
                                }
                            })
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(Util.isWifiConnected(MainActivity.Instance)==false){
                                        loadMachine();
                                    }
                                }
                            })
                            .create();
                    alertDialog1.show();
                }else{
                   //loadMachine();
                }
            }
        };



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message msg = new Message();
                Bundle data = new Bundle();
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }).start();

    }


    protected void resetLanguage(){
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
            Log.e("Locale","1");
        } else {
            locale = getResources().getConfiguration().locale;
            Log.e("Locale","2");
        }
//或者仅仅使用 locale = Locale.getDefault(); 不需要考虑接口 deprecated(弃用)问题
        String lang = locale.getLanguage();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        lang=prefs.getString("lang",lang);

        Log.e("firstlang",lang);

        if(lastlang.equals(lang)){
            return;
        }
        lastlang=lang;


        Log.e("firstlanglangkk",lang);
        Log.e("firstlanglastlangkk",lastlang);


        Locale locale1=Locale.CHINESE;
        if(lang.equals("en")){
            MainActivity.LangCode="eng";
            locale1=Locale.ENGLISH;
        }else if(lang.equals("fr")){
            MainActivity.LangCode="fra";
            locale1=Locale.FRANCE;
        }else if(lang.equals("es")){
            MainActivity.LangCode="esp";
            locale1=new Locale("ES");
        }else if(lang.equals("pt")){
            MainActivity.LangCode="por";
            locale1=new Locale("PT");
            //locale1=Locale.
        }else if(lang.equals("de")){
            MainActivity.LangCode="deu";
            locale1=Locale.GERMAN;
        }else if(lang.equals("py")){
            MainActivity.LangCode="py";
            locale1=new Locale("RU");
        }else{
            MainActivity.LangCode="chn";
            locale1=Locale.CHINESE;
        }


        Configuration configuration = this.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale1);
        } else {
            configuration.locale = locale1;
        }
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.getResources().updateConfiguration(configuration, displayMetrics);
        //this.recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.hideBottomMenu(this);
        this.loginCheck();
    }




    private void loadMachine(){

        Log.e("loadMachine",MainActivity.machineid+"_"+MainActivity.account_id);

        if(MainActivity.machineid.equals("")==false
        &&MainActivity.account_id.equals("0")==false
        ){
            return;
        }

        Cutter cutter=new Cutter();
        cutter.getMachineCode(new Handler(){
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                final String fullcode=data.getString("fullcode");
                if(resultcode==0){//1==1||
                    String machineid=data.getString("machineid");
                    if(machineid.equals("")){
                        return;
                    }
                    MainActivity.machineid=machineid;
                    //machineid="34FFD8054E58383209670444";
                    DeviceApi api=new DeviceApi();
                    final Map<String,String> json=new HashMap<String, String>();
                    json.put("deviceno",machineid);
                    api.login(json,new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle data = msg.getData();
                            String val = data.getString("ret");

                            try {
                                JSONObject obj=new JSONObject(val);
                                String code=obj.getString("code");
                                String account_id=obj.getString("return");
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                                if(code.equals("0")){

                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("account_id",account_id);
                                    editor.putString("isdevice","1");
                                    editor.commit();
                                }else {
                                    String isdevice=prefs.getString("isdevice","0");
                                    if(isdevice.equals("1")){
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("account_id","0");
                                        editor.putString("isdevice","0");
                                        editor.commit();
                                    }
                                }
                                checkLogin();

                            } catch (Exception e) {
                                //
                                AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(R.string.tishi)//标题
                                        .setMessage(R.string.wifinoconnect)//内容
                                        .setNegativeButton(R.string.qr, new DialogInterface.OnClickListener() {//添加取消
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //loadMachine();
                                            }
                                        })
                                        .create();
                                alertDialog1.show();
                            }
                        }
                    });
                }else{
                    //checkLogin();

                    AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.machineidcannotread)//内容
                            .setNegativeButton(R.string.qr, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    loadMachine();
                                }
                            })
                            .create();
                    alertDialog1.show();

                }
            }
        });
    }


    protected void checkLogin(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        String account_id=prefs.getString("account_id","0");
        Log.e("account_id",account_id);
        if(account_id.equals("0")){
            if(LoginActivity.Show==false){



                Log.e("checkLogin 2",MainActivity.machineid+"_"+MainActivity.account_id);
                Intent intent=new Intent(this, LoginActivity.class);
                //执行意图  
                startActivity(intent);
                LoginActivity.Show=true;
            }
        }else{
            MainActivity.account_id=account_id;
        }
    }


}

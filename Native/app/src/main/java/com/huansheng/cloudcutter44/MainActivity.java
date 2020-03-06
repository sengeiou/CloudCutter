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
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;
import com.huansheng.cloudcutter44.Mgr.SerialManager;
import com.huansheng.cloudcutter44.Mgr.UpdateManager;
import com.huansheng.cloudcutter44.Mgr.Util;
import com.huansheng.cloudcutter44.ui.cutdetail.CutdetailFragment;

import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    public  static MainActivity Instance;

    public static String account_id="0";
    public static String lastlang="";


    private UpdateManager mUpdateManager;

    public static String LangCode="chn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        int color=Color.parseColor("#ffffff");

        resetLanguage();

        super.onCreate(savedInstanceState);

        Cutter.Debug();

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
        this.checkLogin();
    }

    protected void checkWifi(){
        if(Util.isWifiConnected(this)==false){
            AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                    .setTitle(R.string.tishi)//标题
                    .setMessage(R.string.wifinoconnect)//内容
                    .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create();
            alertDialog1.show();
        }
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

        if(lastlang.equals(lang)){
            return;
        }
        lastlang=lang;


        Log.e("langkk",lang);
        Log.e("lastlangkk",lastlang);


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
        this.checkWifi();
    }


    protected void checkLogin(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        String account_id=prefs.getString("account_id","0");
        Log.e("account_id",account_id);
        if(account_id.equals("0")){
            if(LoginActivity.Show==false){
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

package com.huansheng.cloudcutter44;

import android.app.Activity;
import android.content.Context;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;
import com.huansheng.cloudcutter44.Mgr.SerialManager;
import com.huansheng.cloudcutter44.Mgr.UpdateManager;

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
    public String lastlang="";


    private UpdateManager mUpdateManager;

    public static String LangCode="chn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        int color=Color.parseColor("#ffffff");


        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }
//或者仅仅使用 locale = Locale.getDefault(); 不需要考虑接口 deprecated(弃用)问题
        String lang = locale.getLanguage();

        Log.e("langkk",lang);

//        if(lang=='ch'){
//            AppBase.langcode='chn'
//        }if(lang=='en'){
//            AppBase.langcode='eng'
//        }if(lang=='fr'){
//            AppBase.langcode='fra'
//        }if(lang=='es'){
//            AppBase.langcode='esp'
//        }if(lang=='po'){
//            AppBase.langcode='por'
//        }if(lang=='de'){
//            AppBase.langcode='deu'
//        }if(lang=='py'){
//            AppBase.langcode='py'
//        }

        if(lang.equals("en")){
            MainActivity.LangCode="eng";
        }else if(lang.equals("fr")){
            MainActivity.LangCode="fra";
        }else if(lang.equals("es")){
            MainActivity.LangCode="esp";
        }else if(lang.equals("en")){
            MainActivity.LangCode="eng";
        }else if(lang.equals("po")){
            MainActivity.LangCode="por";
        }else if(lang.equals("de")){
            MainActivity.LangCode="deu";
        }else if(lang.equals("py")){
            MainActivity.LangCode="py";
        }


        super.onCreate(savedInstanceState);
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
        //this.resetLanguage();

        SerialManager serialManager=SerialManager.GetInstance();


        MainActivity.Instance=this;


        mUpdateManager = new UpdateManager(this);
        mUpdateManager.checkUpdateInfo();
    }

    protected void checkLogin(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        String account_id=prefs.getString("account_id","0");
        Log.e("account_id",account_id);
        if(account_id.equals("0")){

            Intent intent=new Intent(this, LoginActivity.class);
            //执行意图  
            startActivity(intent);
        }else{
            MainActivity.account_id=account_id;
        }
    }

    protected void resetLanguage(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        String lang=prefs.getString("lang","");
        if(!lang.equals("")){

            if(!this.lastlang.equals(lang)){
                this.lastlang=lang;
                Configuration configuration = this.getResources().getConfiguration();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {


//                    $eng[$result[$i]["mark"]]=$result[$i]["eng"];
//                    $chn[$result[$i]["mark"]]=$result[$i]["chn"];
//
//                    $esp[$result[$i]["mark"]]=$result[$i]["esp"];
//                    $por[$result[$i]["mark"]]=$result[$i]["por"];
//                    $deu[$result[$i]["mark"]]=$result[$i]["deu"];
//                    $fra[$result[$i]["mark"]]=$result[$i]["fra"];
//                    $py[$result[$i]["mark"]]=$result[$i]["py"];
                    if(lang.equals("eng")){
                        configuration.setLocale(Locale.ENGLISH);
                    }else if(lang.equals("chn")){
                        configuration.setLocale(Locale.CHINESE);
                    }else if(lang.equals("esp")){
                        configuration.setLocale(Locale.CHINA);

                    }else if(lang.equals("por")){
                        configuration.setLocale(Locale.CHINESE);

                    }else if(lang.equals("deu")){
                        configuration.setLocale(Locale.CHINESE);

                    }else if(lang.equals("fra")){
                        configuration.setLocale(Locale.CHINESE);

                    }else if(lang.equals("py")){
                        configuration.setLocale(Locale.CHINESE);

                    }else{
                        configuration.setLocale(Locale.ENGLISH);

                    }

                } else {
                    //configuration.locale = locale;
                }
                DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                this.getResources().updateConfiguration(configuration, displayMetrics);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.checkLogin();
        //this.resetLanguage();
    }

}

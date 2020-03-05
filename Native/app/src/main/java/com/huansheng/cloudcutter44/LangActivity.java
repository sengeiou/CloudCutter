package com.huansheng.cloudcutter44;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.Mgr.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LangActivity extends AppCompatActivity {

    public  static LangActivity Instance;
    private ListView list;

    public String currentLangcode=MainActivity.lastlang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);

        setTitle( R.string.setyuyan );
        final LangActivity that=this;
        LangActivity.Instance=this;

        this.list=findViewById(R.id.list);
        List<Lang> list=new ArrayList<Lang>();
        list.add(new Lang(R.mipmap.chn,"中文","ch"));
        list.add(new Lang(R.mipmap.usa,"English","en"));
        list.add(new Lang(R.mipmap.es,"Español","es"));
        list.add(new Lang(R.mipmap.pt,"Portugues","pt"));
        list.add(new Lang(R.mipmap.de,"Deutsch","de"));
        list.add(new Lang(R.mipmap.fr,"Français","fr"));
        list.add(new Lang(R.mipmap.ru,"Pyсскпй","ru"));

        LangListAdapter brandListAdapter=new LangListAdapter(that.getBaseContext(),R.layout.imagenamelist,list);
        that.list.setAdapter(brandListAdapter);
        Log.e("MainLang",MainActivity.lastlang);
        Log.e("MainLangcurrentLangcode",currentLangcode);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(MainActivity.lastlang.equals(currentLangcode)==false){
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("lang",currentLangcode);
                    editor.commit();
                    //LangActivity.this.finish();
                    resetLanguage();
                }else{

                    this.finish(); // back button
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class Lang{
        int img;
        String name;
        String langcode;
        public Lang(int img,String name,String langcode){
            this.img=img;
            this.name=name;
            this.langcode=langcode;
        }
    }


    class LangListAdapter extends ArrayAdapter<Lang> {

        private int resourceId;
        public LangListAdapter(@NonNull Context context, int resource, @NonNull List<Lang> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            final Lang obj=getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            //
            try {
                ((SimpleDraweeView) view.findViewById(R.id.img)).setImageResource(obj.img);

                ((TextView) view.findViewById(R.id.name)).setText(obj.name);
                ((TextView) view.findViewById(R.id.count)).setVisibility(View.GONE);
                ImageView right=(ImageView)view.findViewById(R.id.right_icon);
                right.setImageResource(R.mipmap.checking);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,
                        30);//两个400分别为添加图片的大小
                right.setLayoutParams(params);

                view.setTag(obj);
                right.setVisibility(obj.langcode.equals(currentLangcode)?View.VISIBLE:View.INVISIBLE);
                if(obj.langcode.equals(currentLangcode)){
                    setTitle(obj.name);
                }
                view.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        currentLangcode=obj.langcode;
                        loadCheck();
                        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                        //SharedPreferences.Editor editor = prefs.edit();
                        //editor.putString("lang",obj.langcode);
                        //editor.commit();
                        //LangActivity.this.finish();
                        //resetLanguage();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }
    }

    public  void loadCheck(){
        Log.e("MainLangf",currentLangcode);
        for(int i=0;i<list.getChildCount();i++){
            View view=list.getChildAt(i);
            Lang obj=(Lang)view.getTag();
            String langcode=obj.langcode;
            ImageView right=view.findViewById(R.id.right_icon);
            Log.e("MainLangk",langcode+"="+ currentLangcode);
            right.setVisibility(langcode.equals(currentLangcode)?View.VISIBLE:View.INVISIBLE);
            if(langcode.equals(currentLangcode)){
                setTitle(obj.name);
            }
        }
    }



    public static String lastlang="";

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
        reStartApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.hideBottomMenu(this);
    }


    public static void reStartApp()
    {
        Intent intent = MainActivity.Instance.getPackageManager().getLaunchIntentForPackage(MainActivity.Instance.getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(MainActivity.Instance, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager)MainActivity.Instance.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
        System.exit(0);
    }

}

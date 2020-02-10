package com.huansheng.cloudcutter44;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LangActivity extends AppCompatActivity {

    public  static LangActivity Instance;
    private ListView list;

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
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
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
                ((UrlImageView) view.findViewById(R.id.img)).setImageResource(obj.img);

                ((TextView) view.findViewById(R.id.name)).setText(obj.name);
                ((TextView) view.findViewById(R.id.count)).setVisibility(View.GONE);
                view.findViewById(R.id.right_icon).setVisibility(View.VISIBLE);

                view.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Log.e("kk","aa");
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("lang",obj.langcode);
                        editor.commit();
                        LangActivity.this.finish();
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }
    }
}

package com.huansheng.cloudcutter44;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;
import com.huansheng.cloudcutter44.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAccountActivity extends AppCompatActivity {

    ListView buyrecordlist;
    ListView cutlist;
    ListView chargelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        setTitle(R.string.wodezhanghu);


        this.buyrecordlist=findViewById(R.id.buyrecordlist);

        this.cutlist=findViewById(R.id.cutlist);
        this.chargelist=findViewById(R.id.chargelist);


        this.tabhot = findViewById(R.id.tabhot);
        this.t0 = findViewById(R.id.t0);
        this.t1 = findViewById(R.id.t1);
        this.t2 = findViewById(R.id.t2);
        this.t0v = findViewById(R.id.t0v);
        this.t1v = findViewById(R.id.t1v);
        this.t2v = findViewById(R.id.t2v);

        this.tabhot.getTabAt(MyAccountActivity.ShowType).select();
        setTabVisable();

        this.tabhot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

//                Toast toast=Toast.makeText(getContext(),"Toast提示消息"+tab.getPosition(),Toast.LENGTH_SHORT    );
//                toast.show();
                MyAccountActivity.ShowType=tab.getPosition();
                setTabVisable();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        final MyAccountActivity that = this;

        MemberApi memberApi = new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        json.put("account_id", MainActivity.account_id);
        memberApi.buyrecordlist(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    List<NameDateCount> alist = new ArrayList<NameDateCount>();
                    JSONArray list = new JSONArray(val);
                    for (int i = 0; i < list.length(); i++) {
//{{lang.chongqian}} {{item.buycount}} {{lang.cishu}}
                          String name=getResources().getString(R.string.chongqian)+" "
                                  + list.getJSONObject(i).getString("buycount")+" "
                                  +getResources().getString(R.string.cishu);
                          String date=list.getJSONObject(i).getString("buytime");
                          String count="-¥"+list.getJSONObject(i).getString("buyprice");
                          NameDateCount ndc=new NameDateCount(name,date,count);
                          alist.add(ndc);
                    }
                    NameDateCountAdapter brandListAdapter = new NameDateCountAdapter(that.getBaseContext(), R.layout.namedatecountlist, alist);

                    that.buyrecordlist.setAdapter(brandListAdapter);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });


        memberApi.cutlist(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    List<NameDateCount> alist = new ArrayList<NameDateCount>();
                    JSONArray list = new JSONArray(val);
                    for (int i = 0; i < list.length(); i++) {
//{{lang.chongqian}} {{item.buycount}} {{lang.cishu}}
                        String name=  list.getJSONObject(i).getString("model_modelname") ;
                        String date=list.getJSONObject(i).getString("cuttime");
                        String count="-"+list.getJSONObject(i).getString("cutcount");
                        NameDateCount ndc=new NameDateCount(name,date,count);
                        alist.add(ndc);
                    }
                    NameDateCountAdapter brandListAdapter = new NameDateCountAdapter(that.getBaseContext(), R.layout.namedatecountlist, alist);

                    that.cutlist.setAdapter(brandListAdapter);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });


        memberApi.rechargelist(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    List<JSONObject> alist = new ArrayList<JSONObject>();
                    JSONArray list = new JSONArray(val);
                    for (int i = 0; i < list.length(); i++) {
                        alist.add((JSONObject) list.get(i));
                    }
                    alist.add(null);
                    RechargeListAdapter hotListAdapter = new RechargeListAdapter(that, R.layout.imagenamelist, alist);

                    that.chargelist.setAdapter(hotListAdapter);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static int ShowType=0;

    TabLayout tabhot;
    private TabItem t0;
    private TabItem t1;
    private TabItem t2;
    private View t0v;
    private View t1v;
    private View t2v;

    public void setTabVisable() {
        int position= MyAccountActivity.ShowType;
        Log.e("setTabVisable", String.valueOf(position));
        this.t0v.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        this.t1v.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
        this.t2v.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
    }

    class  NameDateCount{
        public  String name;
        public  String date;
        public  String count;
        public NameDateCount(String name,String date,String count){
            this.name=name;
            this.date=date;
            this.count=count;
        }
    }


    class NameDateCountAdapter extends ArrayAdapter<NameDateCount> {

        private int resourceId;

        public NameDateCountAdapter(@NonNull Context context, int resource, @NonNull List<NameDateCount> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final NameDateCount obj = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            //
            try {
                ((TextView) view.findViewById(R.id.name)).setText(obj.name);
                ((TextView) view.findViewById(R.id.date)).setText(obj.date);
                ((TextView) view.findViewById(R.id.count)).setText(obj.count);



            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }
    }


    class RechargeListAdapter extends ArrayAdapter<JSONObject> {

        private int resourceId;

        public RechargeListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final JSONObject obj = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            if (obj == null) {
                return view;
            }
            //
            try {
                ((UrlImageView) view.findViewById(R.id.img)).setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.name)).setText("¥"+obj.getString("price") );
                ((TextView) view.findViewById(R.id.count)).setText(obj.getString("count")+MyAccountActivity.this.getResources().getString(R.string.cishu));
                view.findViewById(R.id.right_icon).setVisibility(View.VISIBLE);

                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Log.e("kk", "aa");

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }
    }
}
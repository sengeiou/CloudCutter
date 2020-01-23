package com.huansheng.cloudcutter44.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.CutdetailActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private  UrlImageView testimg;
    private TabLayout tabhot;
    private TabItem tabuse;

    private View hotcontent;
    private View usecontent;

    private ListView hotlist;
    private ListView uselist;

    private TextView currentspeed;
    private TextView currentpressure;

    private View trycut;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        this.hotcontent=root.findViewById(R.id.hotcontent);
        this.usecontent=root.findViewById(R.id.usecontent);
        this.hotlist=root.findViewById(R.id.hotlist);
        this.uselist=root.findViewById(R.id.uselist);
        this.currentspeed=root.findViewById(R.id.currentspeed);
        this.currentpressure=root.findViewById(R.id.currentpressure);

        this.trycut=root.findViewById(R.id.trycut);

        this.setTabVisable(0);

        this.tabhot=root.findViewById(R.id.tabhot);
        this.tabhot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

//                Toast toast=Toast.makeText(getContext(),"Toast提示消息"+tab.getPosition(),Toast.LENGTH_SHORT    );
//                toast.show();
                setTabVisable(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        this.trycut.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Cutter cutter=new Cutter();
                cutter.tryCut(new Handler(){
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle data = msg.getData();
                        int resultcode=data.getInt("resultcode");
                        Log.e("cutter trycut",String.valueOf(resultcode));
                        if(resultcode==0){
                            Toast.makeText(HomeFragment.this.getContext(),R.string.zlxd,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(HomeFragment.this.getContext(),R.string.sksb,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        this.onMyShow();
        return root;
    }

    public  void setTabVisable(int position){
        Log.e("setTabVisable" ,String.valueOf(position) );
        this.hotcontent.setVisibility(position==0?View.VISIBLE:View.GONE);
        this.usecontent.setVisibility(position==1?View.VISIBLE:View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e("onStart" ,"1" );
        //this.onMyShow();
    }

    public void onResume() {

        super.onResume();
        //this.onMyShow();

        this.refreshSpeed();

    }

    private void refreshSpeed(){
        Cutter cutter=new Cutter();
        cutter.getSpeed(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int speed=data.getInt("speed");
                int resultcode=data.getInt("resultcode");
                Log.e("cutter getSpeed",String.valueOf(speed));
                if(resultcode==0){
                    HomeFragment.this.currentspeed.setText(String.valueOf(speed));
                }else{
                    HomeFragment.this.currentspeed.setText("- -");
                }
                HomeFragment.this.refreshPressure();
            }
        });
    }


    private void refreshPressure() {
        Cutter cutter=new Cutter();
        cutter.getPressure(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int pressure=data.getInt("pressure");
                int resultcode=data.getInt("resultcode");
                Log.e("cutter getPressure",String.valueOf(pressure));
                if(resultcode==0){
                    HomeFragment.this.currentpressure.setText(String.valueOf(pressure));
                }else{
                    HomeFragment.this.currentpressure.setText("- -");
                }
            }
        });

    }

    @SuppressLint("HandlerLeak")
    public void  onMyShow(){

        final Context ctx=getContext();
        final HomeFragment that=this;

        PhoneApi phoneapi=new PhoneApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("orderby","r_main.cutcount desc");
        json.put("limit","0,10");
        phoneapi.modellist(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                //Log.e("modellistkkk",val);

                try {
                    List<JSONObject> alist=new ArrayList<JSONObject>();
                    JSONArray list=new JSONArray(val);
                    for (int i=0;i<list.length();i++){
                        alist.add((JSONObject) list.get(i));
                    }
                    alist.add(null);
                    HotListAdapter hotListAdapter=new HotListAdapter(getContext(),R.layout.imagenamelist,alist);

                    that.hotlist.setAdapter(hotListAdapter);

                } catch (Exception e) {
                    Log.e("modellist2",e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        final Map<String,String> json2=new HashMap<String, String>();
        json.put("orderby","r_main.cutcount desc");
        json.put("limit","0,10");
        phoneapi.commonlist(json2,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    List<JSONObject> alist=new ArrayList<JSONObject>();
                    JSONArray list=new JSONArray(val);
                    for (int i=0;i<list.length();i++){
                        alist.add((JSONObject) list.get(i));
                    }
                    alist.add(null);
                    HotListAdapter hotListAdapter=new HotListAdapter(getContext(),R.layout.imagenamelist,alist);

                    that.uselist.setAdapter(hotListAdapter);

                } catch (Exception e) {
                    Log.e("modellist2",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


}

class HotListAdapter extends ArrayAdapter<JSONObject>{

    private int resourceId;
    public HotListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        final JSONObject obj=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        if(obj==null){
            return view;
        }
        //
        try {
            ((UrlImageView) view.findViewById(R.id.img)).setImageURL(ApiConfig.getUploadPath()+"brand/"+obj.getString("brand_brandlogo"));
            Log.e("modelist4",obj.getString("modelname"));
            ((TextView) view.findViewById(R.id.name)).setText(obj.getString("modelname")+"/"+obj.getString("cutclassify_id_name"));
            ((TextView) view.findViewById(R.id.count)).setText(obj.getString("cutcount"));

            final String id=obj.getString("id");
            final String modelname=obj.getString("modelname");
            final String typename=obj.getString("cy_classifyname");

            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Log.e("kk","aa");

                    Intent intent=new Intent(MainActivity.Instance, CutdetailActivity.class);
                    intent.putExtra("id",id );
                    intent.putExtra("modelname", modelname+typename);
                    //执行意图  
                    MainActivity.Instance.startActivity(intent);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
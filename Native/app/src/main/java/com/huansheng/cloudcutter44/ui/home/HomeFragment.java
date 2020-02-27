package com.huansheng.cloudcutter44.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.CutdetailActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.ui.cutdetail.CutdetailFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static int ShowType=0;

    private HomeViewModel homeViewModel;
    private SimpleDraweeView testimg;
    private TabLayout tabhot;
    private TabItem t0;
    private TabItem t1;

    private View hotcontent;
    private View usecontent;

    private ListView hotlist;
    private ListView uselist;

    private TextView currentspeed;
    private TextView currentpressure;

    private View trycut;

    private static int LOADED = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        if(HomeFragment.LOADED==1){
            //return root;
        }
        HomeFragment.LOADED=1;

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        this.hotcontent = root.findViewById(R.id.hotcontent);
        this.usecontent = root.findViewById(R.id.usecontent);
        this.hotlist = root.findViewById(R.id.hotlist);
        this.uselist = root.findViewById(R.id.uselist);
        this.currentspeed = root.findViewById(R.id.currentspeed);
        this.currentpressure = root.findViewById(R.id.currentpressure);

        this.trycut = root.findViewById(R.id.trycut);

        this.setTabVisable();

        this.tabhot = root.findViewById(R.id.tabhot);
        this.t0 = root.findViewById(R.id.t0);
        this.t1 = root.findViewById(R.id.t1);

        this.tabhot.getTabAt(HomeFragment.ShowType).select();

        this.tabhot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

//                Toast toast=Toast.makeText(getContext(),"Toast提示消息"+tab.getPosition(),Toast.LENGTH_SHORT    );
//                toast.show();
                HomeFragment.ShowType=tab.getPosition();
                setTabVisable();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        this.trycut.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cutter cutter = new Cutter();
                cutter.tryCut(new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle data = msg.getData();
                        int resultcode = data.getInt("resultcode");
                        Log.e("cutter trycut", String.valueOf(resultcode));
                        if (resultcode == 0) {
                            Toast.makeText(HomeFragment.this.getContext(), R.string.zlxd, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HomeFragment.this.getContext(), R.string.sksb, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.onMyShow();
        loadMember();
    }


    protected void loadMember(){

        final HomeFragment that=this;

        MemberApi memberapi=new MemberApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("id", MainActivity.account_id);
        memberapi.accountinfo(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {

                    JSONObject ret=new JSONObject(val);
                    that.currentpressure.setText(ret.getString("daoya"));
                    that.currentspeed.setText(ret.getString("sudu"));

                    String checking=ret.getString("checking");

                } catch (Exception e) {
                    //Log.e("accountinfo",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void setTabVisable() {
        int position=HomeFragment.ShowType;
        Log.e("setTabVisable", String.valueOf(position));
        this.hotcontent.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        this.usecontent.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.e("onStart", String.valueOf(loaded++));
    }

    public void onResume() {

        super.onResume();

    }

//    private void refreshSpeed() {
//        Cutter cutter = new Cutter();
//        cutter.getSpeed(new Handler() {
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                Bundle data = msg.getData();
//                int speed = data.getInt("speed");
//                int resultcode = data.getInt("resultcode");
//                Log.e("cutter getSpeed", String.valueOf(speed));
//                if (resultcode == 0) {
//                    HomeFragment.this.currentspeed.setText(String.valueOf(speed));
//                } else {
//                    HomeFragment.this.currentspeed.setText("- -");
//                }
//                HomeFragment.this.refreshPressure();
//            }
//        });
//    }


//    private void refreshPressure() {
//        Cutter cutter = new Cutter();
//        cutter.getPressure(new Handler() {
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                Bundle data = msg.getData();
//                int pressure = data.getInt("pressure");
//                int resultcode = data.getInt("resultcode");
//                Log.e("cutter getPressure", String.valueOf(pressure));
//                if (resultcode == 0) {
//                    HomeFragment.this.currentpressure.setText(String.valueOf(pressure));
//                } else {
//                    HomeFragment.this.currentpressure.setText("- -");
//                }
//            }
//        });
//
//    }

    @SuppressLint("HandlerLeak")
    public void onMyShow() {

        final Context ctx = getContext();
        final HomeFragment that = this;

        PhoneApi phoneapi = new PhoneApi();
        final Map<String, String> json = new HashMap<String, String>();
        json.put("orderby", "r_main.cutcount desc");
        json.put("limit", "0,10");
        phoneapi.modellist(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                //Log.e("modellistkkk",val);

                try {
                    List<JSONObject> alist = new ArrayList<JSONObject>();
                    JSONArray list = new JSONArray(val);
                    for (int i = 0; i < list.length(); i++) {
                        alist.add((JSONObject) list.get(i));
                    }
                    alist.add(null);
                    HotListAdapter hotListAdapter = new HotListAdapter(getContext(), R.layout.imagenamelist, alist);

                    that.hotlist.setAdapter(hotListAdapter);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
        loadCommonlist();

    }


    public void loadCommonlist() {
        final Context ctx = getContext();
        final HomeFragment that = this;

        MemberApi memberapi = new MemberApi();

        final Map<String, String> json = new HashMap<String, String>();
        json.put("account_id", MainActivity.account_id);
        Log.e("commonlist",MainActivity.account_id);
        memberapi.commonlist(json, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    Log.e("commonlist",val);
                    List<JSONObject> alist = new ArrayList<JSONObject>();
                    JSONArray list = new JSONArray(val);
                    for (int i = 0; i < list.length(); i++) {
                        alist.add((JSONObject) list.get(i));
                    }
                    alist.add(null);
                    CommonListAdapter hotListAdapter = new CommonListAdapter(getContext(), R.layout.imagenamelist, alist);

                    that.uselist.setAdapter(hotListAdapter);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }


    class HotListAdapter extends ArrayAdapter<JSONObject> {

        private int resourceId;

        public HotListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
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
                Uri uri = Uri.parse(FormatUtil.URLEncode(ApiConfig.getUploadPath() + "brand/" + obj.getString("brand_brandlogo")));
                ((SimpleDraweeView) view.findViewById(R.id.img)).setImageURI(uri);
                Log.e("modelist4", obj.getString("modelname"));
                ((TextView) view.findViewById(R.id.name)).setText(obj.getString("modelname") + "/" + obj.getString("cy_classifyname"));
                ((TextView) view.findViewById(R.id.count)).setText(obj.getString("cutcount"));

                final String id = obj.getString("id");
                final String modelname = obj.getString("modelname");
                final String typename = obj.getString("cy_classifyname");

                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Log.e("kk", "aa");

                        Intent intent = new Intent(MainActivity.Instance, CutdetailActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("modelname", modelname + typename);
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


    class CommonListAdapter extends ArrayAdapter<JSONObject> {

        private int resourceId;

        public CommonListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
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
                Log.e("kkk1","kkk");
                Uri uri = Uri.parse(FormatUtil.URLEncode(ApiConfig.getUploadPath() + "brand/" + obj.getString("logo")));
                ((SimpleDraweeView) view.findViewById(R.id.img)).setImageURI(uri);

                ((TextView) view.findViewById(R.id.name)).setText(obj.getString("model_modelname") + "/" + obj.getString("classifyname"));

                ((TextView) view.findViewById(R.id.count)).setText(R.string.sc);

                final String id = obj.getString("id");
                final String model_id = obj.getString("model_id");
                final String modelname = obj.getString("model_modelname");
                final String typename = obj.getString("classifyname");

                ((TextView) view.findViewById(R.id.name)).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Log.e("kk", "aa");

                        Intent intent = new Intent(MainActivity.Instance, CutdetailActivity.class);
                        intent.putExtra("id", model_id);
                        intent.putExtra("modelname", modelname + typename);
                        //执行意图  
                        MainActivity.Instance.startActivity(intent);
                    }
                });

                ((TextView) view.findViewById(R.id.count)).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.Instance)
                                .setTitle(R.string.tishi)//标题
                                .setMessage(R.string.qrsc)//内容
                                .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        MemberApi memberapi=new MemberApi();
                                        final Map<String, String> json = new HashMap<String, String>();
                                        json.put("id", id);
                                        memberapi.deletecommon(json, new Handler() {
                                            @Override
                                            public void handleMessage(Message msg) {
                                                super.handleMessage(msg);
                                                Bundle data = msg.getData();
                                                String val = data.getString("ret");

                                                try {

                                                    loadCommonlist();

                                                } catch (Exception e) {

                                                    e.printStackTrace();
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


            } catch (Exception e) {
                Log.e("kkk","kkk");
                e.printStackTrace();
            }
            return view;
        }
    }

}
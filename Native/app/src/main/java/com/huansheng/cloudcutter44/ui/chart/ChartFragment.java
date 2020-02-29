package com.huansheng.cloudcutter44.ui.chart;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.CutdetailActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.Mgr.DataCountChart;
import com.huansheng.cloudcutter44.Mgr.ModalCountBarchat;
import com.huansheng.cloudcutter44.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartFragment extends Fragment {
    private static int ShowType=0;
    private static int DayType=2;
    private TabLayout tabhot;
    private TabItem t0;
    private TabItem t1;
    private View daily;
    private View models;
    private View daily2;
    private View models2;
    private ChartViewModel mViewModel;
    LineChart dailychart;
    ListView dailydata;
    PieChart modelscharta7d;
    PieChart modelscharta1m;
    PieChart modelscharta3m;
    ListView modelsdata;

    TextView a7d;
    TextView a1m;
    TextView a3m;


    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.chart_fragment, container, false);

        this.tabhot = root.findViewById(R.id.tabhot);
        this.daily = root.findViewById(R.id.daily);
        this.models = root.findViewById(R.id.models);
        this.daily2 = root.findViewById(R.id.daily2);
        this.models2 = root.findViewById(R.id.models2);
        this.t0 = root.findViewById(R.id.t0);
        this.t1 = root.findViewById(R.id.t1);


        this.a7d = root.findViewById(R.id.a7d);
        this.a7d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChartFragment.DayType=0;
                setDayOptionActive();
            }
        });
        this.a1m = root.findViewById(R.id.a1m);
        this.a1m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChartFragment.DayType=1;
                setDayOptionActive();
            }
        });
        this.a3m = root.findViewById(R.id.a3m);
        this.a3m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChartFragment.DayType=2;
                setDayOptionActive();
            }
        });

        this.dailydata=root.findViewById(R.id.dailydata);

        this.dailychart=root.findViewById(R.id.dailychart);
        this.dailychart.setNoDataText("");
        this.dailychart.setDescription(null);

        this.modelsdata=root.findViewById(R.id.modelsdata);

        this.modelscharta7d=root.findViewById(R.id.modelscharta7d);
        this.modelscharta1m=root.findViewById(R.id.modelscharta1m);
        this.modelscharta3m=root.findViewById(R.id.modelscharta3m);
        this.modelscharta7d.setNoDataText("");
        this.modelscharta1m.setNoDataText("");
        this.modelscharta3m.setNoDataText("");
        this.modelscharta7d.setDescription(null);
        this.modelscharta1m.setDescription(null);
        this.modelscharta3m.setDescription(null);


        this.tabhot.getTabAt(ChartFragment.ShowType).select();

        this.tabhot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

//                Toast toast=Toast.makeText(getContext(),"Toast提示消息"+tab.getPosition(),Toast.LENGTH_SHORT    );
//                toast.show();
                ChartFragment.ShowType=tab.getPosition();
                setTabVisable();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setDayOptionActive();
        setTabVisable();

        loadDailyChart();
        loadModalsChart(0);
        loadModalsChart(1);
        loadModalsChart(2);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChartViewModel.class);
        // TODO: Use the ViewModel
    }

    public void setDayOptionActive(){

        int position=ChartFragment.DayType;
        this.a7d.setBackgroundColor(getResources().getColor( position==0?R.color.primary:R.color.white) );
        this.a1m.setBackgroundColor(getResources().getColor( position==1?R.color.primary:R.color.white) );
        this.a3m.setBackgroundColor(getResources().getColor( position==2?R.color.primary:R.color.white) );
        this.a7d.setTextColor(getResources().getColor( position==0?R.color.white:R.color.grayft) );
        this.a1m.setTextColor(getResources().getColor( position==1?R.color.white:R.color.grayft) );
        this.a3m.setTextColor(getResources().getColor( position==2?R.color.white:R.color.grayft) );
        this.modelscharta7d.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        this.modelscharta1m.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
        this.modelscharta3m.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
    }
    public void setTabVisable() {
        int position=ChartFragment.ShowType;
        Log.e("setTabVisable", String.valueOf(position));
        this.daily.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        this.daily2.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        this.models.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
        this.models2.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
    }

    public  void loadDailyChart(){
         MemberApi memberapi=new MemberApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("type","A");
        json.put("account_id", MainActivity.account_id);
        memberapi.cutlist(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("cutlist",val);
                try {
                    List<JSONObject> alist=new ArrayList<JSONObject>();
                    List<JSONObject> blist=new ArrayList<JSONObject>();
                    JSONArray list=new JSONArray(val);
                    for (int i=list.length()-1;i>=0;i--){
                        alist.add((JSONObject) list.get(i));
                    }
                    for (int i=0;i<list.length();i++){
                        blist.add((JSONObject) list.get(i));
                    }
                    if(alist.size()>0){
                        String dailysales=getResources().getString(R.string.mrxl);
                        DataCountChart dataCountChart=new DataCountChart(ChartFragment.this.dailychart,alist,Color.BLUE,dailysales,LineDataSet.Mode.LINEAR);
                        String start=alist.get(0).getString("cuttime");
                        String end=alist.get(alist.size()-1).getString("cuttime");
                        dataCountChart.setLength(start,end);
                        Drawable db=getResources().getDrawable(R.drawable.fade_blue);
                        dataCountChart.drawable=db;
                        dataCountChart.render();
                        ChartFragment.this.dailychart.setVisibility(View.VISIBLE);
                    }

                    blist.add(null);
                    CuttimeListAdapter hotListAdapter = new CuttimeListAdapter(getContext(), R.layout.imagenamelist, blist);
                    ChartFragment.this.dailydata.setAdapter(hotListAdapter);
                } catch (Exception e) {
                    //
                    e.printStackTrace();
                }
            }
        });


    }




    public  void loadModalsChart(int datatype){
        MemberApi memberapi=new MemberApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("type","B");
        json.put("account_id", MainActivity.account_id);

        Date enddate=new Date();
        long st=0;

        PieChart modelschart=this.modelscharta7d;
        if(datatype==0){
            st=(long)7*24*3600*1000;
            modelschart=this.modelscharta7d;
        }
        if(datatype==1){
            st=(long)30*24*3600*1000;
            modelschart=this.modelscharta1m;
        }
        if(datatype==2){
            st=(long)90*24*3600*1000;
            modelschart=this.modelscharta3m;
        }
        final PieChart finalmodelchart=modelschart;
        Date startdate=new Date(enddate.getTime()-st);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        final String starttimestr=formatter.format(startdate);
        final String endtimestr=formatter.format(enddate);

        json.put("startime", starttimestr);
        json.put("endtime", endtimestr);

        memberapi.cutlist(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("cutlist",val);
                try {
                    int showcount=9;
                    List<JSONObject> alist=new ArrayList<JSONObject>();
                    JSONArray list=new JSONArray(val);
                    for (int i=0;i<list.length()&&i<showcount;i++){
                        alist.add((JSONObject) list.get(i));
                    }
                    int othercount=0;
                    for (int i=showcount;i<list.length();i++){
                        othercount+=((JSONObject) list.get(i)).getInt("count");
                    }
                    if(othercount>0){
                        JSONObject other=new JSONObject("{'modelname':'"+getResources().getString(R.string.others)+"',count:"+String.valueOf(othercount)+"}");
                        alist.add(other);
                    }

                    ModalCountBarchat dataCountChart=new ModalCountBarchat(finalmodelchart,alist);
                    dataCountChart.render();

                    List<JSONObject> blist=new ArrayList<JSONObject>();
                    for (int i=0;i<list.length();i++){
                        blist.add((JSONObject) list.get(i));
                    }
                    blist.add(null);
                    ModelListAdapter hotListAdapter = new ModelListAdapter(getContext(), R.layout.imagenamelist, blist);
                    ChartFragment.this.modelsdata.setAdapter(hotListAdapter);
                } catch (Exception e) {
                    //
                    e.printStackTrace();
                }
            }
        });
    }

}


class CuttimeListAdapter extends ArrayAdapter<JSONObject> {

    private int resourceId;

    public CuttimeListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
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
            ( view.findViewById(R.id.img)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.name)).setText(obj.getString("cuttime") );
            ((TextView) view.findViewById(R.id.count)).setText(obj.getString("count"));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}


class ModelListAdapter extends ArrayAdapter<JSONObject> {

    private int resourceId;

    public ModelListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
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
            (view.findViewById(R.id.img)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.name)).setText(obj.getString("modelname") );
            ((TextView) view.findViewById(R.id.count)).setText(obj.getString("count"));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
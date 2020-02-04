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

import com.github.mikephil.charting.charts.LineChart;
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
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;
import com.huansheng.cloudcutter44.ui.home.HomeFragment;

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
    private TabLayout tabhot;
    private TabItem t0;
    private TabItem t1;
    private View daily;
    private View models;
    private ChartViewModel mViewModel;
    LineChart dailychart;

    ListView dailydata;


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
        this.t0 = root.findViewById(R.id.t0);
        this.t1 = root.findViewById(R.id.t1);

        this.dailydata=root.findViewById(R.id.dailydata);

        this.dailychart=root.findViewById(R.id.dailychart);


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

        loadDailyChart();


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChartViewModel.class);
        // TODO: Use the ViewModel
    }

    public void setTabVisable() {
        int position=ChartFragment.ShowType;
        Log.e("setTabVisable", String.valueOf(position));
        this.daily.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        this.models.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
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
                    JSONArray list=new JSONArray(val);
                    for (int i=0;i<list.length();i++){
                        alist.add((JSONObject) list.get(i));
                    }
                    if(alist.size()==0){
                        SimpleDateFormat sf2 = new SimpleDateFormat("YYYY-MM-dd");
                        String formatStr = sf2.format(new Date());
                        alist.add(new JSONObject("{cuttime:'"+formatStr+"',count:0}"));
                    }
                    String dailysales=getResources().getString(R.string.mrxl);
                    DataCountChart dataCountChart=new DataCountChart(ChartFragment.this.dailychart,alist,Color.BLUE,dailysales);
                    Drawable db=getResources().getDrawable(R.drawable.fade_blue);
                    dataCountChart.drawable=db;
                    dataCountChart.render();


                    CuttimeListAdapter hotListAdapter = new CuttimeListAdapter(getContext(), R.layout.imagenamelist, alist);
                    ChartFragment.this.dailydata.setAdapter(hotListAdapter);
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
            ((UrlImageView) view.findViewById(R.id.img)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.name)).setText(obj.getString("cuttime") );
            ((TextView) view.findViewById(R.id.count)).setText(obj.getString("count"));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
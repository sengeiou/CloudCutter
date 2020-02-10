package com.huansheng.cloudcutter44.Mgr;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ModalCountBarchat {

    public ModalCountBarchat(PieChart chart, List<JSONObject> dataList){
        this.pieChart=chart;
        this.dataList=dataList;
    }

    public void render(){
        setData();  // 设置数据
    }
    private PieChart pieChart;
    List<JSONObject> dataList;

    private void setData() {
        List<PieEntry> strings = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int i=0;i<dataList.size();i++){
            try {
                String modelname = dataList.get(i).getString("modelname");
                int cut=dataList.get(i).getInt("count");
                strings.add(new PieEntry(cut,modelname));
                colors.add(randomColor(modelname));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        PieDataSet dataSet = new PieDataSet(strings,"Label");

        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueTextSize(9f);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    public int randomColor(String name){
        int r=0,g=0,b=0;
        for(int i=0;i<name.length();i++){
            if(i%3==0){
                r+=(int)name.charAt(i);
            }
            if(i%3==1){
                g+=(int)name.charAt(i);
            }
            if(i%3==2){
                b+=(int)name.charAt(i);
            }
        }

        Log.e("rgb",String.valueOf(r)+","+String.valueOf(g)+","+String.valueOf(b)+",");

        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(r%256).toUpperCase();
        G = Integer.toHexString(g%256).toUpperCase();
        B = Integer.toHexString(b%256).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return Color.parseColor( "#" + R + G + B);
    }
}

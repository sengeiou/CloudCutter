package com.huansheng.cloudcutter44.Mgr;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

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
import com.huansheng.cloudcutter44.R;

import org.json.JSONArray;
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

public class DataCountChart {

    public DataCountChart(LineChart chart,List<JSONObject> dataList,int linecolor,String title,LineDataSet.Mode mode){
        this.lineChart=chart;
        this.dataList=dataList;
        this.linecolor=linecolor;
        this.title=title;
        this.mode=mode;
    }

    public void render(){
        initChart();
        if(this.dataList!=null){
            showLineChart(this.title, linecolor);
        }
        if(drawable!=null){
            setChartFillDrawable(drawable);
        }
    }
    public LineDataSet.Mode mode;
    public Map<String,Integer> ylendge1=null;
    public Map<Integer,String> ylendge2=null;
    String title="";
    int linecolor;
    public  Drawable drawable= null;//= getResources().getDrawable(R.drawable.fade_blue);
    List<JSONObject> dataList;
    private LineChart lineChart;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;

    private void initChart() {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置XY轴动画效果
        lineChart.animateY(250);
        lineChart.animateX(150);

        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);

        xAxis.setDrawGridLines(false);
        rightYaxis.setDrawGridLines(false);
        leftYAxis.setDrawGridLines(true);
        rightYaxis.setEnabled(false);

        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate="";
                int i= (int) value;
                if(ylendge2==null){
                    try {
                        tradeDate=dataList.get(i).getString("modelname");
                    } catch (JSONException e) {

                    }
                }else{
                    tradeDate = ylendge2.get(i);
                }
                if(tradeDate==null){
                    tradeDate="";
                }

                return tradeDate;
            }
        });


        leftYAxis.setLabelCount(6);

        legend.setDrawInside(false);
    }

    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat("");
                return df.format(value ) ;
            }
        });
    }

    public void showLineChart(  String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            JSONObject data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            try {
                int k=i;
                if(ylendge1!=null){
                    k=ylendge1.get(data.getString("cuttime"));
                }
                Entry entry = new Entry(k, (float) data.getInt("count"));
                entries.add(entry);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, this.mode);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }
    public void setChartFillDrawable( Drawable drawable) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineChart.invalidate();
        }
    }



    String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    public void addLine(List<JSONObject> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            JSONObject data = dataList.get(i);
            try {
                Entry entry = new Entry(i, (float) data.getInt("count"));
                entries.add(entry);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        if(lineChart.getLineData()==null){
            LineData lineData = new LineData(lineDataSet);
            lineChart.setData(lineData);
        }else{

            lineChart.getLineData().addDataSet(lineDataSet);
            lineChart.invalidate();
        }
    }

    public void setLength(String start,String end){

        Map<String,Integer> map1=new HashMap<String,Integer>();
        Map<Integer,String> map2=new HashMap<Integer,String>();

        Date startdate=null;
        Date enddate=null;
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        try {
            startdate=formatter.parse(start);
            enddate=formatter.parse(end);

            int daycount=(int) ((enddate.getTime()-startdate.getTime())/24/3600/1000);

            for(long i=0;i<=daycount;i++){
                Log.e("lao"+String.valueOf(i),String.valueOf(i*24*3600*1000+startdate.getTime()));
                Date d=new Date(i*24*3600*1000+startdate.getTime());
                String td=formatter.format(d);
                map1.put(td,(int)i);
                map2.put((int)i,td);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.ylendge1=map1;
        this.ylendge2=map2;
    }
}

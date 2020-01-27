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

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseModelActivity extends AppCompatActivity {

    public  static ChooseModelActivity Instance;
    private ListView modellist;
    String id="";
    String classify_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_model);
        setTitle( R.string.xinhao );

        this.id=getIntent().getStringExtra("id");
        this.classify_id=getIntent().getStringExtra("classify_id");

        ChooseModelActivity.Instance=this;
        final ChooseModelActivity that=this;

        this.modellist=findViewById(R.id.modellist);

        PhoneApi phoneapi=new PhoneApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("brand_id",getIntent().getStringExtra("id"));
        json.put("cutclassify_id",getIntent().getStringExtra("classify_id"));
        phoneapi.modellist(json,new Handler() {
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
                    ModelListAdapter modelListAdapter=new ModelListAdapter(that.getBaseContext(),R.layout.imagenamelist,alist);

                    that.modellist.setAdapter(modelListAdapter);
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
}

class ModelListAdapter extends ArrayAdapter<JSONObject> {

    private int resourceId;
    public ModelListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        final JSONObject obj=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //
        try {
            ((UrlImageView) view.findViewById(R.id.img)).setImageURL(ApiConfig.getUploadPath()+"model/"+obj.getString("img"));
            Log.e("modelist4",obj.getString("modelname"));
            ((TextView) view.findViewById(R.id.name)).setText(obj.getString("modelname"));

            ((TextView) view.findViewById(R.id.count)).setVisibility(View.GONE);
            view.findViewById(R.id.right_icon).setVisibility(View.VISIBLE);

            final String id=obj.getString("id");
            final String modelname=obj.getString("modelname");
            final String typename=obj.getString("cy_classifyname");

            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Log.e("kk","aa");

                    Intent intent=new Intent(ChooseModelActivity.Instance, CutdetailActivity.class);
                    intent.putExtra("id",id );
                    intent.putExtra("modelname", modelname+typename);
                    //执行意图  
                    ChooseModelActivity.Instance.startActivity(intent);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
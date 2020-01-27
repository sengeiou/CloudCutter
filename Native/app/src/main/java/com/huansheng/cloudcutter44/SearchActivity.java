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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    public  static SearchActivity Instance;
    private ListView result;
    private TextView search;
    private EditText keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle( R.string.search );


        SearchActivity.Instance=this;
        final SearchActivity that=this;

        this.result=findViewById(R.id.result);
        this.keyword=findViewById(R.id.keyword);
        this.search=findViewById(R.id.search);


        this.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(that,that.keyword.getText(),Toast.LENGTH_SHORT).show();
                PhoneApi phoneapi=new PhoneApi();
                final Map<String,String> json=new HashMap<String, String>();
                json.put("searchkeyword", String.valueOf(that.keyword.getText()));
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
                            SearchResultAdapter searchResultAdapter=new SearchResultAdapter(that.getBaseContext(),R.layout.imagenamelist,alist);

                            that.result.setAdapter(searchResultAdapter);
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                });
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

class SearchResultAdapter extends ArrayAdapter<JSONObject> {

    private int resourceId;
    public SearchResultAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
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

                    Intent intent=new Intent(SearchActivity.Instance, CutdetailActivity.class);
                    intent.putExtra("id",id );
                    intent.putExtra("modelname", modelname+typename);
                    //执行意图  
                    SearchActivity.Instance.startActivity(intent);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
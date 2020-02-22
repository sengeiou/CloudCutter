package com.huansheng.cloudcutter44;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseBrandActivity extends AppCompatActivity {


    public  static ChooseBrandActivity Instance;
    private ListView brandlist;
    String classify_id="";
    EditText quicksearch;
    String keyword="";
    List<JSONObject> datalist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_brand);

        setTitle( R.string.pinpai );

        final ChooseBrandActivity that=this;
        ChooseBrandActivity.Instance=this;

        this.brandlist=findViewById(R.id.brandlist);

        this.classify_id=getIntent().getStringExtra("id");
        this.quicksearch=findViewById(R.id.quicksearch);

        quicksearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("quicksearch",editable.toString());
                keyword=editable.toString();
                search();
            }
        });

        PhoneApi phoneapi=new PhoneApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("cutclassify_id",getIntent().getStringExtra("id"));
        phoneapi.brandlist(json,new Handler() {
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
                    datalist=alist;
                    search();
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

    public void search(){

        List<JSONObject> alist=new ArrayList<JSONObject>();
        for(int i=0;i<datalist.size();i++) {

            if(keyword.trim().equals("")){
                alist.add(datalist.get(i));
            }else{
                String modelname="";
                try {
                    modelname=datalist.get(i).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(modelname.toLowerCase().indexOf(keyword.toLowerCase())<0){
                    //view.setVisibility(View.GONE);
                }else{
                    alist.add(datalist.get(i));
                }
            }

        }

        BrandListAdapter brandListAdapter=new BrandListAdapter(ChooseBrandActivity.this.getBaseContext(),R.layout.imagenamelist,alist);

        ChooseBrandActivity.this.brandlist.setAdapter(brandListAdapter);
    }
}

class BrandListAdapter extends ArrayAdapter<JSONObject> {

    private int resourceId;
    public BrandListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        final JSONObject obj=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //
        try {
            ((SimpleDraweeView) view.findViewById(R.id.img)).setImageURI(FormatUtil.URLEncode(ApiConfig.getUploadPath()+"brand/"+obj.getString("brandlogo")));
            final String name=obj.getString("name");
            ((TextView) view.findViewById(R.id.name)).setText(obj.getString("name"));
            ((TextView) view.findViewById(R.id.count)).setVisibility(View.GONE);
            view.findViewById(R.id.right_icon).setVisibility(View.VISIBLE);
            final String id=obj.getString("id");

            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Log.e("kk","aa");
                    Intent intent=new Intent(ChooseBrandActivity.Instance, ChooseModelActivity.class);
                    intent.putExtra("id",id );
                    intent.putExtra("title",name );
                    intent.putExtra("classify_id", ChooseBrandActivity.Instance.classify_id );
                    //执行意图  
                    ChooseBrandActivity.Instance.startActivity(intent);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
package com.huansheng.cloudcutter44.ui.product;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.ChooseBrandActivity;
import com.huansheng.cloudcutter44.CutdetailActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.Mgr.FormatUtil;
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductFragment extends Fragment implements View.OnClickListener {

    private ProductViewModel mViewModel;
    private ListView classifylist;
    private TextView search;

    private GridView typelist;

    View p1;
    SimpleDraweeView p1img;
    TextView p1text;

    View p2;
    SimpleDraweeView p2img;
    TextView p2text;

    View p3;
    SimpleDraweeView p3img;
    TextView p3text;

    View p4;
    SimpleDraweeView p4img;
    TextView p4text;


    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.product_fragment, container, false);

        this.typelist=root.findViewById(R.id.typelist);

        this.search=root.findViewById(R.id.search);
        this.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.Instance, SearchActivity.class);
                //执行意图  
                MainActivity.Instance.startActivity(intent);
            }
        });



        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        // TODO: Use the ViewModel


        final ProductFragment that=this;



        PhoneApi phoneapi=new PhoneApi();
        final Map<String,String> json=new HashMap<String, String>();
        phoneapi.classifylist(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    JSONArray list=new JSONArray(val);

                    List<JSONObject> alist=new ArrayList<JSONObject>();
                    for(int i=0;i<list.length();i++) {
                        JSONObject obj=(JSONObject) list.get(i);
                        alist.add(obj);
                    }

                    ClassifyListAdapter brandListAdapter=new ClassifyListAdapter(ProductFragment.this.getContext(),R.layout.gridbox,alist);
                    ProductFragment.this.typelist.setAdapter(brandListAdapter);



                } catch (Exception e) {
                    //
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        JSONObject obj=(JSONObject) view.getTag();
        Intent intent=new Intent(MainActivity.Instance, ChooseBrandActivity.class);
        String id= null;
        try {
            id = obj.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra("id",id );
        //执行意图  
        MainActivity.Instance.startActivity(intent);
    }
}

class ClassifyListAdapter extends ArrayAdapter<JSONObject> {

    private int resourceId;
    public ClassifyListAdapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        final JSONObject obj=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //
        try {
            ((SimpleDraweeView) view.findViewById(R.id.img)).setImageURI(FormatUtil.URLEncode( ApiConfig.getUploadPath()+"cutclassify/"+obj.getString("classifyicon")));
            ((TextView) view.findViewById(R.id.name)).setText(obj.getString("classifyname"));


            final String id=obj.getString("id");

            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Log.e("kk","aa");

                    Intent intent=new Intent(MainActivity.Instance, ChooseBrandActivity.class);
                    intent.putExtra("id",id );
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
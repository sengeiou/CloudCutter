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
import android.widget.ListView;
import android.widget.TextView;

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.ChooseBrandActivity;
import com.huansheng.cloudcutter44.CutdetailActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.SearchActivity;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductFragment extends Fragment {

    private ProductViewModel mViewModel;
    private ListView classifylist;
    private TextView search;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.product_fragment, container, false);

        this.classifylist=root.findViewById(R.id.classifylist);
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
                    List<JSONObject> alist=new ArrayList<JSONObject>();
                    JSONArray list=new JSONArray(val);
                    for (int i=0;i<list.length();i++){
                        alist.add((JSONObject) list.get(i));
                    }
                    ClassifyListAdapter classifyListAdapter=new ClassifyListAdapter(getContext(),R.layout.imagenamelist,alist);

                    that.classifylist.setAdapter(classifyListAdapter);
                } catch (Exception e) {
                    //
                    e.printStackTrace();
                }
            }
        });
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
            ((UrlImageView) view.findViewById(R.id.img)).setImageURL(ApiConfig.getUploadPath()+"cutclassify/"+obj.getString("classifyicon"));
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
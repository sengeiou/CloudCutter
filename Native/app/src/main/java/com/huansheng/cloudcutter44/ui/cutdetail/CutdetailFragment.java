package com.huansheng.cloudcutter44.ui.cutdetail;

import androidx.lifecycle.ViewModelProviders;

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
import android.widget.Button;
import android.widget.TextView;

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.CutdetailActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;
import com.huansheng.cloudcutter44.ui.mine.MineFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CutdetailFragment extends Fragment {

    private CutdetailViewModel mViewModel;
    private UrlImageView cutimg;
    private TextView cy_explain;
    private TextView daoya;
    private TextView sudu;
    private Button back;
    private Button cutnow;
    public static CutdetailFragment newInstance() {
        return new CutdetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cutdetail_fragment, container, false);

        this.cutimg=root.findViewById(R.id.cutimg);
        this.cy_explain=root.findViewById(R.id.cy_explain);
        this.back=root.findViewById(R.id.back);
        this.daoya=root.findViewById(R.id.daoya);
        this.sudu=root.findViewById(R.id.sudu);
        this.cutnow=root.findViewById(R.id.cutnow);
        this.back.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        this.back.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {



            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CutdetailViewModel.class);
        // TODO: Use the ViewModel


        final CutdetailFragment that=this;

        String id=getActivity().getIntent().getStringExtra("id");

        PhoneApi phoneapi=new PhoneApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("id",id);
        phoneapi.modelinfo(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");

                try {
                    JSONObject obj=new JSONObject(val);
                    that.cutimg.setImageURL(ApiConfig.getUploadPath()+"model/"+obj.getString("cutimg"));
                    that.cy_explain.setText(obj.getString("cy_explain"));
                } catch (Exception e) {
                    Log.e("modellist2",e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        loadMember();

    }

    protected void loadMember(){

        final CutdetailFragment that=this;

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
                    that.daoya.setText(ret.getString("daoya"));
                    that.sudu.setText(ret.getString("sudu"));

                } catch (Exception e) {
                    Log.e("modellist2",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    protected void cut(){





    }

}

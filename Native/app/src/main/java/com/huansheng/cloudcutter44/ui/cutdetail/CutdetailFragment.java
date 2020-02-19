package com.huansheng.cloudcutter44.ui.cutdetail;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.FileDownload;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.ApiProviders.PhoneApi;
import com.huansheng.cloudcutter44.CutdetailActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;
import com.huansheng.cloudcutter44.ui.home.HomeFragment;
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
    private int isudu=200;
    private int idaoya=320;
    private String filename="";
    private View adduse;
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
        this.cutnow.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                        .setTitle(R.string.tishi)//标题
                        .setMessage(R.string.chulizhong)//内容
                        .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cut();
                            }
                        }).setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                alertDialog1.show();

            }
        });

        this.adduse=root.findViewById(R.id.adduse);
        this.adduse.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                String id=getActivity().getIntent().getStringExtra("id");
                //account_id: this.memberInfo.id, model_id: model_id, status: 'A'
                MemberApi memberapi=new MemberApi();
                final Map<String,String> json=new HashMap<String, String>();
                json.put("account_id",MainActivity.account_id);
                json.put("model_id",id);
                json.put("status","A");
                memberapi.addcommon(json,new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle data = msg.getData();
                        String val = data.getString("ret");

                        try {
                            JSONObject obj=new JSONObject(val);
                            if(obj.getString("code").equals("0")){
                                Toast.makeText(CutdetailActivity.Instance,R.string.tianjiaok,Toast.LENGTH_LONG).show();
                            }else{
                                AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                                        .setTitle(R.string.tishi)//标题
                                        .setMessage(R.string.ycz)//内容
                                        .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加取消
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .create();
                                alertDialog1.show();
                            }

                        } catch (Exception e) {
                            //
                            e.printStackTrace();
                        }
                    }
                });

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
                    that.cutimg.setImageURL(ApiConfig.getUploadPath()+"model/"+obj.getString("cutimg")+ApiConfig.photoStyle2(),false);
                    that.cy_explain.setText(obj.getString("cy_explain"));

                    that.filename=obj.getString("file");

                } catch (Exception e) {
                  //
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


                    that.isudu=Integer.parseInt(ret.getString("sudu"));
                    that.idaoya=Integer.parseInt(ret.getString("daoya"));

                } catch (Exception e) {
                    //Log.e("accountinfo",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    protected void cut(){

        //第一步检查机器状态
        //第二步设置速度
        //第三步设置刀压
        //第四步下载文件
        //上传文件
        this.getStatus();
    }

    protected void getStatus(){
        Cutter cutter=new Cutter();
        cutter.getStatus(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                String status=data.getString("status");
                Log.e("resultcode",String.valueOf(resultcode));
                if(resultcode==0&&status.equals("00")){
                    setSpeed();
                }else {

                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.chulizhong)//内容
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog1.show();
                }
            }
        });
    }

    protected void setSpeed(){
        Cutter cutter=new Cutter();
        cutter.setSpeed(isudu,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    setPressure();
                }else {

                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.setspeedfail)//内容
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog1.show();
                }
            }
        });
    }

    protected void setPressure(){
        Cutter cutter=new Cutter();
        cutter.setPressure(idaoya,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    downloadfile();
                }else {

                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.setpressurefail)//内容
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog1.show();
                }
            }
        });
    }

    protected void downloadfile(){
        final CutdetailFragment that=this;
        String url=ApiConfig.getUploadPath()+"model/"+this.filename;

        FileDownload api=new FileDownload();
        api.download(url,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("downloadfile1",val);
                if(val=="-1"){
                    Log.e("downloadfileerr",val);
                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.downloadfail)//内容
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog1.show();
                }else {
                    upload(val);
                }
            }
        });
    }

    protected void upload(String filecontent){

        Cutter cutter=new Cutter();
        cutter.uploadFile(filecontent,new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    Toast.makeText(CutdetailFragment.this.getContext(),R.string.cutting,Toast.LENGTH_LONG).show();
                }else {

                    AlertDialog alertDialog1 = new AlertDialog.Builder(CutdetailFragment.this.getContext())
                            .setTitle(R.string.tishi)//标题
                            .setMessage(R.string.cutfail)//内容
                            .setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create();
                    alertDialog1.show();
                }
            }
        });
    }


}

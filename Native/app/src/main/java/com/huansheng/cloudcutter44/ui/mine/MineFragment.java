package com.huansheng.cloudcutter44.ui.mine;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huansheng.cloudcutter44.AboutMachineActivity;
import com.huansheng.cloudcutter44.ApiProviders.DeviceApi;
import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.CanshuActivity;
import com.huansheng.cloudcutter44.ContentActivity;
import com.huansheng.cloudcutter44.LangActivity;
import com.huansheng.cloudcutter44.LoginActivity;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.Mgr.PackageUtils;
import com.huansheng.cloudcutter44.MyAccountActivity;
import com.huansheng.cloudcutter44.PersonalDataActivity;
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.ui.cutdetail.CutdetailFragment;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MineFragment extends Fragment implements View.OnClickListener {

    private MineViewModel mViewModel;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    TextView account;
    TextView cutcount;
    TextView appversion;


    View personaldata;
    View signout;
    View canshusetting;
    View gybj;
    View myaccount;
    View lang;
    View xieyi;
    View manual;
    View isnotconnect;
    View isconnect;

    View refresh;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.mine_fragment, container, false);


        this.isnotconnect=root.findViewById(R.id.isnotconnect);
        this.isconnect=root.findViewById(R.id.isconnect);

        this.account=root.findViewById(R.id.account);
        this.cutcount=root.findViewById(R.id.cutcount);

        this.personaldata=root.findViewById(R.id.personaldata);
        this.personaldata.setOnClickListener(this);


        this.signout=root.findViewById(R.id.signout);
        this.signout.setOnClickListener(this);
        this.signout.setVisibility(View.INVISIBLE);

        this.myaccount=root.findViewById(R.id.myaccount);
        this.myaccount.setOnClickListener(this);


        this.canshusetting=root.findViewById(R.id.canshusetting);
        this.canshusetting.setOnClickListener(this);

        this.gybj=root.findViewById(R.id.gybj);
        this.gybj.setOnClickListener(this);


        this.lang=root.findViewById(R.id.lang);
        this.lang.setOnClickListener(this);


        this.xieyi=root.findViewById(R.id.xieyi);
        this.xieyi.setOnClickListener(this);

        this.manual=root.findViewById(R.id.manual);
        this.manual.setOnClickListener(this);


        this.refresh=root.findViewById(R.id.refresh);
        this.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isconnect.setVisibility(View.VISIBLE);
                isnotconnect.setVisibility(View.GONE);
                loadMachine();
            }
        });


        this.appversion=root.findViewById(R.id.appversion);
        String ver=PackageUtils.getAppName(this.getContext())+" v"+PackageUtils.getVersionName(this.getContext());
        this.appversion.setText(ver);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MineViewModel.class);
        // TODO: Use the ViewModel
        this.loadMember();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadMember();
    }

    protected void loadMember(){

        final MineFragment that=this;
        that.account.setText("");
        that.cutcount.setText("--");


        MemberApi memberapi=new MemberApi();
        final Map<String,String> json=new HashMap<String, String>();
        json.put("id", MainActivity.account_id);
        memberapi.accountinfo(json,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("ret");
                Log.e("accountinfo",val);
                //val="";
                try {

                    JSONObject ret=new JSONObject(val);
                    that.account.setText(ret.getString("name"));
                    that.cutcount.setText(ret.getString("cutcount"));


                    String isdevice=ret.getString("isdevice_value");
                    if(isdevice.equals("Y")){
                        signout.setVisibility(View.INVISIBLE);
                    }else{
                        signout.setVisibility(View.VISIBLE);
                    }
                    isconnect.setVisibility(View.VISIBLE);
                    isnotconnect.setVisibility(View.GONE);

                } catch (Exception e) {
              //
                    isconnect.setVisibility(View.GONE);
                    isnotconnect.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        });
    }



    private void loadMachine(){


        Cutter cutter=new Cutter();
        cutter.getMachineCode(new Handler(){
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                Bundle data = msg.getData();
                int resultcode=data.getInt("resultcode");
                final String fullcode=data.getString("fullcode");
                if(resultcode==0){//1==1||
                    String machineid=data.getString("machineid");
                    //machineid="34FFD8054E58383209670444";
                    DeviceApi api=new DeviceApi();
                    final Map<String,String> json=new HashMap<String, String>();
                    json.put("deviceno",machineid);
                    api.login(json,new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle data = msg.getData();
                            String val = data.getString("ret");

                            try {
                                JSONObject obj=new JSONObject(val);
                                String code=obj.getString("code");
                                String account_id=obj.getString("return");
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                                if(code.equals("0")){

                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("account_id",account_id);
                                    editor.putString("isdevice","1");
                                    editor.commit();
                                }else {
                                    String isdevice=prefs.getString("isdevice","0");
                                    if(isdevice.equals("1")){
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("account_id","0");
                                        editor.putString("isdevice","0");
                                        editor.commit();
                                    }
                                }

                            } catch (Exception e) {
                                //
                            }
                            loadMember();
                        }
                    });
                }else{
                    //checkLogin();

                    loadMember();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.canshusetting:
                                        Intent intent2=new Intent(MainActivity.Instance, CanshuActivity.class);
                                        startActivity(intent2);

//                final EditText inputServer = new EditText(this.getContext());
//                inputServer.setInputType(129);
//                inputServer.setHint(R.string.shumima);
//                inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
//                //inputServer.setBackgroundResource(R.drawable.shape_corner);
////                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
////                        ViewGroup.LayoutParams.MATCH_PARENT);
////                lp.setMargins(120, 120, 140, 0);
////                inputServer.setLayoutParams(lp);
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//                builder.setTitle(R.string.szcsyzmm).setView(inputServer)
//                        .setNegativeButton(R.string.quxiao, null);
//                builder.setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String password=inputServer.getText().toString();
//
//                        MemberApi memberapi=new MemberApi();
//                        final Map<String,String> json=new HashMap<String, String>();
//                        json.put("account_id", MainActivity.account_id);
//                        json.put("password", password);
//                        memberapi.checkpws(json,new Handler() {
//                            @Override
//                            public void handleMessage(Message msg) {
//                                super.handleMessage(msg);
//                                Bundle data = msg.getData();
//                                String val = data.getString("ret");
//
//                                try {
//
//                                    JSONObject ret=new JSONObject(val);
//                                    if(ret.getString("code").equals("0")){
//                                        Intent intent2=new Intent(MainActivity.Instance, CanshuActivity.class);
//                                        startActivity(intent2);
//                                    }else{
//                                        Toast.makeText(MineFragment.this.getContext(), MainActivity.Instance.getApplication().getString(R.string.mimacuo),Toast.LENGTH_LONG  ).show();
//                                    }
//
//                                } catch (Exception e) {
//
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//
//                    }
//                });
//                builder.show();


                break;
            case R.id.myaccount:
                MyAccountActivity.ShowType=0;
                Intent intentmy=new Intent(MainActivity.Instance, MyAccountActivity.class);
                startActivity(intentmy);

                break;
            case R.id.personaldata:

                Intent intent=new Intent(MainActivity.Instance, PersonalDataActivity.class);
                startActivity(intent);

                break;
            case R.id.gybj:

                Intent intent3=new Intent(MainActivity.Instance, AboutMachineActivity.class);
                startActivity(intent3);

                break;
            case R.id.lang:

                Intent intent4=new Intent(MainActivity.Instance, LangActivity.class);
                startActivity(intent4);

                break;
            case R.id.xieyi:

                Intent intent5=new Intent(MainActivity.Instance, ContentActivity.class);
                intent5.putExtra("keycode","agreement");
                intent5.putExtra("title",getResources().getString(R.string.xieyi));
                startActivity(intent5);

                break;
            case R.id.manual:

                Intent intent6=new Intent(MainActivity.Instance, ContentActivity.class);
                intent6.putExtra("keycode","manual");
                intent6.putExtra("title",getResources().getString(R.string.Manual));
                startActivity(intent6);

                break;
            case R.id.signout:

                AlertDialog alertDialog1 = new AlertDialog.Builder(MineFragment.this.getContext())
                        .setTitle(R.string.tishi)//标题
                        .setMessage(R.string.qrtcdl)//内容
                        .setPositiveButton(R.string.qr, new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.Instance) ;
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("account_id","0");
                                editor.commit();
                                MainActivity.account_id="0";
                                Intent intent=new Intent(MainActivity.Instance, LoginActivity.class);
                                //执行意图  
                                MainActivity.Instance.startActivity(intent);
                            }
                        }).setNegativeButton(R.string.quxiao, new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                alertDialog1.show();

                break;
            default:
                return;
        }
    }
}

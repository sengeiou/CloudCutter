package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.ui.home.HomeFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CanshuActivity extends AppCompatActivity {

    TextView daoyaname;
    TextView daoya;

    EditText sudu;
    EditText x;
    EditText y;

    EditText width;
    Switch spacing;
    TextView banbenhao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canshu);

        this.daoyaname=findViewById(R.id.daoyaname);
        this.daoya=findViewById(R.id.daoya);
        TextView setsudu=findViewById(R.id.setsudu);
        setsudu.setText(this.getString(R.string.setsudu)+"(0-800，"+this.getString(R.string.moren)+"200)");

        TextView namewidth=findViewById(R.id.namewidth);
        namewidth.setText(this.getString(R.string.fukuan)+"(0-1300mm，"+this.getString(R.string.moren)+"190mm)");


        TextView namegearx=findViewById(R.id.namegearx);
        namegearx.setText(this.getString(R.string.chulunbi)+" X");

        TextView namegeary=findViewById(R.id.namegeary);
        namegeary.setText(this.getString(R.string.chulunbi)+" Y");

        this.sudu=findViewById(R.id.sudu);
        this.x=findViewById(R.id.x);
        this.y=findViewById(R.id.y);
        this.width=findViewById(R.id.width);
        this.spacing=findViewById(R.id.spacing);
        this.banbenhao=findViewById(R.id.banbenhao);

        loadMember();
        getGear();
    }

    protected void loadMember(){


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
                try {

                    JSONObject ret=new JSONObject(val);
                    String checking=ret.getString("checking");
                    if(checking.equals("1")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname1"));
                    }else if(checking.equals("2")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname2"));
                    }else if(checking.equals("3")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname3"));

                    }else if(checking.equals("4")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname4"));

                    }else if(checking.equals("5")){
                        CanshuActivity.this.daoyaname.setText(ret.getString("daoyaname5"));

                    }
                    CanshuActivity.this.daoya.setText(ret.getString("daoya"));
                    CanshuActivity.this.sudu.setText(ret.getString("sudu"));

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    private void getGear(){
        Cutter cutter=new Cutter();
        cutter.getGear(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int x=data.getInt("x");
                int y=data.getInt("y");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    CanshuActivity.this.x.setText(String.valueOf( x));
                    CanshuActivity.this.y.setText(String.valueOf( y));
                }else{
                    CanshuActivity.this.x.setText("-");
                    CanshuActivity.this.y.setText("-");
                    CanshuActivity.this.x.setEnabled(false);
                    CanshuActivity.this.y.setEnabled(false);
                }
                getWidth();
            }
        });
    }
    private void getWidth(){
        Cutter cutter=new Cutter();
        cutter.getWidth(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int width=data.getInt("width");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    CanshuActivity.this.width.setText(String.valueOf( width));
                }else{
                    CanshuActivity.this.width.setText("-");
                    CanshuActivity.this.width.setEnabled(false);
                }
                getSpacing();
            }
        });
    }
    private void getSpacing(){
        Cutter cutter=new Cutter();
        cutter.getSpacing(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                int spacing=data.getInt("spacing");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    CanshuActivity.this.spacing.setChecked(spacing==1);
                }
                getVersion();
            }
        });
    }
    private void getVersion(){
        Cutter cutter=new Cutter();
        cutter.getVersion(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String version=data.getString("version");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    CanshuActivity.this.banbenhao.setText(( version));
                }
            }
        });
    }

}

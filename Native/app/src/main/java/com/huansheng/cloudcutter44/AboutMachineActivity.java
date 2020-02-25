package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.ApiProviders.InstApi;
import com.huansheng.cloudcutter44.Mgr.Cutter;
import com.huansheng.cloudcutter44.Mgr.Util;

import java.util.HashMap;
import java.util.Map;

public class AboutMachineActivity extends AppCompatActivity {

    SimpleDraweeView qrcode;
    TextView banbenhao;
    TextView machineid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_machine);

        setTitle(R.string.gybj);

        this.qrcode=findViewById(R.id.qrcode);
        this.banbenhao=findViewById(R.id.banbenhao);
        this.machineid=findViewById(R.id.machineid);

        getVersion();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Util.hideBottomMenu(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                    AboutMachineActivity.this.banbenhao.setText(( version));
                }
                getMachineId();
            }
        });
    }

    private void getMachineId(){
        Cutter cutter=new Cutter();
        cutter.getMachineCode(new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                final String machineid=data.getString("machineid");
                int resultcode=data.getInt("resultcode");
                if(resultcode==0){
                    AboutMachineActivity.this.machineid.setText(( machineid));
                    InstApi api=new InstApi();
                    Map<String,String> json=new HashMap<String,String>();
                    json.put("str",machineid);
                    api.qrcode(json,new Handler(){
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle data = msg.getData();
                            String url=ApiConfig.getLogUrl()+machineid+".png";
                            AboutMachineActivity.this.qrcode.setImageURI(url);
                        }
                    });
                }
            }
        });
    }
}

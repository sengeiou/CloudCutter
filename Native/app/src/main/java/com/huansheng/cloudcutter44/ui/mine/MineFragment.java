package com.huansheng.cloudcutter44.ui.mine;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huansheng.cloudcutter44.ApiProviders.MemberApi;
import com.huansheng.cloudcutter44.MainActivity;
import com.huansheng.cloudcutter44.R;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.mine_fragment, container, false);

        this.account=root.findViewById(R.id.account);
        this.cutcount=root.findViewById(R.id.cutcount);

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
                    that.account.setText(ret.getString("name"));
                    that.cutcount.setText(ret.getString("cutcount"));

                } catch (Exception e) {
                    Log.e("modellist2",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.canshusetting:

                Toast.makeText(this.getContext(),"aa",Toast.LENGTH_SHORT).show();

                break;
            default:
                return;
        }
    }
}

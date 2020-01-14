package com.huansheng.cloudcutter44.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.ui.components.UrlImageView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private  UrlImageView testimg;
    private  Button btn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        testimg=root.findViewById(R.id.testimg);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        this.btn=root.findViewById(R.id.button);
        this.btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast toast=Toast.makeText(getContext(),"Toast提示消息",Toast.LENGTH_SHORT    );
                toast.show();
                testimg.setImageURL("https://www.baidu.com/img/baidu_resultlogo@2.png");
            }
        });
        return root;
    }
}
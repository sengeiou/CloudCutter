package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.huansheng.cloudcutter44.ui.cutdetail.CutdetailFragment;

public class CutdetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cutdetail_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CutdetailFragment.newInstance())
                    .commitNow();
        }


        //Intent intent = getIntent();
        //setTitle(intent.getStringExtra("modelname"));
    }
}

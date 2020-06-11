package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.huansheng.cloudcutter44.Mgr.Util;
import com.huansheng.cloudcutter44.ui.cutdetail.CutdetailFragment;

public class CutdetailActivity extends AppCompatActivity {

    public  static CutdetailActivity Instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cutdetail_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CutdetailFragment.newInstance())
                    .commitNow();
        }
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("modelname"));
        CutdetailActivity.Instance=this;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.hideBottomMenu(this);
    }
}

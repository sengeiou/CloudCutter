package com.huansheng.cloudcutter44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import com.huansheng.cloudcutter44.ApiProviders.ApiConfig;
import com.huansheng.cloudcutter44.Mgr.Util;

public class ContentActivity extends AppCompatActivity {
    WebView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent=getIntent();

        this.content=findViewById(R.id.content);

        setTitle( intent.getStringExtra("title") );
        String keycode=intent.getStringExtra("keycode");
        String url= ApiConfig.getApiUrl()+"inst/showcontent?keycode="+keycode+"&lang="+MainActivity.LangCode;

        Log.e("url",url);

        this.content.loadUrl(url);
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

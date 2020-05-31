package com.jiyun.zhulong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.jiyun.zhulong.R;

public class WebActivity extends AppCompatActivity {

    private WebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    private void initView() {
        mWeb = (WebView) findViewById(R.id.web);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWeb.loadUrl(url);
        mWeb.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}

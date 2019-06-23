package com.siddharth.searchrepogithub.project_web_view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.siddharth.searchrepogithub.Constants;
import com.siddharth.searchrepogithub.R;
import com.siddharth.searchrepogithub.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding mActivityWebViewBinding;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWebViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_web_view);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            link = extras.getString(Constants.PROJECT_LINK);
        }

        if (link != null) {

            mActivityWebViewBinding.webview.setWebViewClient(new WebViewClient());
            mActivityWebViewBinding.webview.loadUrl(link);
        }

        mActivityWebViewBinding.toolbar.imageViewBack.setOnClickListener(view -> onBackPressed());
    }

}

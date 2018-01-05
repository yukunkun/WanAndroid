package com.yukunkun.wanandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_me)
    ImageView mIvback;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.rl)
    RelativeLayout mRl;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    private String mUrl;

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        String title = getIntent().getStringExtra("title");
        mTvTitle.setText(title);
        mTvTitle.setSelected(true);
        mUrl = getIntent().getStringExtra("url");
        WebSettings webSettings = mWebview.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        mWebview.loadUrl(mUrl);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressbar.setVisibility(View.GONE);
            }
        });

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.iv_me, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_me:
                finish();
                break;
            case R.id.iv_share:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
                startActivity(Intent.createChooser(textIntent, "分享"));
                break;
        }
    }
}

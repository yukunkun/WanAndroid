package com.yukunkun.wanandroid.activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yukunkun.wanandroid.MyApp;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.base.BaseActivity;
import com.yukunkun.wanandroid.common.Constanct;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class CollectActivity extends BaseActivity {

    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.iv_me)
    ImageView mIvBack;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.sw)
    SwipeRefreshLayout mSw;

    @Override
    public int getLayout() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        getCollectInfo();
    }

    private void getCollectInfo() {
        Log.i("name",MyApp.getUesrInfo().getUsername());
        Log.i("pass",MyApp.getUesrInfo().getPassword());
        OkHttpUtils.get().url(Constanct.COLLECTLIST)
                .addHeader("loginUserName", MyApp.getUesrInfo().getUsername())
                .addHeader("loginUserPassword",MyApp.getUesrInfo().getPassword())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("res",response);
                    }
                });
    }

    @OnClick({R.id.iv_search, R.id.iv_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                break;
            case R.id.iv_me:
                finish();
                break;
        }
    }
}

package com.yukunkun.wanandroid.activity;
import android.content.SharedPreferences;
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
    String cook="loginUserName=yukunkun; loginUserPassword=123456789ykk; JSESSIONID=45B8F78EFC5DA418267D0FC5923C3C98;";

    @Override
    public int getLayout() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        getCollectInfo();
    }

    private void getCollectInfo() {
        SharedPreferences pref = getSharedPreferences("cookie",MODE_PRIVATE);
        String cookie = pref.getString("cookie","");//第二个参数为默认值
        Log.i("cookie",cookie);

        OkHttpUtils.get().url(Constanct.COLLECTLIST)
                .addHeader("Cookie", cookie)
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

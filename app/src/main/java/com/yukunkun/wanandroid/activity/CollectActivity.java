package com.yukunkun.wanandroid.activity;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.adapter.CollectAdapter;
import com.yukunkun.wanandroid.base.BaseActivity;
import com.yukunkun.wanandroid.common.Constanct;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    private CollectAdapter mCollectAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
//        mCollectAdapter = new CollectAdapter();
//        mRecyclerview.setAdapter(mCollectAdapter);
        getCollectInfo();
        setListener();
    }

    private void setListener() {
        mSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSw.setRefreshing(false);
            }
        });
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

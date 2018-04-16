package com.yukunkun.wanandroid.activity;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yukunkun.wanandroid.MyApp;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.adapter.CollectAdapter;
import com.yukunkun.wanandroid.base.BaseActivity;
import com.yukunkun.wanandroid.common.Constanct;
import com.yukunkun.wanandroid.enerty.CollectInfo;
import com.yukunkun.wanandroid.utils.ActivityUtils;
import com.yukunkun.wanandroid.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;


public class CollectActivity extends BaseActivity {

    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.iv_me)
    ImageView mIvBack;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.sw)
    SwipeRefreshLayout mSw;
    int page=0;
    private List<CollectInfo> collectInfos=new ArrayList<>();
    CollectAdapter mCollectAdapter;


    @Override
    public int getLayout() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mCollectAdapter = new CollectAdapter(collectInfos,this);
        mRecyclerview.setAdapter(mCollectAdapter);
        getCollectInfo();
        setListener();
    }

    private void setListener() {
        mSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSw.setRefreshing(false);
                collectInfos.clear();
                page=0;
                getCollectInfo();
            }
        });
    }

    private void getCollectInfo() {

        OkHttpUtils.initClient(MyApp.getMyApp().getOkHttpCliet()).get().url(Constanct.COLLECTLIST+page+"/json")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToast(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.optInt("errorCode")==0){
                                JSONObject data = jsonObject.optJSONObject("data");
                                JSONArray datas = data.optJSONArray("datas");
                                Gson gson=new Gson();
                                List<CollectInfo> collectInfo = gson.fromJson(datas.toString(),new TypeToken<List<CollectInfo>>(){}.getType());
                                collectInfos.addAll(collectInfo);
                                mCollectAdapter.notifyDataSetChanged();
                            }else {
                                ToastUtils.showToast("no collect");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.iv_search, R.id.iv_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                ActivityUtils.startSearchkActivity(this,"");
                break;
            case R.id.iv_me:
                finish();
                break;
        }
    }
}

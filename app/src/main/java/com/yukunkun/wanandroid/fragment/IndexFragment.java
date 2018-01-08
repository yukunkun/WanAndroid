package com.yukunkun.wanandroid.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.adapter.IndexAdapter;
import com.yukunkun.wanandroid.base.BaseFragment;
import com.yukunkun.wanandroid.common.Constanct;
import com.yukunkun.wanandroid.enerty.FeedInfo;
import com.yukunkun.wanandroid.utils.ActivityUtils;
import com.yukunkun.wanandroid.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by yukun on 18-1-4.
 */

public class IndexFragment extends BaseFragment {
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int page=0;
    List<FeedInfo> feedLists=new ArrayList<>();
    private IndexAdapter mIndexAdapter;

    public static IndexFragment getInstance() {
        return new IndexFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.index_fragment;
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(layoutManager);
        mIndexAdapter = new IndexAdapter(feedLists,getContext());
        mRecyclerview.setAdapter(mIndexAdapter);
        setListener();
        mRefreshLayout.setPrimaryColors(Color.WHITE,Color.BLUE);
        mRefreshLayout.setBackgroundResource(R.color.colorPrimary);
        getInfo();
    }

    private void getInfo() {
        OkHttpUtils.get().url(Constanct.FEEDURL+page+"/json").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("---",e.toString());
                ToastUtils.showToast("请求错误！");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optInt("errorCode")==0){
                        JSONObject data=jsonObject.optJSONObject("data");
                        JSONArray jsonArray = data.optJSONArray("datas");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            FeedInfo feedInfo=new FeedInfo();
                            feedInfo.setId(jsonObject1.getInt("id"));
                            feedInfo.setTitle(jsonObject1.getString("title"));
                            feedInfo.setChapterId(jsonObject1.getInt("chapterId"));
                            feedInfo.setChapterName(jsonObject1.getString("chapterName"));
                            feedInfo.setLink(jsonObject1.getString("link"));
                            feedInfo.setAuthor(jsonObject1.getString("author"));
                            feedInfo.setNiceDate(jsonObject1.getString("niceDate"));
                            feedInfo.setCollect(jsonObject1.getBoolean("collect"));
                            feedLists.add(feedInfo);
                        }
                        mIndexAdapter.notifyDataSetChanged();
                        if(page==0){
                            mRefreshLayout.finishRefresh();
                        }else {
                            mRefreshLayout.finishLoadmore();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setListener() {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=0;
                feedLists.clear();
                getInfo();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getInfo();

            }
        });
    }


    @OnClick({R.id.iv_search, R.id.refreshLayout,R.id.iv_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                ActivityUtils.startSearchkActivity(getContext(),"");
                break;
            case R.id.iv_me:
                ActivityUtils.startMeActivity(getContext());
                break;
        }
    }
}

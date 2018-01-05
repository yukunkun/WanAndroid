package com.yukunkun.wanandroid.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.adapter.IndexAdapter;
import com.yukunkun.wanandroid.adapter.SearchAdapterAdapter;
import com.yukunkun.wanandroid.base.BaseActivity;
import com.yukunkun.wanandroid.common.Constanct;
import com.yukunkun.wanandroid.enerty.SearchInfo;
import com.yukunkun.wanandroid.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.smartLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ed_text)
    EditText mEdText;
    private int page;
    private List<SearchInfo.DataBean.DatasBean> searchInfos=new ArrayList<>();
    private SearchAdapterAdapter mIndexAdapter;


    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        String key = getIntent().getStringExtra("key");
        if (key.equals("")) {

        } else {
            mEdText.setText(key);
            search(key);
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mIndexAdapter = new SearchAdapterAdapter(searchInfos,this);
        mRecyclerview.setAdapter(mIndexAdapter);
        mRecyclerview.setAdapter(mIndexAdapter);
        mRefreshLayout.setPrimaryColors(Color.GRAY,Color.BLUE);
        mRefreshLayout.setBackgroundResource(R.color.colorPrimary);
//        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshHeader(new BezierCircleHeader(this));
        setListener();
    }

    private void setListener() {
        mEdText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //完成自己的事件
                    String edMsg = mEdText.getText().toString();
                    if(edMsg.length()>0){
                        searchInfos.clear();
                        mIndexAdapter.notifyDataSetChanged();
                        search(edMsg);
                    }
                }
                return false;
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                String edMsg = mEdText.getText().toString();
                if(edMsg.length()>0){
                    searchInfos.clear();
                    mIndexAdapter.notifyDataSetChanged();
                    search(edMsg);
                }
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                String edMsg = mEdText.getText().toString();
                if(edMsg.length()>0){
                    search(edMsg);
                }

            }
        });
    }

    private void search(String edMsg) {
        OkHttpUtils.post().url(Constanct.SEARCH+page+"/json").addParams("k",edMsg).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson=new Gson();
                SearchInfo searchInfo = gson.fromJson(response, SearchInfo.class);
                if(searchInfo.getErrorCode()==-1){
                    ToastUtils.showToast(searchInfo.getErrorMsg()+"");
                }else {
                    List<SearchInfo.DataBean.DatasBean> datas = searchInfo.getData().getDatas();
                    if(datas.size()==0){
                        ToastUtils.showToast("没有搜索到数据");
                        return;
                    }
                    searchInfos.addAll(datas);
                    mIndexAdapter.notifyDataSetChanged();
                    if(page==0){
                        mRefreshLayout.finishRefresh();
                    }else {
                        mRefreshLayout.finishLoadmore();
                    }
                }
            }
        });
    }


    @OnClick({R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;

        }
    }

}

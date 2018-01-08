package com.yukunkun.wanandroid.activity;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.adapter.KnowListViewAdapter;
import com.yukunkun.wanandroid.adapter.KnowledgeListAdapter;
import com.yukunkun.wanandroid.base.BaseActivity;
import com.yukunkun.wanandroid.common.Constanct;
import com.yukunkun.wanandroid.enerty.KnowledgeInfo;
import com.yukunkun.wanandroid.enerty.KnowledgeListInfo;
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
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.Call;

public class KnowListActivity extends BaseActivity {

    @BindView(R.id.titles)
    TextView mTitle;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.iv_me)
    ImageView mIvList;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.drawlayout)
    DrawerLayout mDrawlayout;
    private int page=0;
    private KnowledgeInfo.DataBean mKnowlist;
    private KnowListViewAdapter mKnowListAdapter;
    private KnowledgeListAdapter mKnowledgeAdapter;
    private KnowledgeListInfo mKnowledgeListInfo;
    private List<KnowledgeListInfo.DataBean.DatasBean> mKlInfos=new ArrayList<>();
    private int cid;

    @Override
    public int getLayout() {
        mKnowlist = (KnowledgeInfo.DataBean) getIntent().getSerializableExtra("knowlist");
        return R.layout.activity_know_list;
    }

    @Override
    public void initView() {
        mTitle.setText(mKnowlist.getName()+"");
        List<KnowledgeInfo.DataBean.ChildrenBean> children = mKnowlist.getChildren();
        mKnowListAdapter = new KnowListViewAdapter(children,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mKnowledgeAdapter = new KnowledgeListAdapter(this,mKlInfos);
        mRecyclerview.setAdapter(mKnowledgeAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(mListview);
        mRefreshLayout.setPrimaryColors(Color.GRAY,Color.BLUE);
        mRefreshLayout.setBackgroundResource(R.color.colorPrimary);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        if(mKnowlist.getChildren().size()==0){
            return;
        }
        mListview.setAdapter(mKnowListAdapter);
        setListener();
        cid=mKnowlist.getChildren().get(0).getId();
        getInfo(cid);
    }

    private void getInfo(int pcid) {
        OkHttpUtils.get().url(Constanct.KNOWLEDGELISTURL+page+"/json?cid="+pcid).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(response);
                    if(jsonObject.optInt("errorCode")==0){
                        Gson gson = new Gson();
                        mKnowledgeListInfo = gson.fromJson(response, KnowledgeListInfo.class);
                        mKlInfos.addAll(mKnowledgeListInfo.getData().getDatas());
                        mKnowledgeAdapter.notifyDataSetChanged();
                        if(page==0){
                            mRefreshLayout.finishRefresh();
                        }else {
                            mRefreshLayout.finishLoadmore();
                        }
                    }else {
                        ToastUtils.showToast(jsonObject.optString("errorMsg"));
                    }
                } catch (JSONException e) {

                }
            }
        });
    }

    private void setListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page=0;
                mKlInfos.clear();
                cid=mKnowlist.getChildren().get(position).getId();
                getInfo(cid);
                mDrawlayout.closeDrawer(Gravity.LEFT);
                mKnowListAdapter.setSelector(position);
                mKnowListAdapter.notifyDataSetChanged();
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=0;
                mKlInfos.clear();
                getInfo(cid);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getInfo(cid);

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
                if(mDrawlayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawlayout.closeDrawer(Gravity.LEFT);
                }else {
                    mDrawlayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
    }
}

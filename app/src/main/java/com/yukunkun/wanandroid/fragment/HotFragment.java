package com.yukunkun.wanandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.adapter.ListViewAdapter;
import com.yukunkun.wanandroid.base.BaseFragment;
import com.yukunkun.wanandroid.common.Constanct;
import com.yukunkun.wanandroid.enerty.CommonUrl;
import com.yukunkun.wanandroid.enerty.SearchHot;
import com.yukunkun.wanandroid.utils.ActivityUtils;
import com.yukunkun.wanandroid.utils.ToastUtils;
import com.yukunkun.wanandroid.views.NoScrolledListView;
import com.yukunkun.wanandroid.views.TagLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.Call;

/**
 * Created by yukun on 18-1-4.
 */

public class HotFragment extends BaseFragment {
    @BindView(R.id.taglayout)
    TagLayout mTaglayout;
    @BindView(R.id.listview)
    NoScrolledListView mListview;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.iv_me)
    ImageView mIvMe;
    @BindView(R.id.scrollview)
    ScrollView mScrollview;
    private SearchHot mJokeList;
    private CommonUrl mCommonUrl;

    public static HotFragment getInstance() {
        return new HotFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.hot_fragment;
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        getInfo();
        setListener();
        OverScrollDecoratorHelper.setUpOverScroll(mScrollview);
        mListview.setFocusable(false);
    }

    private void setListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityUtils.startDetaikActivity(getContext(), mCommonUrl.getData().get(position).getLink(),mCommonUrl.getData().get(position).getName());
            }
        });
    }

    private void getInfo() {
        OkHttpUtils.get().url(Constanct.SEARCHHOT).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String errorMsg = jsonObject.optString("errorMsg");
                    if (jsonObject.optInt("errorCode") == -1) {
                        ToastUtils.showToast(errorMsg);
                    } else {
                        Gson gson = new Gson();
                        mJokeList = gson.fromJson(response, SearchHot.class);
                        initTagLayout();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        OkHttpUtils.get().url(Constanct.COMMONURL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String errorMsg = jsonObject.optString("errorMsg");
                    if (jsonObject.optInt("errorCode") == -1) {
                        ToastUtils.showToast(errorMsg);
                    } else {
                        Gson gson = new Gson();
                        mCommonUrl = gson.fromJson(response, CommonUrl.class);

                        ListViewAdapter listAdapter = new ListViewAdapter(getContext(), mCommonUrl.getData());
                        mListview.setAdapter(listAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initTagLayout() {
        for (int i = 0; i < mJokeList.getData().size(); i++) {
            final TextView tv = new TextView(getContext());
            tv.setText(mJokeList.getData().get(i).getName());
            tv.setTextColor(getResources().getColor(R.color.color_ff2323));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(15, 15, 15, 15);
            tv.setPadding(12, 8, 12, 8);
            tv.setLayoutParams(params);
            tv.setClickable(true);
            tv.setTextSize(14);
            tv.setBackgroundResource(R.drawable.shape_tag_back);
            mTaglayout.addView(tv);
            //监听
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchHot.DataBean dataBean = mJokeList.getData().get(finalI);
                    ActivityUtils.startSearchkActivity(getContext(),dataBean.getName());
                }
            });
        }
    }

    @OnClick({R.id.iv_search, R.id.iv_me})
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

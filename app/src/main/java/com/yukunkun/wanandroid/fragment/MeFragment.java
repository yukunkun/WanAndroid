package com.yukunkun.wanandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yukunkun.wanandroid.MyApp;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.base.BaseFragment;
import com.yukunkun.wanandroid.enerty.UesrInfo;
import com.yukunkun.wanandroid.utils.ActivityUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by yukun on 18-1-4.
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.rl_collect)
    RelativeLayout mRlCollect;
    @BindView(R.id.rl_adoutus)
    RelativeLayout mRlAdoutus;
    @BindView(R.id.scrollview)
    ScrollView mScrollview;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    private boolean isLogin;
    public static MeFragment getInstance(Context context) {
        return new MeFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.me_fragment;
    }

    public void getType(boolean isLogin) {
        this.isLogin = isLogin;
        if (!isLogin) {
            mTvLogin.setText("登录");
        } else {
            mTvLogin.setText("退出登录");
        }
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        OverScrollDecoratorHelper.setUpOverScroll(mScrollview);
        //判断登录与否
        if( MyApp.uesrInfo==null){
            isLogin=false;
        }else {
            isLogin=true;
        }
        if (!isLogin) {
            mTvLogin.setText("登录");
        } else {
            mTvLogin.setText("退出登录");
        }
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getContext().getPackageName(), 0);
            String versionName = pi.versionName;
            mTvVersion.setText("V " + versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_collect, R.id.rl_adoutus, R.id.tv_login, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_collect:
                break;
            case R.id.rl_adoutus:
                AboutUsFragment aboutUsFragment=new AboutUsFragment();
                aboutUsFragment.show(getChildFragmentManager(),"");
                break;
            case R.id.tv_login:
                if (!isLogin) {
                    ActivityUtils.startLoginActivity(getContext());
                } else {
                    DataSupport.deleteAll(UesrInfo.class);
                    MyApp.uesrInfo=null;
                    ((Activity)getContext()).finish();
                }
                break;
            case R.id.iv_back:
                ((Activity) getContext()).finish();
                break;
        }
    }
}

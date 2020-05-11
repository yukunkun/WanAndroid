package com.yukunkun.wanandroid.base;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

/**
 * Created by yukun on 17-11-17.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayout(),null);
        ButterKnife.bind(this, inflate);
        initView(inflate, savedInstanceState);
        return inflate;
    }

    public abstract int getLayout() ;

    public abstract void initView(View inflate,Bundle savedInstanceState);
}

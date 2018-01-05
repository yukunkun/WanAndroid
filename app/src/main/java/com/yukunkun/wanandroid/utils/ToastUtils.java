package com.yukunkun.wanandroid.utils;

import android.widget.Toast;

import com.yukunkun.wanandroid.MyApp;

/**
 * Created by yukun on 18-1-4.
 */

public class ToastUtils {
    public static void showToast(String txt){
        Toast.makeText(MyApp.getMyApp(), txt, Toast.LENGTH_SHORT).show();
    }
}

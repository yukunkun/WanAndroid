package com.yukunkun.wanandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yukunkun.wanandroid.activity.DetailActivity;
import com.yukunkun.wanandroid.activity.LoginActivity;
import com.yukunkun.wanandroid.activity.MeActivity;
import com.yukunkun.wanandroid.activity.SearchActivity;

import java.util.Stack;

/**
 * Created by yukun on 18-1-5.
 */

public class ActivityUtils {

    public static void startMeActivity(Context context){
        Intent intent=new Intent(context, MeActivity.class);
        context.startActivity(intent);
    }
    public static void startLoginActivity(Context context){
        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    public static void startDetaikActivity(Context context,String url,String title){
        Intent intent=new Intent(context, DetailActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void startSearchkActivity(Context context,String key){
        Intent intent=new Intent(context, SearchActivity.class);
        intent.putExtra("key",key);
        context.startActivity(intent);
    }

}

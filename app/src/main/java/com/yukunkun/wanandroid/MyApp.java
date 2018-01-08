package com.yukunkun.wanandroid;

import android.util.Log;

import com.yukunkun.wanandroid.enerty.UesrInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by yukun on 18-1-4.
 */

public class MyApp extends LitePalApplication {
    public static MyApp myApp;
    public static UesrInfo uesrInfo;

    public static UesrInfo getUesrInfo() {
        return uesrInfo;
    }

    public static void setUesrInfo(UesrInfo userInfo) {
        uesrInfo=new UesrInfo();
        uesrInfo=userInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp=this;
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static MyApp getMyApp() {
        return myApp;
    }
}

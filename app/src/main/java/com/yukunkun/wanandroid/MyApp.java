package com.yukunkun.wanandroid;

import android.content.SharedPreferences;
import android.util.Log;

import com.yukunkun.wanandroid.adapter.CollectAdapter;
import com.yukunkun.wanandroid.enerty.UesrInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by yukun on 18-1-4.
 */

public class MyApp extends LitePalApplication {
    public static MyApp myApp;
    public static UesrInfo uesrInfo;
    private OkHttpClient mOkHttpClient;
    private final static HashMap<String, List<Cookie>> cookieStore=new HashMap<>();
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

    public  OkHttpClient getOkHttpCliet(){
        //[JSESSIONID=46E9A324BA18D392606B8D47FB0CA1D2; expires=Fri, 31 Dec 9999 23:59:59 GMT; path=/; httponly]
        SharedPreferences pref = getSharedPreferences("cookie",MODE_PRIVATE);
        String cookie = pref.getString("cookie","");//第二个参数为默认值
        Log.i("save_cookie",cookie);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //添加记录cookie的代码
        clientBuilder.cookieJar(new CookieJar() {

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                Log.i("cookie_set",cookies.toString());
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                Log.i("cookie_get",cookies.toString());
                return cookies != null ? cookies : new ArrayList<Cookie>();           }
        });
        mOkHttpClient = clientBuilder.build();
        return mOkHttpClient;
    }
}

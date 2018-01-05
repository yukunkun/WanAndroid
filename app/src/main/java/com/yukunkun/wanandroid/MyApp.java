package com.yukunkun.wanandroid;

import android.util.Log;

import com.yukunkun.wanandroid.enerty.UesrInfo;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by yukun on 18-1-4.
 */

public class MyApp extends LitePalApplication {
    public static MyApp myApp;
    public static UesrInfo uesrInfo;

    public static UesrInfo getUesrInfo() {
        return uesrInfo;
    }

    public static void setUesrInfo(UesrInfo uesrInfo) {
        MyApp.uesrInfo = uesrInfo;
        List<UesrInfo> all = DataSupport.findAll(UesrInfo.class);
        if(all.size()==0){
            uesrInfo.save();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp=this;
        List<UesrInfo> all = DataSupport.findAll(UesrInfo.class);
        if(all.size()!=0){
            MyApp.uesrInfo = all.get(0);
        }
        Log.i("---",uesrInfo.toString());
    }

    public static MyApp getMyApp() {
        return myApp;
    }
}

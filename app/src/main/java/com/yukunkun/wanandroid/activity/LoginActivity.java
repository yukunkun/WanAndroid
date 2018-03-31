package com.yukunkun.wanandroid.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yukunkun.wanandroid.MyApp;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.base.BaseActivity;
import com.yukunkun.wanandroid.common.Constanct;
import com.yukunkun.wanandroid.enerty.EventLoginType;
import com.yukunkun.wanandroid.enerty.UesrInfo;
import com.yukunkun.wanandroid.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.CookieStore;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_name)
    EditText mEtLoginName;
    @BindView(R.id.et_login_password)
    EditText mEtLoginPassword;
    @BindView(R.id.et_relogin_password)
    EditText mEtReloginPassword;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.rl_login_et)
    RelativeLayout mRlLoginEt;
    @BindView(R.id.tv_zuce)
    TextView mTvZuce;
    @BindView(R.id.tv_login)
    TextView mTvLogin;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.tv_zuce, R.id.tv_login,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_zuce:
                mEtReloginPassword.setVisibility(View.VISIBLE);
                newJoin();
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void login() {
        if(mEtLoginName.getText().toString().length()>0&&mEtLoginPassword.getText().toString().length()>0){
            OkHttpUtils.initClient(MyApp.getMyApp().getOkHttpCliet()).post().url(Constanct.LOGINURL).addParams("username",mEtLoginName.getText().toString()).addParams("password",mEtLoginPassword.getText().toString()).build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.i("userinfo",response);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String s = jsonObject.optString("errorMsg");
                                if(jsonObject.optString("errorCode").equals("-1")){
                                    ToastUtils.showToast(s);
                                }else {
                                    String data = jsonObject.optString("data");
                                    //save
                                    Gson gson=new Gson();
                                    UesrInfo uesrInfo = gson.fromJson(data, UesrInfo.class);
                                    saveNamePasword(uesrInfo);
                                    ToastUtils.showToast("登录成功");
                                    EventBus.getDefault().post(new EventLoginType(1));
                                    finish();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }else {
            ToastUtils.showToast("请输入帐号和密码");
        }
    }

    private void saveNamePasword(UesrInfo uesrInfo) {
        getCookiesStr();
        DataSupport.deleteAll(UesrInfo.class);
        uesrInfo.save();
        MyApp.uesrInfo=uesrInfo;
    }

    /**获取cookies*/
    public  String getCookiesStr(){
        String cookiesInfo = "";
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl) {
            CookieStore cookieStore = ((CookieJarImpl) cookieJar).getCookieStore();
            List<Cookie> cookies = cookieStore.getCookies();
            Log.i("cookies",cookies.toString());
            for(Cookie cookie : cookies){
                cookiesInfo = cookiesInfo + cookie.name() + "=" + cookie.value() + ";";
                Log.i("cookie",cookie.value());
//                saveToPre(cookies.toString());
                saveToPre(cookie.value());
            }
        }
        return cookiesInfo;
    }

    private void saveToPre(String cookiesInfo) {
        SharedPreferences cookie = getSharedPreferences("cookie", MODE_PRIVATE);
        SharedPreferences.Editor edit = cookie.edit();
        edit.putString("cookie",cookiesInfo);
        edit.commit();
    }

    private void newJoin() {
        if(mEtReloginPassword.getText().toString().length()==0){
            ToastUtils.showToast("请确认密码");
            return;
        }
        if(mEtLoginName.getText().toString().length()>0&&mEtLoginPassword.getText().toString().length()>0&&mEtReloginPassword.getText().toString().length()>0){
            OkHttpUtils.initClient(MyApp.getMyApp().getOkHttpCliet()).post().url(Constanct.ZHUCEURL)
                    .addParams("username",mEtLoginName.getText().toString())
                    .addParams("password",mEtLoginPassword.getText().toString()).
                    addParams("repassword",mEtReloginPassword.getText().toString()).build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("e",e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String s = jsonObject.optString("errorMsg");
                                if(jsonObject.optString("errorCode").equals("-1")){
                                    ToastUtils.showToast(s);
                                }else {
                                    //save
                                    Gson gson=new Gson();
                                    UesrInfo uesrInfo = gson.fromJson(response, UesrInfo.class);
                                    saveNamePasword(uesrInfo);
                                    ToastUtils.showToast("注册成功");
                                    EventBus.getDefault().post(new EventLoginType(1));
                                    finish();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }else {
            ToastUtils.showToast("请输入帐号和密码");
        }
    }
}

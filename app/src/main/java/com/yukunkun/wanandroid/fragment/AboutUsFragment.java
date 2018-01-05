package com.yukunkun.wanandroid.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.base.BaseCenterDialog;

/**
 * Created by yukun on 18-1-5.
 */

public class AboutUsFragment extends BaseCenterDialog {

    String htmlText="<body class=\"ke-content\"><p style=\"font-size:14px;font-family:&quot;margin-left:5px;background:#FFFFFF;\">QQ交流群：591683946</p><p style=\"font-size:14px;font-family:&quot;margin-left:5px;background:#FFFFFF;\">对本站的建议：<a target=\"_blank\" href=\"https://github.com/hongyangAndroid/wanandroid\" data-ke-src=\"https://github.com/hongyangAndroid/wanandroid\">我要反馈</a></p><p style=\"font-size:14px;font-family:&quot;margin-left:5px;background:#FFFFFF;\">最近更新：最近加了对分类的检索，尝试搜索“优化”即可发现；更换了网站主题。</p><p style=\"font-size:14px;font-family:&quot;margin-left:5px;background:#FFFFFF;text-align:right;\">欢迎收藏本站。</p></body>";
    @Override
    protected void initView(View inflate, Bundle savedInstanceState) {
        TextView viewById = (TextView) inflate.findViewById(R.id.tv_msg);
        viewById.setText(Html.fromHtml(htmlText));
    }

    @Override
    public int setLayout() {
        return R.layout.center_dialog;
    }
}

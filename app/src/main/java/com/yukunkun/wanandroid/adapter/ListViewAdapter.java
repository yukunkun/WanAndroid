package com.yukunkun.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.enerty.CommonUrl;

import java.util.List;
import java.util.Random;

/**
 * Created by yukun on 18-1-5.
 */

public class ListViewAdapter extends BaseAdapter{
    Context context;
    List<CommonUrl.DataBean> data;
    Random mRandom=new Random();
    Integer[] mList=new Integer[]{R.color.color_2b2b2b,R.color.color_2e4eef,R.color.colorPrimary,
            R.color.color_ff2323,R.color.color_ff01bb,R.color.color_ff4081,R.color.color_ffe100,
            R.color.color_494949,R.color.color_30f209,R.color.color_30f209};

    public ListViewAdapter(Context context, List<CommonUrl.DataBean> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.common_item,null);
        TextView viewById = (TextView) convertView.findViewById(R.id.tv_msg);
        viewById.setText(data.get(position).getName());
        int pos = mRandom.nextInt(mList.length);
        viewById.setTextColor(context.getResources().getColor(mList[pos]));
        return convertView;
    }

}

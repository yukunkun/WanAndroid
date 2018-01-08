package com.yukunkun.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.enerty.CommonUrl;
import com.yukunkun.wanandroid.enerty.KnowledgeInfo;

import java.util.List;
import java.util.Random;

/**
 * Created by yukun on 18-1-5.
 */

public class KnowListViewAdapter extends BaseAdapter{
    Context context;
    List<KnowledgeInfo.DataBean.ChildrenBean> data;
    int pos=0;
    public KnowListViewAdapter(List<KnowledgeInfo.DataBean.ChildrenBean> data, Context context) {
        this.context=context;
        this.data=data;
    }
    public void setSelector(int pos){
        this.pos=pos;
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
        if(pos==position){
            viewById.setTextColor(context.getResources().getColor(R.color.color_ff2323));
        }else {
            viewById.setTextColor(context.getResources().getColor(R.color.color_585858));
        }
        return convertView;
    }

}

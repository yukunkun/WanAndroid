package com.yukunkun.wanandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.enerty.KnowledgeInfo;
import com.yukunkun.wanandroid.utils.ActivityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yukun on 18-1-4.
 */

public class KnowledgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    KnowledgeInfo mFeedInfos;
    Context mContext;



    public KnowledgeAdapter(KnowledgeInfo feedInfos, Context context) {
        mFeedInfos = feedInfos;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.knowledge_item, null);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            final KnowledgeInfo.DataBean dataBean = mFeedInfos.getData().get(position);
            ((MyHolder) holder).mTvTitle.setText(dataBean.getName());
            List<KnowledgeInfo.DataBean.ChildrenBean> children = dataBean.getChildren();
            String child="";
            for (int i = 0; i < children.size(); i++) {
                child=child+children.get(i).getName()+" / ";
            }
            ((MyHolder) holder).mTvCatgroy.setText(child);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.startKnowListActivity(mContext,dataBean);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFeedInfos.getData().size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_catgroy)
        TextView mTvCatgroy;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

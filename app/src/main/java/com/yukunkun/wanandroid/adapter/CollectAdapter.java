package com.yukunkun.wanandroid.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yukunkun.wanandroid.MyApp;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.common.Constanct;
import com.yukunkun.wanandroid.enerty.CollectInfo;
import com.yukunkun.wanandroid.enerty.FeedInfo;
import com.yukunkun.wanandroid.utils.ActivityUtils;
import com.yukunkun.wanandroid.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yukun on 18-1-4.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<CollectInfo> mFeedInfos ;
    Context mContext;
    Random mRandom=new Random();
    Integer[] mList=new Integer[]{R.color.color_2b2b2b,R.color.color_2e4eef,R.color.colorPrimary,
            R.color.color_ff2323,R.color.color_ff01bb,R.color.color_ff4081,R.color.color_ffe100,
            R.color.color_30f209,R.color.color_30f209};


    public CollectAdapter(List<CollectInfo> feedInfos, Context context) {
        mFeedInfos = feedInfos;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.index_item, null);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyHolder){
            final CollectInfo datasBean = mFeedInfos.get(position);
            ((MyHolder) holder).mTvName.setText(datasBean.getAuthor());
            ((MyHolder) holder).mTvContent.setText(datasBean.getTitle());
            ((MyHolder) holder).mTvTime.setText(datasBean.getNiceDate());
            ((MyHolder) holder).mTvClass.setText(datasBean.getChapterName());
            int pos = mRandom.nextInt(mList.length);
            ((MyHolder) holder).mTvName.setTextColor(mContext.getResources().getColor(mList[pos]));
            ((MyHolder) holder).mIvCollect.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.startDetaikActivity(mContext,datasBean.getLink(),datasBean.getTitle());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFeedInfos.size();

    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.iv_collect)
        ImageView mIvCollect;
        @BindView(R.id.tv_class)
        TextView mTvClass;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

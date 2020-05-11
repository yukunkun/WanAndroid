package com.yukunkun.wanandroid.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yukunkun.wanandroid.MyApp;
import com.yukunkun.wanandroid.R;
import com.yukunkun.wanandroid.common.Constanct;
import com.yukunkun.wanandroid.enerty.FeedInfo;
import com.yukunkun.wanandroid.utils.ActivityUtils;
import com.yukunkun.wanandroid.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yukun on 18-1-4.
 */

public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<FeedInfo> mFeedInfos ;
    Context mContext;
    Random mRandom=new Random();
    Integer[] mList=new Integer[]{R.color.color_2b2b2b,R.color.color_2e4eef,R.color.colorPrimary,
            R.color.color_ff2323,R.color.color_ff01bb,R.color.color_ff4081,R.color.color_ffe100,
            R.color.color_30f209,R.color.color_30f209};


    public IndexAdapter(List<FeedInfo> feedInfos, Context context) {
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
            final FeedInfo datasBean = mFeedInfos.get(position);
            ((MyHolder) holder).mTvName.setText(datasBean.getAuthor());
            ((MyHolder) holder).mTvContent.setText(datasBean.getTitle());
            ((MyHolder) holder).mTvTime.setText(datasBean.getNiceDate());
            ((MyHolder) holder).mTvClass.setText(datasBean.getChapterName());
            int pos = mRandom.nextInt(mList.length);
            ((MyHolder) holder).mTvName.setTextColor(mContext.getResources().getColor(mList[pos]));
            if(datasBean.isCollect()){
                ((MyHolder) holder).mIvCollect.setImageResource(R.mipmap.collection_fill);
            }else {
                ((MyHolder) holder).mIvCollect.setImageResource(R.mipmap.collection);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.startDetaikActivity(mContext,datasBean.getLink(),datasBean.getTitle());
                }
            });
            ((MyHolder) holder).mIvCollect.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    if(MyApp.getUesrInfo()==null){
                        ActivityUtils.startLoginActivity(mContext);
                    }else {
                        SharedPreferences pref = mContext.getSharedPreferences("cookie",MODE_PRIVATE);
                        String cookie = pref.getString("cookie","");//第二个参数为默认值
                        if(!datasBean.isCollect()){
                            OkHttpUtils.post()
                                    .url(Constanct.COLLECTURL+datasBean.getId()+"/json")
                                    .addHeader("Cookie",cookie)
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {

                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            ToastUtils.showToast("收藏成功");
                                            mFeedInfos.get(position).setCollect(true);
                                            ((MyHolder) holder).mIvCollect.setImageResource(R.mipmap.collection_fill);
                                        }
                                    });
                        }else {
                            OkHttpUtils.post().url(Constanct.CANCELCOLURL+datasBean.getId()+"/json")
                                    .addHeader("Cookie",cookie)
                                    .addParams("originId",datasBean.getId()+"")
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            Log.i("collect",e.toString());
                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            ToastUtils.showToast("取消收藏");
                                            mFeedInfos.get(position).setCollect(false);
                                            ((MyHolder) holder).mIvCollect.setImageResource(R.mipmap.collection);
                                        }
                                    });
                        }
                    }
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

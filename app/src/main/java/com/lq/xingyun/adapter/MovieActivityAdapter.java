package com.lq.xingyun.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.model.entity.MovieDetailBean;
import com.lq.xingyun.ui.holder.MovieActivityViewHolder;

/**
 * Created by lenovo on 2016/8/8.
 */
public class MovieActivityAdapter extends RecyclerArrayAdapter<MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean> {
    public MovieActivityAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieActivityViewHolder(parent,getAllData());
    }
}

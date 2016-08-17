package com.lq.xingyun.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.model.entity.MovieBean;
import com.lq.xingyun.ui.holder.MovieFragmentViewHolder;

/**
 * Created by lenovo on 2016/8/2.
 */
public class MovieFragmentAdapter extends RecyclerArrayAdapter <MovieBean.DataBean.ResultsBean> {
    public MovieFragmentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieFragmentViewHolder(parent);
    }
}

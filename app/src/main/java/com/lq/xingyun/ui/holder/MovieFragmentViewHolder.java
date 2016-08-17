package com.lq.xingyun.ui.holder;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.lq.xingyun.R;
import com.lq.xingyun.model.entity.MovieBean;

/**
 * Created by lenovo on 2016/8/2.
 */
public class MovieFragmentViewHolder extends BaseViewHolder<MovieBean.DataBean.ResultsBean> {

    private ImageView movieImageView;
    private TextView movieTitleTv;
    private TextView movieUpdateInfoIv;

    public MovieFragmentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_moviefragment);
        movieImageView = $(R.id.item_moviefragment_imageview);
        movieTitleTv = $(R.id.item_moviefragment_title);
        movieUpdateInfoIv = $(R.id.item_moviefragment_updateinfo);
    }

    @Override
    public void setData(MovieBean.DataBean.ResultsBean data) {
        super.setData(data);
        //此处给UI绑定数据
        Glide.with(getContext())
                .load(data.getVerticalUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(movieImageView);
        movieTitleTv.setText(data.getTitle());
        movieUpdateInfoIv.setText("更新至第" + data.getUpInfo() + "集");
    }
}

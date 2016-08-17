package com.lq.xingyun.ui.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.lq.xingyun.R;
import com.lq.xingyun.model.entity.MovieDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/8.
 */
public class MovieActivityViewHolder extends BaseViewHolder<MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean> {
    private TextView tv_title;
    private TextView tv_title2;
    private List<MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean> list=new ArrayList<>();

    public MovieActivityViewHolder(ViewGroup parent, List<MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean> list) {
        super(parent, R.layout.item_movielist);
        tv_title = $(R.id.tv_title);
        tv_title2 = $(R.id.tv_title2);
        this.list=list;
    }

    @Override
    public void setData(MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean data) {
        super.setData(data);
        tv_title.setText((list.size()-getLayoutPosition()+1)+"");
        if (getLayoutPosition() == 1) {
            tv_title2.setVisibility(View.VISIBLE);
        } else {
            tv_title2.setVisibility(View.INVISIBLE);
        }
    }
}

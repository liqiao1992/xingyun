package com.lq.xingyun.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.model.entity.ArticleBean;
import com.lq.xingyun.ui.holder.ArticleFragmentViewHolder;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ArticleFragmentAdapter extends RecyclerArrayAdapter<ArticleBean> {

    public ArticleFragmentAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticleFragmentViewHolder(parent);
    }

}

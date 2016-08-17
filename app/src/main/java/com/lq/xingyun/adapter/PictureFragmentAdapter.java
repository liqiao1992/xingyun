package com.lq.xingyun.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.model.entity.PictureBean;
import com.lq.xingyun.ui.holder.PictureFragmentViewHolder;

/**
 * Created by lenovo on 2016/8/5.
 */
public class PictureFragmentAdapter extends RecyclerArrayAdapter<PictureBean.PictureGirl> {
    public PictureFragmentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureFragmentViewHolder(parent);
    }
}

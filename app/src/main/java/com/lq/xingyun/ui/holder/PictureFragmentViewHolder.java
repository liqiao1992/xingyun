package com.lq.xingyun.ui.holder;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.lq.xingyun.R;
import com.lq.xingyun.model.entity.PictureBean;
import com.lq.xingyun.widget.RatioImageView;


/**
 * Created by lenovo on 2016/8/5.
 */
public class PictureFragmentViewHolder extends BaseViewHolder<PictureBean.PictureGirl> {

    private RatioImageView imageView;
    private TextView decTv;

    public PictureFragmentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_picturefragment);
        imageView = $(R.id.item_picturefragment_imageview);
        imageView.setOriginalSize(50, 50);
        decTv = $(R.id.item_picturefragment_dec_tv);
    }


    @Override
    public void setData(PictureBean.PictureGirl data) {
        super.setData(data);
        //给UI绑定数据
        String text =data.getDesc();
        decTv.setText(text);
        Glide.with(getContext())
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}

package com.lq.xingyun.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lq.xingyun.R;
import com.lq.xingyun.presenter.BasePresenter;

/**
 * Created by lenovo on 2016/8/5.
 */
public class PictureDetailActivity extends BaseActivity {
    private String title;
    private ImageView imageView;
    private String imageUrl;

    @Override
    public int getToolBarId() {
        return R.id.activity_picture_detail_toolbar;
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        imageView = (ImageView) findViewById(R.id.activity_picture_detail_imageview);
        Glide.with(this).load(imageUrl).centerCrop().placeholder(R.mipmap.ic_loading).into(imageView);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_picture_detail;
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void getIntentValue() {
        super.getIntentValue();
        title = getIntent().getStringExtra("imageTitle");
        imageUrl = getIntent().getStringExtra("imageUrl");
    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}

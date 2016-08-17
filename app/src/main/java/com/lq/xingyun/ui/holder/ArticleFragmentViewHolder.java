package com.lq.xingyun.ui.holder;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.lq.xingyun.R;
import com.lq.xingyun.model.entity.ArticleBean;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ArticleFragmentViewHolder extends BaseViewHolder<ArticleBean> {
    private TextView titleTv;
    private TextView authorTv;
    private TextView contentTv;
    private RelativeLayout relativeLayout;

    public ArticleFragmentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_articlefragment);
        titleTv=$(R.id.item_articlefragment_title_tv);
        authorTv=$(R.id.item_articlefragment_author_tv);
        contentTv=$(R.id.item_articlefragment_conten_tv);
        relativeLayout=$(R.id.item_articlefragment_rl);
    }

    @Override
    public void setData(ArticleBean data) {
        super.setData(data);
        titleTv.setText(data.title);
        contentTv.setText(Html.fromHtml(data.text));
        if (TextUtils.isEmpty(data.auth)) {
            relativeLayout.setVisibility(View.GONE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            authorTv.setText(data.auth);
        }
    }
}

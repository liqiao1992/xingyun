package com.lq.xingyun.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.lq.xingyun.R;
import com.lq.xingyun.model.entity.ArticleBean;
import com.lq.xingyun.presenter.ArticleActivityPresenter;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.ui.view.IArticleActivityView;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ArticleDetailActivity extends BaseActivity implements IArticleActivityView {


    private ArticleBean articleBean;
    private TextView contentTv;

    /**
     * 获取传值
     */
    @Override
    public void getIntentValue() {
        super.getIntentValue();
        articleBean = getIntent().getParcelableExtra("article");
    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        getSupportActionBar().setTitle(articleBean.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public int getToolBarId() {
        return R.id.activity_article_detail_toolbar;
    }

    @Override
    public BasePresenter getPresenter() {
        return new ArticleActivityPresenter();
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        contentTv = (TextView) findViewById(R.id.activity_article_detail_content_tv);
        ((ArticleActivityPresenter) mPresenter).getArticleDetailData(articleBean.href);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_article_detail;
    }

    /**
     * 更新UI
     * @param s
     */
    @Override
    public void setArticleDetailData(String s) {
        if (contentTv == null) return;
        contentTv.setText(Html.fromHtml(s));
    }
}

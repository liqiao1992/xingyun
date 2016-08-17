package com.lq.xingyun.ui.view;

import com.lq.xingyun.model.entity.ArticleBean;

import java.util.List;

/**
 * Created by lenovo on 2016/7/28.
 */
public interface IArticleFragmentView  extends IBaseView {

    void setArticleData(List<ArticleBean> data, boolean isRefresh);

    void loadMoreArticleDataError();

    void refreshArticleDataError();
}

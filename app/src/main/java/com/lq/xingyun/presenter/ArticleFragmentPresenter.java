package com.lq.xingyun.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.ArticleFragmentModel;
import com.lq.xingyun.model.entity.ArticleBean;
import com.lq.xingyun.model.impl.IArticleFragmentModel;
import com.lq.xingyun.ui.view.IArticleFragmentView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/7/28.
 */
public class ArticleFragmentPresenter extends BasePresenter<IArticleFragmentView> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private IArticleFragmentModel articleFragmentModel;
    private String baseUrl = "http://www.lz13.cn/lizhi/lizhiwenzhang.html";
    private int currentPage = 0;
    private boolean isLoading = false;
    public ArticleFragmentPresenter() {
        this.articleFragmentModel = new ArticleFragmentModel();
    }

    public void loadArticleData(final boolean isRefresh) {
        if(isLoading)
            return;
        isLoading=true;
        Map<String, Object> params = new HashMap<>();
        params.put("url", baseUrl);
        params.put("page", currentPage);
        articleFragmentModel.getArticles(params, new Callback<List<ArticleBean>>() {
            @Override
            public void onSuccess(List<ArticleBean> data) {
                isLoading=false;
                if (mView != null) {
                    mView.setArticleData(data,isRefresh);
                }
            }

            @Override
            public void onFailed() {
               isLoading=false;
                if (mView == null)
                    return;
                isLoading = false;
                if (isRefresh) {
                    Log.i("fuck","刷新数据");
                    mView.refreshArticleDataError();
                } else {
                    Log.i("fuck","加载更多数据");
                    if(currentPage==0){
                        mView.refreshArticleDataError();
                    }else {
                        mView.loadMoreArticleDataError();
                    }
                }
            }
        });

    }

    /**
     * 实现EasyRecyerView上拉加载更多的接口
     */
    @Override
    public void onLoadMore() {
        currentPage++;
        loadArticleData(false);
    }

    /**
     * 实现EasyRecyerView下拉刷新的接口
     */
    @Override
    public void onRefresh() {
        currentPage = 0;
        loadArticleData(true);
    }
}

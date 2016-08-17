package com.lq.xingyun.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.MovieFragmentModel;
import com.lq.xingyun.model.entity.MovieBean;
import com.lq.xingyun.model.impl.IMovieFragmentModel;
import com.lq.xingyun.ui.view.IMovieFragmentView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/8/3.
 */
public class MovieFragmentPresenter extends BasePresenter<IMovieFragmentView> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private IMovieFragmentModel movieFragmentModel;
    private int currentPage = 1;
    private boolean isLoading = false;

    public MovieFragmentPresenter() {
        movieFragmentModel = new MovieFragmentModel();
    }

    public void loadMovieData(final boolean isRefresh) {
        if (isLoading)
            return;
        isLoading = true;
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("cat", "");
        mapParams.put("rows", "12");
        mapParams.put("page", currentPage);
        mapParams.put("sort", "heat");
        mapParams.put("isFinish", "");
        movieFragmentModel.getMovies(mapParams, new Callback<List<MovieBean.DataBean.ResultsBean>>() {
            @Override
            public void onSuccess(List<MovieBean.DataBean.ResultsBean> data) {
                if (mView == null)
                    return;
                isLoading = false;
                mView.setMovieData(data, isRefresh);
            }

            @Override
            public void onFailed() {
                if (mView == null)
                    return;
                isLoading = false;
                if (isRefresh) {
                    Log.i("fuck","刷新数据");
                    mView.refreshMovieDataError();
                } else {
                    Log.i("fuck","加载更多数据");
                    if(currentPage==1){
                        mView.refreshMovieDataError();
                    }else {
                        mView.loadMoreMovieDataError();
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
        loadMovieData(false);
    }

    /**
     * 实现EasyRecyerView下拉刷新的接口
     */
    @Override
    public void onRefresh() {
        currentPage = 1;
        loadMovieData(true);
    }
}

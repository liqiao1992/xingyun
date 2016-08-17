package com.lq.xingyun.ui.view;

import com.lq.xingyun.model.entity.MovieBean;

import java.util.List;

/**
 * Created by lenovo on 2016/8/3.
 */
public interface IMovieFragmentView extends IBaseView {

    void setMovieData(List<MovieBean.DataBean.ResultsBean> movieData,boolean isRefresh);

    void loadMoreMovieDataError();

    void refreshMovieDataError();


}

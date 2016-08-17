package com.lq.xingyun.ui.view;

import com.lq.xingyun.model.entity.MovieDetailBean;
import com.lq.xingyun.model.entity.VideoUrlBean;

/**
 * Created by lenovo on 2016/8/8.
 */
public interface IMovieActivityView extends IBaseView{

    void setMovieDetailData(MovieDetailBean.DataBean.SeasonBean data);

    void loadingMovieDetailDataFailed();

    void gotoPlayVideo(VideoUrlBean.DataBean.M3u8Bean data);

    void loadingVideoPathFailed();
}

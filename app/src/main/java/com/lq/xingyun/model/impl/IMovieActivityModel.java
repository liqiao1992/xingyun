package com.lq.xingyun.model.impl;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.entity.MovieDetailBean;
import com.lq.xingyun.model.entity.VideoUrlBean;

/**
 * Created by lenovo on 2016/8/8.
 */
public interface IMovieActivityModel extends BaseModel {

    void getMovieDetailData(String movieId, Callback<MovieDetailBean.DataBean.SeasonBean> mCallback);

    void getMoviePlayPath(String quality, String seasonId, String episodeSid, Callback<VideoUrlBean.DataBean.M3u8Bean> mCallback);
}

package com.lq.xingyun.model.impl;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.entity.MovieBean;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/8/2.
 */
public interface IMovieFragmentModel extends BaseModel {
    /**
     * 获取电影数据
     */
    void getMovies(Map<String,Object> mapParams, Callback<List<MovieBean.DataBean.ResultsBean>> callback);
}

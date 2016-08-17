package com.lq.xingyun.model;

import android.util.Log;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.entity.MovieBean;
import com.lq.xingyun.model.impl.IMovieFragmentModel;
import com.lq.xingyun.net.RetrofitRxjavaService;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/8/2.
 */
public class MovieFragmentModel implements IMovieFragmentModel {

    @Override
    public void getMovies(Map<String, Object> mapParams, final Callback<List<MovieBean.DataBean.ResultsBean>> callback) {
        Log.i("fuck", "开始加载数据----------------------------------------------------------");

        RetrofitRxjavaService.builder().RRVideoApi().getSeasonQuery(mapParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<MovieBean, MovieBean.DataBean>() {
                    @Override
                    public MovieBean.DataBean call(MovieBean movieBean) {
                        return movieBean.getData();
                    }
                })
                .subscribe(new Subscriber<MovieBean.DataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("fuck", "加载电影数据出错----------------------------------------------------------");
                        e.printStackTrace();
                        callback.onFailed();
                    }

                    @Override
                    public void onNext(MovieBean.DataBean dataBean) {
                        Log.i("fuck", "加载电影数据结束----------------------------------------------------------");
                        callback.onSuccess(dataBean.getResults());
                    }
                });
    }
}

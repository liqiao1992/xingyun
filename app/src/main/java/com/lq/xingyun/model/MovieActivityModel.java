package com.lq.xingyun.model;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.entity.MovieDetailBean;
import com.lq.xingyun.model.entity.VideoUrlBean;
import com.lq.xingyun.model.impl.IMovieActivityModel;
import com.lq.xingyun.net.RetrofitRxjavaService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/8/8.
 */
public class MovieActivityModel implements IMovieActivityModel {
    @Override
    public void getMovieDetailData(String movieId, final Callback<MovieDetailBean.DataBean.SeasonBean> mCallback) {
        RetrofitRxjavaService.builder().RRVideoApi().getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<MovieDetailBean, MovieDetailBean.DataBean.SeasonBean>() {
                    @Override
                    public MovieDetailBean.DataBean.SeasonBean call(MovieDetailBean movieDetailBean) {
                        return movieDetailBean.getData().getSeason();
                    }
                })
                .subscribe(new Subscriber<MovieDetailBean.DataBean.SeasonBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mCallback.onFailed();
                    }

                    @Override
                    public void onNext(MovieDetailBean.DataBean.SeasonBean seasonBean) {
                        if (seasonBean != null)
                            mCallback.onSuccess(seasonBean);
                        else {
                            mCallback.onFailed();
                        }
                    }
                });
    }

    @Override
    public void getMoviePlayPath(String quality, String seasonId, String episodeSid, final Callback<VideoUrlBean.DataBean.M3u8Bean> mCallback) {
        RetrofitRxjavaService.builder().RRVideoApi().getVideoUrl(quality, seasonId, episodeSid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .map(new Func1<VideoUrlBean, VideoUrlBean.DataBean.M3u8Bean>() {
                    @Override
                    public VideoUrlBean.DataBean.M3u8Bean call(VideoUrlBean videoUrlBean) {
                        return videoUrlBean.getData().getM3u8();
                    }
                })
                .subscribe(new Subscriber<VideoUrlBean.DataBean.M3u8Bean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mCallback.onFailed();
                    }

                    @Override
                    public void onNext(VideoUrlBean.DataBean.M3u8Bean m3u8Bean) {
                        mCallback.onSuccess(m3u8Bean);
                    }
                });
    }
}

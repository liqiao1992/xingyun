package com.lq.xingyun.presenter;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.MovieActivityModel;
import com.lq.xingyun.model.entity.MovieDetailBean;
import com.lq.xingyun.model.entity.VideoUrlBean;
import com.lq.xingyun.model.impl.IMovieActivityModel;
import com.lq.xingyun.ui.view.IMovieActivityView;

/**
 * Created by lenovo on 2016/8/8.
 */
public class MovieActivityPresenter extends BasePresenter<IMovieActivityView> {

    private IMovieActivityModel movieActivityModel;

    public MovieActivityPresenter() {
        movieActivityModel = new MovieActivityModel();
    }

    public void getMovieDetail(String movieId) {
        movieActivityModel.getMovieDetailData(movieId, new Callback<MovieDetailBean.DataBean.SeasonBean>() {
            @Override
            public void onSuccess(MovieDetailBean.DataBean.SeasonBean data) {
                if (mView != null)
                    mView.setMovieDetailData(data);
            }

            @Override
            public void onFailed() {
                if(mView!=null){
                    mView.loadingMovieDetailDataFailed();
                }
            }
        });

    }

    public void getPlayVideoPath(String quality, String seasonId, String episodeSid){
        movieActivityModel.getMoviePlayPath(quality, seasonId, episodeSid, new Callback<VideoUrlBean.DataBean.M3u8Bean>() {
            @Override
            public void onSuccess(VideoUrlBean.DataBean.M3u8Bean data) {
                 mView.gotoPlayVideo(data);
            }

            @Override
            public void onFailed() {
                  mView.loadingVideoPathFailed();
            }
        });

    }
}

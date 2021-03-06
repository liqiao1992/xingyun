package com.lq.xingyun.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.R;
import com.lq.xingyun.adapter.MovieFragmentAdapter;
import com.lq.xingyun.model.entity.MovieBean;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.presenter.MovieFragmentPresenter;
import com.lq.xingyun.ui.activity.MovieDetailActivity;
import com.lq.xingyun.ui.view.IMovieFragmentView;

import java.util.List;

/**
 * Created by lenovo on 2016/8/3.
 */
public class MovieFragment extends BaseFragment implements IMovieFragmentView {
    public static String TAG="MovieFragment";
    private EasyRecyclerView easyRecyclerView;
    private MovieFragmentAdapter movieFragmentAdapter;

    private    LocalBroadcastReceiver localBroadcastReceiver;
    @Override
    public BasePresenter getPresenter() {
        if(mBasePresenter!=null){
            return mBasePresenter;
        }else {
            return new MovieFragmentPresenter();
        }
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if(!firstInitUi){
            return;
        }
        easyRecyclerView = (EasyRecyclerView) getView().findViewById(R.id.fragment_movie_recycler_view);
        movieFragmentAdapter = new MovieFragmentAdapter(getContext());

        movieFragmentAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra("movieId", movieFragmentAdapter.getItem(position).getId() + "");
                getContext().startActivity(intent);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(movieFragmentAdapter.obtainGridSpanSizeLookUp(3));
        easyRecyclerView.setLayoutManager(gridLayoutManager);
        easyRecyclerView.setAdapterWithProgress(movieFragmentAdapter);

        movieFragmentAdapter.setMore(R.layout.load_more_layout, (MovieFragmentPresenter) mBasePresenter);
        movieFragmentAdapter.setNoMore(R.layout.no_more_layout);
        movieFragmentAdapter.setError(R.layout.error_layout);
        easyRecyclerView.setRefreshListener((MovieFragmentPresenter) mBasePresenter);

        //通过MoviePresenter调用加载电影数据
        ((MovieFragmentPresenter) mBasePresenter).loadMovieData(false);
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_movie;
    }


    @Override
    public void setMovieData(List<MovieBean.DataBean.ResultsBean> movieData, boolean isRefresh) {
        if (isRefresh)
            movieFragmentAdapter.clear();
        movieFragmentAdapter.addAll(movieData);
    }

    @Override
    public void loadMoreMovieDataError() {
        movieFragmentAdapter.pauseMore();
    }

    @Override
    public void refreshMovieDataError() {
        easyRecyclerView.showError();
    }

    @Override
    public void onStart() {
        super.onStart();
        localBroadcastReceiver=new LocalBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(TAG);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(localBroadcastReceiver,intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(localBroadcastReceiver);
    }

    public class LocalBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("fuck","接收到广播了");
            if(intent.getAction().equals(TAG)) {
                if (easyRecyclerView != null) {
                    easyRecyclerView.scrollToPosition(0);
                }
            }
        }
    }
}

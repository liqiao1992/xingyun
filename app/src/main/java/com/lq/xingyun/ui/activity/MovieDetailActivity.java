package com.lq.xingyun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.R;
import com.lq.xingyun.adapter.MovieActivityAdapter;
import com.lq.xingyun.model.entity.MovieDetailBean;
import com.lq.xingyun.model.entity.VideoUrlBean;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.presenter.MovieActivityPresenter;
import com.lq.xingyun.ui.view.IMovieActivityView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lenovo on 2016/8/8.
 */
public class MovieDetailActivity extends BaseActivity implements IMovieActivityView {

    private EasyRecyclerView easyRecyclerView;
    private MovieActivityAdapter movieActivityAdapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private String movieId;
    private String movieTitle;
    private Snackbar snackbar;
    private boolean isLoadingPath=false;

    @Override
    public int getToolBarId() {
        return R.id.activity_movie_detail_toolbar;
    }

    /**
     * 获取传值
     */
    @Override
    public void getIntentValue() {
        super.getIntentValue();
        movieId = getIntent().getStringExtra("movieId");
    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return new MovieActivityPresenter();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        easyRecyclerView = (EasyRecyclerView) findViewById(R.id.activity_movie_detail_recycler_view);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_movie_detail_collapsingtoolbarlayout);
        imageView = (ImageView) findViewById(R.id.activity_movie_detail_image);
        movieActivityAdapter = new MovieActivityAdapter(getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        gridLayoutManager.setSpanSizeLookup(movieActivityAdapter.obtainGridSpanSizeLookUp(5));
        easyRecyclerView.setLayoutManager(gridLayoutManager);

        movieActivityAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return initheadview();
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        movieActivityAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(!isLoadingPath) {
                    isLoadingPath=true;
                    snackbar = Snackbar.make(easyRecyclerView, "获取播放链接...", Snackbar.LENGTH_INDEFINITE);
                    SnackbarAddView(snackbar,0);
                    snackbar.show();
                    ((MovieActivityPresenter) mPresenter).getPlayVideoPath("high", movieId, movieActivityAdapter.getItem(position).getSid());
                }
            }
        });
        easyRecyclerView.setAdapterWithProgress(movieActivityAdapter);
        ((MovieActivityPresenter) mPresenter).getMovieDetail(movieId);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_movie_detail;
    }

    private TextView tv_playcount;
    private TextView tv_score;
    private TextView tv_content;

    private View initheadview() {
        View view = getLayoutInflater().inflate(R.layout.item_movielist_header, null);
        tv_playcount = (TextView) view.findViewById(R.id.tv_playcount);
        tv_score = (TextView) view.findViewById(R.id.tv_score);
        tv_content = (TextView) view.findViewById(R.id.tv_content);

        return view;
    }

    @Override
    public void setMovieDetailData(MovieDetailBean.DataBean.SeasonBean data) {
        Comparator<MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean> comparable = new Comparator<MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean>() {

            @Override
            public int compare(MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean data1, MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean data2) {
                return -(Integer.parseInt(data1.getEpisode()) - Integer.parseInt(data2.getEpisode()));
            }
        };
        List<MovieDetailBean.DataBean.SeasonBean.EpisodeBriefBean> playUrlList = data.getEpisode_brief();
        Collections.sort(playUrlList, comparable);
        //header
        updateheaer(data.getViewCount() + "", data.getScore() + "", data.getBrief());
        movieActivityAdapter.addAll(playUrlList);
        movieTitle=data.getTitle();
        collapsingToolbarLayout.setTitle(data.getTitle());
        Glide.with(this).load(data.getCover()).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    @Override
    public void loadingMovieDetailDataFailed() {
        easyRecyclerView.showError();
    }

    @Override
    public void gotoPlayVideo(VideoUrlBean.DataBean.M3u8Bean data) {
        isLoadingPath=false;
        if(snackbar!=null&&snackbar.isShown()){
            snackbar.dismiss();
        }
        Intent intent = new Intent(MovieDetailActivity.this, MoviePlayActivity.class);
        intent.putExtra("VIEEO_PATH", data.getUrl());
        intent.putExtra("VIDEO_TITLE",movieTitle);
        startActivity(intent);
    }

    @Override
    public void loadingVideoPathFailed() {
        Log.v("fuck","加载失败播放地址");
        isLoadingPath=false;
        if(snackbar!=null&&snackbar.isShown()){
          snackbar.dismiss();
        }
        Snackbar.make(easyRecyclerView,"获取播放链接失败...",Snackbar.LENGTH_LONG).show();
    }

    private void updateheaer(String playcount, String score, String content) {
        if (content.length() == 0) {
            content = "暂时没有简介哦~~";
        }
        tv_content.setText(content);
        tv_score.setText("评分：" + score);
        tv_playcount.setText("播放量:" + playcount);
    }

    public static void SnackbarAddView(Snackbar snackbar,int index) {
        View snackbarview = snackbar.getView();//获取snackbar的View(其实就是SnackbarLayout)
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;//将获取的View转换成SnackbarLayout
        ProgressBar progressBar=new ProgressBar(snackbarview.getContext());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数
        snackbarLayout.addView(progressBar,index,p);//将新建布局添加进snackbarLayout相应位置
    }

}

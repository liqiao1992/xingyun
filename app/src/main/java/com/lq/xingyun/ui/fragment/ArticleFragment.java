package com.lq.xingyun.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.lq.xingyun.R;
import com.lq.xingyun.adapter.ArticleFragmentAdapter;
import com.lq.xingyun.model.entity.ArticleBean;
import com.lq.xingyun.presenter.ArticleFragmentPresenter;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.ui.activity.ArticleDetailActivity;
import com.lq.xingyun.ui.view.IArticleFragmentView;
import com.lq.xingyun.utils.DipUtils;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ArticleFragment extends BaseFragment implements IArticleFragmentView {
    public static String TAG="ArticleFragment";
    private EasyRecyclerView easyRecyclerView;
    private ArticleFragmentAdapter articleFragmentAdapter;
    private    LocalBroadcastReceiver localBroadcastReceiver;
    @Override
    public BasePresenter getPresenter() {
        return new ArticleFragmentPresenter();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        easyRecyclerView = (EasyRecyclerView) getView().findViewById(R.id.fragment_article_recycler_view);
        articleFragmentAdapter = new ArticleFragmentAdapter(getContext());
        articleFragmentAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArticleBean articleBean = articleFragmentAdapter.getItem(position);
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("article", articleBean);
                mContext.startActivity(intent);
            }
        });
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, DipUtils.dip2px(getContext(),0.5f), DipUtils.dip2px(getContext(),72),0);
//        itemDecoration.setDrawLastItem(false);
//        easyRecyclerView.addItemDecoration(itemDecoration);
        easyRecyclerView.setAdapterWithProgress(articleFragmentAdapter);

        articleFragmentAdapter.setMore(R.layout.load_more_layout, (ArticleFragmentPresenter) mBasePresenter);
        articleFragmentAdapter.setNoMore(R.layout.no_more_layout);
        articleFragmentAdapter.setError(R.layout.error_layout);
        easyRecyclerView.setRefreshListener((ArticleFragmentPresenter) mBasePresenter);

        ((ArticleFragmentPresenter) mBasePresenter).loadArticleData(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_article;
    }


    /**
     * 实现IArticleFragmentView接口
     *
     * @param data
     * @param isRefresh
     */
    @Override
    public void setArticleData(List<ArticleBean> data, boolean isRefresh) {
        if (isRefresh)
            articleFragmentAdapter.clear();
        articleFragmentAdapter.addAll(data);
    }

    @Override
    public void loadMoreArticleDataError() {
        articleFragmentAdapter.pauseMore();
    }

    @Override
    public void refreshArticleDataError() {
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

    public class LocalBroadcastReceiver extends BroadcastReceiver {

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

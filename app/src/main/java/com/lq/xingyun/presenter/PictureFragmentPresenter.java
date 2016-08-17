package com.lq.xingyun.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.PictureFragmentModel;
import com.lq.xingyun.model.entity.PictureBean;
import com.lq.xingyun.model.impl.IPictureFragmentModel;
import com.lq.xingyun.ui.view.IPictureFragmentView;

import java.util.List;

/**
 * Created by lenovo on 2016/8/5.
 */
public class PictureFragmentPresenter extends BasePresenter<IPictureFragmentView> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private IPictureFragmentModel pictureFragmentModel;
    private int currentPage = 1;
    private boolean isLoading = false;
    public PictureFragmentPresenter() {
        pictureFragmentModel = new PictureFragmentModel();
    }

    public void loadPicturesData(final boolean isRefresh) {
        if(isLoading)
            return;
        isLoading=true;
        Log.i("fuck","当前加载页数"+currentPage);
        pictureFragmentModel.getPicture(currentPage, new Callback<List<PictureBean.PictureGirl>>() {
            @Override
            public void onSuccess(List<PictureBean.PictureGirl> data) {
                isLoading=false;
                currentPage++;
                if (mView != null) {
                    mView.setPictureData(data, isRefresh);
                }
            }

            @Override
            public void onFailed() {
                isLoading=false;
                if (mView != null) {
                    if (isRefresh) {
                        Log.i("fuck","刷新图片数据");
                        mView.refreshPictureDataError();
                    } else {
                        Log.i("fuck","加载更多图片数据");
                        if(currentPage==1){
                            mView.refreshPictureDataError();
                        }else {
                            mView.loadMorePictureDataError();
                        }
                    }
                }
            }
        });

    }

    /**
     * 实现EasyRecyerView上拉加载更多的接口
     */
    @Override
    public void onLoadMore() {
        currentPage++;
        loadPicturesData(false);
    }

    /**
     * 实现EasyRecyerView下拉刷新的接口
     */
    @Override
    public void onRefresh() {
        currentPage = 1;
        loadPicturesData(true);
    }
}


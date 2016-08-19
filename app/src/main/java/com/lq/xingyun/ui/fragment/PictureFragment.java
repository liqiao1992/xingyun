package com.lq.xingyun.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.lq.xingyun.R;
import com.lq.xingyun.adapter.PictureFragmentAdapter;
import com.lq.xingyun.model.entity.PictureBean;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.presenter.PictureFragmentPresenter;
import com.lq.xingyun.ui.activity.PictureDetailActivity;
import com.lq.xingyun.ui.view.IPictureFragmentView;
import com.lq.xingyun.utils.DipUtils;

import java.util.List;

/**
 * Created by lenovo on 2016/8/5.
 */
public class PictureFragment extends BaseFragment implements IPictureFragmentView {

    public static String TAG = "PictureFragment";
    private EasyRecyclerView easyRecyclerView;
    private PictureFragmentAdapter pictureFragmentAdapter;
    private LocalBroadcastReceiver localBroadcastReceiver;

    @Override
    public BasePresenter getPresenter() {
        return new PictureFragmentPresenter();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        easyRecyclerView = (EasyRecyclerView) getView().findViewById(R.id.fragment_picture_recycler_view);
        pictureFragmentAdapter = new PictureFragmentAdapter(getContext());
        pictureFragmentAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PictureBean.PictureGirl girl = pictureFragmentAdapter.getItem(position);
                Intent intent = new Intent(mContext, PictureDetailActivity.class);
                intent.putExtra("imageTitle", girl.getDesc());
                intent.putExtra("imageUrl", girl.getUrl());
                startActivity(intent);
            }
        });
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        easyRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        easyRecyclerView.setAdapterWithProgress(pictureFragmentAdapter);
//        SpaceDecoration itemDecoration = new SpaceDecoration((int) DipUtils.convertDpToPixel(8,getContext()));
//        itemDecoration.setPaddingEdgeSide(true);
//        itemDecoration.setPaddingStart(true);
//        itemDecoration.setPaddingHeaderFooter(false);
//        easyRecyclerView.addItemDecoration(itemDecoration);
//        pictureFragmentAdapter.setMore(R.layout.load_more_layout, (PictureFragmentPresenter) mBasePresenter);
//        pictureFragmentAdapter.setNoMore(R.layout.no_more_layout);
//        pictureFragmentAdapter.setError(R.layout.error_layout);
//        easyRecyclerView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                ((PictureFragmentPresenter)mBasePresenter).loadPicturesData(false);
//            }
//        });
        easyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
                                pictureFragmentAdapter.getCount() - 2;
                Log.i("fuck", "最后视图坐标" + staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]);
                Log.i("fuck", "总ITEM数" + pictureFragmentAdapter.getCount());
                if (isBottom) {
                    ((PictureFragmentPresenter) mBasePresenter).loadPicturesData(false);
                }
            }
        });
        easyRecyclerView.setRefreshListener((PictureFragmentPresenter) mBasePresenter);
        ((PictureFragmentPresenter) mBasePresenter).loadPicturesData(false);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_picture;
    }

    /**
     * 实现IPictureFragmentView接口
     *
     * @param pictures
     * @param isRefresh
     */
    @Override
    public void setPictureData(List<PictureBean.PictureGirl> pictures, boolean isRefresh) {
        if (isRefresh)
            pictureFragmentAdapter.clear();
        pictureFragmentAdapter.addAll(pictures);
    }

    @Override
    public void loadMorePictureDataError() {
        pictureFragmentAdapter.pauseMore();
    }

    @Override
    public void refreshPictureDataError() {
        easyRecyclerView.showError();
    }

    @Override
    public void onStart() {
        super.onStart();
        localBroadcastReceiver = new LocalBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TAG);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(localBroadcastReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(localBroadcastReceiver);
    }

    public class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("fuck", "接收到广播了");
            if (intent.getAction().equals(TAG)) {
                if (easyRecyclerView != null) {
                    easyRecyclerView.scrollToPosition(0);
                }
            }
        }
    }
}

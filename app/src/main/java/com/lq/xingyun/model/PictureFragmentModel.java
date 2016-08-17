package com.lq.xingyun.model;

import android.util.Log;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.entity.PictureBean;
import com.lq.xingyun.model.impl.IPictureFragmentModel;
import com.lq.xingyun.net.RetrofitRxjavaService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/8/5.
 */
public class PictureFragmentModel implements IPictureFragmentModel {
    @Override
    public void getPicture(int page, final Callback<List<PictureBean.PictureGirl>> mCallback) {
        RetrofitRxjavaService.builder().RRPictureApi().getPictureData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<PictureBean, List<PictureBean.PictureGirl>>() {

                    @Override
                    public List<PictureBean.PictureGirl> call(PictureBean pictureBean) {
                        return pictureBean.getResults();
                    }
                })
                .subscribe(new Subscriber<List<PictureBean.PictureGirl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("fuck", "加载图片数据出错----------------------------------------------------------");
                        e.printStackTrace();
                        mCallback.onFailed();
                    }

                    @Override
                    public void onNext(List<PictureBean.PictureGirl> pictureGirls) {
                        Log.i("fuck", "加载图片数据结束----------------------------------------------------------");
                        mCallback.onSuccess(pictureGirls);
                    }
                });
    }
}

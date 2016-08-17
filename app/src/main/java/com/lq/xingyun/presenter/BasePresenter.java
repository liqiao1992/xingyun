package com.lq.xingyun.presenter;

import com.lq.xingyun.ui.view.IBaseView;

/**
 * Created by lenovo on 2016/7/28.
 */
public abstract class BasePresenter<T extends IBaseView> {
    protected T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void detachView() {
        if (mView != null)
            mView = null;
    }
}

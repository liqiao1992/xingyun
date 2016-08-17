package com.lq.xingyun.inter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.xingyun.presenter.BasePresenter;

/**
 * Created by lenovo on 2016/7/28.
 */
public interface IBase {
    BasePresenter getPresenter();

    View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void bindView(Bundle savedInstanceState);

    View getView();

    int getContentLayout();

}

package com.lq.xingyun.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.xingyun.inter.IBase;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.ui.view.IBaseView;

/**
 * Created by lenovo on 2016/7/28.
 */
public abstract class BaseFragment extends Fragment implements IBase {

    private View mRootView;
    protected BasePresenter mBasePresenter;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("BaseFragment","fragment------create");
        mBasePresenter=getPresenter();
        if(mBasePresenter!=null&&this instanceof IBaseView){
            mBasePresenter.attach((IBaseView) this);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("BaseFragment","fragment------onCreateView");
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = createView(inflater, container, savedInstanceState);
        }
        mContext = mRootView.getContext();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("BaseFragment","fragment------onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        bindView(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("BaseFragment","fragment------onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("BaseFragment","fragment------onStop");
    }

    @Override
    public void onDestroy() {
        if(mBasePresenter!=null&&this instanceof IBaseView){
            mBasePresenter.detachView();;
            mBasePresenter=null;
        }
        mContext=null;
        super.onDestroy();
    }

    //重写IBase中的方法
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(getContentLayout(),container,false);
        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return mRootView;
    }
}

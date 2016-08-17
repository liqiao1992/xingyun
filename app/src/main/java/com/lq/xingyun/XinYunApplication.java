package com.lq.xingyun;

import android.app.Application;
import android.content.Context;

/**
 * Created by lenovo on 2016/8/3.
 */
public class XinYunApplication extends Application {

    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

    public static Context getContext() {

        return mAppContext;
    }
}

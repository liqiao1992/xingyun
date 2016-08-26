package com.lq.xingyun.utils;

import android.util.Log;

import com.lq.xingyun.BuildConfig;

/**
 * Created by lenovo on 2016/8/26.
 */
public class LogUtil {
    private static boolean DEBUG = BuildConfig.LOG_DEBUG;

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable e) {
        if (DEBUG) {
            Log.i(tag, msg, e);
        }
    }
}

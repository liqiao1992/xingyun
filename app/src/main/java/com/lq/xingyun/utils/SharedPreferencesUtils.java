package com.lq.xingyun.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lq.xingyun.XinYunApplication;

/**
 * Created by lenovo on 2016/8/10.
 */
public class SharedPreferencesUtils {

    private static SharedPreferences getSharedPreferences(Context context){
           return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void save(String key,boolean value){
        getSharedPreferences(XinYunApplication.getContext()).edit().putBoolean(key,value).commit();
    }
    public static boolean getBoolean(String key){
        return getSharedPreferences(XinYunApplication.getContext()).getBoolean(key,false);
    }

    public  static void save(String key,int value){
        getSharedPreferences(XinYunApplication.getContext()).edit().putInt(key,value).commit();
    }

    public static int getInteger(String key){
        return getSharedPreferences(XinYunApplication.getContext()).getInt(key,-1);
    }
}

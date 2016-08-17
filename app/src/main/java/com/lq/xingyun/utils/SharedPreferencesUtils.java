package com.lq.xingyun.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lenovo on 2016/8/10.
 */
public class SharedPreferencesUtils {

    private static SharedPreferences getSharedPreferences(Context context){
           return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void save(Context context,String key,boolean value){
        getSharedPreferences(context).edit().putBoolean(key,value).commit();
    }
    public static boolean getBoolean(Context context,String key){
        return getSharedPreferences(context).getBoolean(key,false);
    }
}

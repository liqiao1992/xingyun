package com.lq.xingyun.utils;

import android.app.Activity;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/10.
 */
public class ActivityManagerUtil {
    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void refreshAllActivities() {
        for (Activity activity : activityList) {
            activity.recreate();
        }
    }


}

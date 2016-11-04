package com.lq.xingyun.utils;

import com.lq.xingyun.XinYunApplication;

import java.util.Locale;

/**
 * Created by lenovo on 2016/9/1.
 */
public class CommonUtils {

    public static int getCurrentLanguage() {
        int lang =SharedPreferencesUtils.getInteger("language_mode");
        if (lang == -1) {
            String language = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();

            if (language.equalsIgnoreCase("zh")) {
                if (country.equalsIgnoreCase("CN")) {
                    lang = 1;
                } else {
                    lang = 2;
                }
            } else {
                lang = 0;
            }
        }
        return lang;
    }
}

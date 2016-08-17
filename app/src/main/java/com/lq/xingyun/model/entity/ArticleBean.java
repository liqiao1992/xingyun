package com.lq.xingyun.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ArticleBean implements Parcelable {
    public String title;
    public String href;
    public String text;
    public String auth;

    public ArticleBean() {
    }

    protected ArticleBean(Parcel in) {
        title = in.readString();
        href = in.readString();
        text = in.readString();
        auth = in.readString();
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel in) {
            return new ArticleBean(in);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(href);
        dest.writeString(text);
        dest.writeString(auth);
    }
}

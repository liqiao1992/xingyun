package com.lq.xingyun.model.impl;

import com.lq.xingyun.inter.Callback;

/**
 * Created by lenovo on 2016/8/4.
 */
public interface IArticleActivityModel extends BaseModel {
    /**
     * 获取文章详情
     * @param href
     * @param mCallback
     */
    void getArticleDetail(String href,final Callback<String> mCallback);
}

package com.lq.xingyun.model.impl;

import android.util.ArrayMap;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.entity.ArticleBean;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/8/4.
 */
public interface IArticleFragmentModel extends BaseModel {

    /**
     * 获取文章数据
     * @param params
     * @param callback
     */
     void getArticles(Map<String,Object> params, Callback<List<ArticleBean>> callback);

}

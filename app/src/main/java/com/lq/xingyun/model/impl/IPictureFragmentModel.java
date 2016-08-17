package com.lq.xingyun.model.impl;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.entity.PictureBean;

import java.util.List;

/**
 * Created by lenovo on 2016/8/5.
 */
public interface IPictureFragmentModel extends  BaseModel{

    /**
     * 获取美女图片数据
     * @param page 第几页
     * @param mCallback  回调
     */
    void getPicture(int page, Callback<List<PictureBean.PictureGirl>> mCallback);
}

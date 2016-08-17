package com.lq.xingyun.ui.view;

import com.lq.xingyun.model.entity.PictureBean;

import java.util.List;

/**
 * Created by lenovo on 2016/8/5.
 */
public interface IPictureFragmentView  extends IBaseView{

    void setPictureData(List<PictureBean.PictureGirl> pictures,boolean isRefresh);


    void loadMorePictureDataError();

    void refreshPictureDataError();
}

package com.lq.xingyun.presenter;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.ArticleActivityModel;
import com.lq.xingyun.model.impl.IArticleActivityModel;
import com.lq.xingyun.ui.view.IArticleActivityView;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ArticleActivityPresenter extends BasePresenter<IArticleActivityView> {

    private IArticleActivityModel articleActivityModel;

    public ArticleActivityPresenter() {
        articleActivityModel=new ArticleActivityModel();
    }

    public void getArticleDetailData(String href){

      articleActivityModel.getArticleDetail(href, new Callback<String>() {
          @Override
          public void onSuccess(String data) {
              if(mView!=null){
                  mView.setArticleDetailData(data);
              }

          }

          @Override
          public void onFailed() {

          }
      });
    }
}

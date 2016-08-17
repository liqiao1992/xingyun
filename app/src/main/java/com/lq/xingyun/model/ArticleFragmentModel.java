package com.lq.xingyun.model;

import android.text.TextUtils;
import android.util.Log;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.entity.ArticleBean;
import com.lq.xingyun.model.impl.IArticleFragmentModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ArticleFragmentModel implements IArticleFragmentModel {
    @Override
    public void getArticles(Map<String, Object> params, final Callback<List<ArticleBean>> callback) {
        String url = (String) params.get("url");
        int page = (int) params.get("page");
        String path=url;
        if (page > 0) {
             path = url.replace(".html", "-" +page+ ".html");
        }
        final String finalPath = path;
        Observable.create(new Observable.OnSubscribe<List<ArticleBean>>() {
            @Override
            public void call(Subscriber<? super List<ArticleBean>> subscriber) {
                try {
                    Document result = Jsoup.connect(finalPath).get();
                    Elements postHeads = result.getElementsByClass("PostHead");
                    Elements postContent1s = result.getElementsByClass("PostContent1");
                    List<ArticleBean> list = new ArrayList<ArticleBean>(postHeads.size());
                    for (int i = 0; i < postHeads.size(); i++) {
                        ArticleBean lz13 = new ArticleBean();
                        Element postHead = postHeads.get(i);
                        Element a = postHead.getElementsByTag("a").get(0);
                        lz13.href = a.attr("href");
                        lz13.title = a.text();
                        Element postContent1 = postContent1s.get(i);
                        lz13.text = postContent1.text().replace(lz13.title, "");
                        String[] split = lz13.text.split("\\s{2,}");
                        StringBuffer sb = new StringBuffer();
                        for (String s : split) {
                            if (TextUtils.isEmpty(s)) {
                                continue;
                            }
                            if (lz13.title.equals(s)) {
                                continue;
                            }
                            if (s.startsWith("文/")) {
                                lz13.auth = s;
                                continue;
                            }
                            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(s).append("<br/>");
                        }
                        int index = sb.lastIndexOf("<br/>");
                        if (index == -1) continue;
                        lz13.text = sb.substring(0, sb.lastIndexOf("<br/>") - 1) + "......";
                        list.add(lz13);
                    }
                    subscriber.onNext(list);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable("出错了"));
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ArticleBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("fuck", "加载文章数据出错----------------------------------------------------------");
                        e.printStackTrace();
                        callback.onFailed();
                    }

                    @Override
                    public void onNext(List<ArticleBean> articleBeen) {
                        Log.i("fuck", "加载文章数据结束----------------------------------------------------------");
                       if(articleBeen!=null){
                           callback.onSuccess(articleBeen);
                       }
                    }
                });

    }
}

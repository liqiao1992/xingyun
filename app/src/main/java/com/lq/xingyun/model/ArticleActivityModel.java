package com.lq.xingyun.model;

import com.lq.xingyun.inter.Callback;
import com.lq.xingyun.model.impl.IArticleActivityModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ArticleActivityModel implements IArticleActivityModel {
    @Override
    public void getArticleDetail(final String href, final Callback<String> mCallback) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Document result = Jsoup.connect(href).get();
                    Element postContent = result.getElementsByClass("PostContent").get(0);
                    Elements p = postContent.getElementsByTag("p");
                    StringBuffer sb = new StringBuffer();
                    for (Element e : p) {
                        sb.append("<p>").append(e.text()).append("</p>");
                    }
                    subscriber.onNext(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onNext(null);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        if (s != null) {
                            mCallback.onSuccess(s);
                        }
                    }
                });
    }
}

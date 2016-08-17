package com.lq.xingyun.net;

import com.lq.xingyun.model.entity.ArticleBean;
import com.lq.xingyun.model.entity.MovieBean;
import com.lq.xingyun.model.entity.MovieDetailBean;
import com.lq.xingyun.model.entity.PictureBean;
import com.lq.xingyun.model.entity.VideoUrlBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by lenovo on 2016/8/3.
 */
public interface RetrofitRxjavaApi {
    /**
     * 获取分类列表
     *
     * @param cat      类型  全部为空 喜剧科幻 恐怖 剧情 魔幻 罪案 冒险 动作 悬疑
     * @param rows     12 每页行数
     * @param page     1 页数
     * @param sort     筛选 最热heat 评分score 时间为空
     * @param isFinish 状态 全部为空 false连载中 true已完结
     * @return Observable<MovieBean>
     */
    @FormUrlEncoded
    @POST("season/query")
    Observable<MovieBean> getSeasonQuery(@FieldMap Map<String, Object> mapParams);


    @GET("data/福利/" + 10 + "/{page}")
    Observable<PictureBean> getPictureData(@Path("page") int page);


    @FormUrlEncoded
    @POST("season/detail")
    Observable<MovieDetailBean> getMovieDetail(@Field("seasonId") String seasonId);

    /**
     * 获取视频播放地址
     *
     * @param quality    画质 high高清
     * @param seasonId   电视剧id
     * @param episodeSid 电视剧分集id
     * @return
     */
    @FormUrlEncoded
    @POST("video/findM3u8ByEpisodeSid")
    Observable<VideoUrlBean> getVideoUrl(@Field("quality") String quality, @Field("seasonId") String seasonId, @Field("episodeSid") String episodeSid);
}

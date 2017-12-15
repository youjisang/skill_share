package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.m_class.About;
import com.immymemine.kevin.skillshare.model.m_class.Discussion;
import com.immymemine.kevin.skillshare.model.m_class.Lessons;
import com.immymemine.kevin.skillshare.network.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by quf93 on 2017-12-02.
 */

public interface ClassService {
    @GET("class/lessons/{id}")
    Observable<Lessons> getLessons(@Path("id") String id);

    @GET("class/about/{id}")
    Observable<About> getAbout(@Path("id") String id);

    @GET("class/discussions/{id}")
    Observable<List<Discussion>> getDiscussions(@Path("id") String id);

    @POST("class/addDiscussion")
    Observable<List<Discussion>> addDiscussion(@Body Discussion discussion);

    @POST("class/like")
    Observable<Response> like();
}

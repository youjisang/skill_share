package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.online_class.Discussion;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by quf93 on 2017-12-02.
 */

public interface ClassService {
    @GET("class/{_id}")
    Observable<List<Discussion>> getDiscussions(@Path("_id") String id);

    @POST("class/addDiscussion")
    Observable<List<Discussion>> addDiscussion(@Body Discussion discussion);
}

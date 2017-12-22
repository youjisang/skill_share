package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.m_class.Reply;
import com.immymemine.kevin.skillshare.model.m_class.Subscriber;
import com.immymemine.kevin.skillshare.model.see_all.Project;
import com.immymemine.kevin.skillshare.model.see_all.Review;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by quf93 on 2017-12-08.
 */

public interface SeeAllService {
    @GET("SeeAll/review/{id}")
    Observable<List<Review>> getSeeAllReview(@Path("id") String id);

    @GET("SeeAll/subscriber/{id}")
    Observable<List<Subscriber>> getSeeAllSubscriber(@Path("id") String id);

    @GET("SeeAll/project/{id}")
    Observable<List<Project>> getSeeAllProject(@Path("id") String id);

    @POST("discussions/addReply")
    Observable<List<Reply>> addReply(@Body Reply reply, @Query("discussionId") String discussionId);
}

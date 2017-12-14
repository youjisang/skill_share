package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.see_all.Project;
import com.immymemine.kevin.skillshare.model.see_all.Review;
import com.immymemine.kevin.skillshare.model.see_all.Subscriber;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
}

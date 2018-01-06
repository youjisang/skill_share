package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.m_class.Tutor;
import com.immymemine.kevin.skillshare.model.user.Following;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.user.SignUpRequestBody;
import com.immymemine.kevin.skillshare.network.user.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by quf93 on 2017-11-30.
 */

public interface UserService {
    @POST("user/sign-up")
    Observable<UserResponse> signUp(@Body SignUpRequestBody body);

    @GET("users/sign-in")
    Observable<UserResponse> signIn(@Query("email") String email, @Query("password") String password);

    @GET("user/{id}")
    Observable<User> getUser(@Path("id") String id);

    @PUT("user/follow/{userId}")
    Observable<Following> follow(@Path("userId") String userId, @Body Tutor tutor);
}

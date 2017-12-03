package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.user.SignInResponse;
import com.immymemine.kevin.skillshare.network.user.SignUpRequestBody;
import com.immymemine.kevin.skillshare.network.user.SignUpResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by quf93 on 2017-11-30.
 */

public interface UserService {
    @POST("user/sign-up")
    Observable<SignUpResponse> signUp(@Body SignUpRequestBody body);

    @GET("user/sign-in")
    Observable<SignInResponse> signIn(@Query("email") String email, @Query("password") String password);

    @GET("user/{_id}")
    Observable<User> getUser(@Path("_id") String id);
}

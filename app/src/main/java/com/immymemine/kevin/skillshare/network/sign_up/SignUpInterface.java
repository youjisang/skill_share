package com.immymemine.kevin.skillshare.network.sign_up;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by quf93 on 2017-11-29.
 */

public interface SignUpInterface {
    @POST("user")
    Call<SignUpResponseBody> signUpUser(@Body SignUpRequestBody body);
}

package com.immymemine.kevin.skillshare.gcm.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by quf93 on 2017-11-26.
 */

public interface RegistrationInterface {
    @POST("device")
    Call<RegistrationResponseBody> registerDevice(@Body RegistrationRequestBody body);
}

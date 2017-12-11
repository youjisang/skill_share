package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.network.Response;
import com.immymemine.kevin.skillshare.network.gcm.RegisterRequestBody;
import com.immymemine.kevin.skillshare.network.gcm.SendMessageBody;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by quf93 on 2017-11-30.
 */

public interface GCMService {
    @POST("device/register")
    Observable<Response> register(@Body RegisterRequestBody body);

    @POST("/send")
    Observable<Response> sendMessage(@Body SendMessageBody body);
}

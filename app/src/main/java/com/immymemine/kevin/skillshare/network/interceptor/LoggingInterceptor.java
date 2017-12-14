package com.immymemine.kevin.skillshare.network.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by quf93 on 2017-11-30.
 */

public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "LoggingInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.d(TAG, "-------------------------------------------------------------------");
        Log.d(TAG,"| request method     : " + request.method());
        Log.d(TAG,"| request url        : " + request.headers());
        Log.d(TAG,"| request connection : " + chain.connection());
        Log.d(TAG, "-------------------------------------------------------------------");
        Log.d(" ", " ");
        Response response = chain.proceed(request);
        Log.d(TAG, "-------------------------------------------------------------------");
        Log.d(TAG,"| response requested url : " + response.request().url());
        Log.d(TAG,"| response header        : " + response.headers());
        Log.d(TAG, "| response              : " + response.body().string());
        Log.d(TAG, "-------------------------------------------------------------------");
        return response;
    }
}

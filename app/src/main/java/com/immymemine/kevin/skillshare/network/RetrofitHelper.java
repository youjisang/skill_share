package com.immymemine.kevin.skillshare.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by quf93 on 2017-11-30.
 */

public class RetrofitHelper {

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
//                .addInterceptor(new LoggingInterceptor())
//                .addNetworkInterceptor(new NetworkCacheInterceptor())
//                .cache(buildAndGetCacheDirecory())
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost/8079/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
    }
}

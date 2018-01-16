package com.immymemine.kevin.skillshare.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * not used
 * Created by quf93 on 2017-12-01.
 */

public class AuthenticationInterceptor implements Interceptor {
    private String authToken;

    public AuthenticationInterceptor(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}

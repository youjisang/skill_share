package com.immymemine.kevin.skillshare.network;

import com.immymemine.kevin.skillshare.network.interceptor.AuthenticationInterceptor;
import com.immymemine.kevin.skillshare.network.interceptor.LoggingInterceptor;
import com.immymemine.kevin.skillshare.utility.ValidationUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by quf93 on 2017-11-30.
 */

public class RetrofitHelper {
    public static final String BASE_URL = "http://10.0.2.2:8079/";



    // OkHttpClient
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS);

    // Retrofit Builder
    private static Retrofit.Builder builder =new Retrofit.Builder()
                                                         .baseUrl(BASE_URL)
                                                         .addConverterFactory(GsonConverterFactory.create())
                                                         .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    // Retrofit
    private static Retrofit retrofit = builder.build();

    public static <T> T createApi(Class<T> service){
        return createApi(service, null);
    }

    /* TODO ☆ 지상
    이 부분 모듈화 활용을 이해를 우선적으로.
    Retrofit 및 rxjava 활용해 앱 전체에 필요한 데이터 뿌려주는 연습

    okhttp의 활용용도는 로그 찍기 및 쓰기 시간아웃 등등 그러나 그렇게까지 중요한 이슈는 아닌것 같기도.
    밑에부분은 로그인 이슈 같음. interceptors 개념 파악 필요.
     */

    public static <T> T createApi(Class<T> service, String authToken) {
        if(!ValidationUtil.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if(!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(service);
    }

    private static HttpLoggingInterceptor loggingInterceptor;
    private static HttpLoggingInterceptor createLogginInterceptor() {
        if(loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return loggingInterceptor;
    }
}
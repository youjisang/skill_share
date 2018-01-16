package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.model.m_class.Tutor;
import com.immymemine.kevin.skillshare.model.user.Following;
import com.immymemine.kevin.skillshare.model.user.SubscribedClass;
import com.immymemine.kevin.skillshare.model.user.User;
import com.immymemine.kevin.skillshare.network.Response;
import com.immymemine.kevin.skillshare.network.user.SignUpRequestBody;
import com.immymemine.kevin.skillshare.network.user.UserResponse;
import java.util.List;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @POST("user/joinGroup")
    Observable<Response> joinGroup(@Body Group group, @Query("userId") String userId);

    //TODO client단에서는 변경사항이 있는지 여부만 확인하고 있으면 server 측 데이터를 아예 바꾸기
    @PUT("user/followSkills/{userId}")
    Observable<User> followSkills(@Path("userId") String userId, @Body List<String> skills);

    @POST("user/subscribeClass")
    Observable<SubscribedClass> subscribeClass(@Query("classId") String classId);

    @Multipart
    @POST("user/uploadImageFile")
    Observable<Response> uploadImageFile(@Query("userId") String userId, @Part MultipartBody.Part image);

    @GET("user/imageFile/{userId}")
    Observable<User> downloadImage(@Path("userId")String userId);

    @POST("user/setNickname")
    Observable<Response> setNickname(@Query("userId") String userId, @Query("nickname") String nickname);

    //    @PUT("user/imageUrl/{userId}/{imageUrl}")
//    Observable<User> putImageUrl(@Path("userId") String userId, @Path("imageUrl") String imageUrl);
}

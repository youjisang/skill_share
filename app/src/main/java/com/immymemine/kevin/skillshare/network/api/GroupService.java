package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.group.Chat;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by quf93 on 2018-01-02.
 */

public interface GroupService {
    @GET("group/{id}/{position}")
    Observable<List<Chat>> getChatList(@Path("id") String id, @Path("position") int position);
}

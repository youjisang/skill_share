package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.group.Comment;
import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.network.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by quf93 on 2018-01-02.
 */

public interface GroupService {
    @GET("group/getComments/{groupId}/{position}")
    Observable<List<Comment>> getComments(@Path("groupId") String groupId, @Path("position") int position);

    @GET("group/{groupId}")
    Observable<Group> getGroupInitiateData(@Path("groupId") String groupId);

    @POST("group/sendComment")
    Observable<Response> sendComment(@Query("groupName") String groupName, @Body Comment comment);
}

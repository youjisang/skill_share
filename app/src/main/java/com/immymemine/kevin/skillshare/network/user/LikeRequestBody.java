package com.immymemine.kevin.skillshare.network.user;

/**
 * Created by quf93 on 2018-01-08.
 */

public class LikeRequestBody {
    String discussionId;
    String userId;
    String userName;
    String resId;

    public LikeRequestBody(String discussionId, String userId, String userName, String resId) {
        this.discussionId = discussionId;
        this.userId = userId;
        this.userName = userName;
        this.resId = resId;
    }
}

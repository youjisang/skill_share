package com.immymemine.kevin.skillshare.network;

/**
 * Created by quf93 on 2018-01-08.
 */

public class LikeBody {
    String discussionId;
    String userId;
    String userName;
    String resId;

    public LikeBody(String discussionId, String userId, String userName, String resId) {
        this.discussionId = discussionId;
        this.userId = userId;
        this.userName = userName;
        this.resId = resId;
    }
}

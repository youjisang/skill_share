package com.immymemine.kevin.skillshare.network.gcm;

/**
 * Created by quf93 on 2017-12-11.
 */

public class SendMessageBody {
    String userName;
    String userId;

    public SendMessageBody(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }
}

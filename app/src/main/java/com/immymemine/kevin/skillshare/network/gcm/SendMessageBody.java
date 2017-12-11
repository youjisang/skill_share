package com.immymemine.kevin.skillshare.network.gcm;

/**
 * Created by quf93 on 2017-12-11.
 */

public class SendMessageBody {
    String userName;
    String time;
    String resId;

    public SendMessageBody(String userName, String time, String resId) {
        this.userName = userName;
        this.time = time;
        this.resId = resId;
    }
}

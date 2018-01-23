package com.immymemine.kevin.skillshare.network.user;

import com.immymemine.kevin.skillshare.model.user.SubscribedClass;

/**
 * Created by quf93 on 2018-01-20.
 */

public class SubscribeResponse {
    String result;
    String message;
    SubscribedClass data;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public SubscribedClass getData() {
        return data;
    }
}

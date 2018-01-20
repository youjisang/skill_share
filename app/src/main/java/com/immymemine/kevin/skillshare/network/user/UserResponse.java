package com.immymemine.kevin.skillshare.network.user;

import com.immymemine.kevin.skillshare.model.user.User;

/**
 * Created by quf93 on 2017-12-01.
 */

public class UserResponse {
    private String result;
    private String message;
    private User data;
    private String token;

    public String getResult() {
        return result;
    }
    public String getMessage() {
        return message;
    }
    public User getData() {
        return data;
    }
    public String getToken() {
        return token;
    }
}
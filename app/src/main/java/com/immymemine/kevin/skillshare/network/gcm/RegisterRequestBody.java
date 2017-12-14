package com.immymemine.kevin.skillshare.network.gcm;

/**
 * Created by quf93 on 2017-11-26.
 */

public class RegisterRequestBody {
    private String userId;
    private String registrationId;

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}

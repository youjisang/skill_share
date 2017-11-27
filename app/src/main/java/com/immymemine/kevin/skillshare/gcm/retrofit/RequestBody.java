package com.immymemine.kevin.skillshare.gcm.retrofit;

/**
 * Created by quf93 on 2017-11-26.
 */

public class RequestBody {
    private String deviceName;
    private String deviceId;
    private String registrationId;

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}

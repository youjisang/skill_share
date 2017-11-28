package com.immymemine.kevin.skillshare.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by quf93 on 2017-11-26.
 */

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // token 이 갱신될 때마다 호출되어 토큰의 교체를 담당하는 method
        // registrationIntentService 에서 token 생성
        startService(new Intent(this, RegistrationIntentService.class));
    }
}

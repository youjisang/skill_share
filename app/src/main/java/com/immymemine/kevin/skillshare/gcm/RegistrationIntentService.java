package com.immymemine.kevin.skillshare.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.MainActivity;
import com.immymemine.kevin.skillshare.network.Response;
import com.immymemine.kevin.skillshare.network.RetrofitHelper;
import com.immymemine.kevin.skillshare.network.api.GCMService;
import com.immymemine.kevin.skillshare.network.gcm.RegisterRequestBody;
import com.immymemine.kevin.skillshare.utility.StateUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by quf93 on 2017-11-26.
 */

public class RegistrationIntentService extends IntentService {

    private final String TAG = "InstanceIDService";

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String userId = intent.getStringExtra("USER_ID");

        try {
            // token 값 생성
            InstanceID instanceID = InstanceID.getInstance(this);
            String registrationId = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            StateUtil.getInstance().getUserInstance().setRegistrationId(registrationId);
            // 서버 통신
            registerDevice(userId, registrationId);
        } catch (Exception e ) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void registerDevice(String userId, String registrationId) {
        // request body 세팅
        RegisterRequestBody requestBody = new RegisterRequestBody();
        requestBody.setUserId(userId);
        requestBody.setRegistrationId(registrationId);

        // retrofit
        RetrofitHelper.createApi(GCMService.class)
                .register(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);
    }

    private void handleResponse(Response response) {
        Log.d(TAG, "response : " + response.getResult() + ", " + response.getMessage());
    }

    private void handleError(Throwable error) {
        Log.d(TAG, "response error : " + error.getMessage());
    }
}

package com.immymemine.kevin.skillshare.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.gcm.retrofit.RegistrationInterface;
import com.immymemine.kevin.skillshare.gcm.retrofit.RegistrationRequestBody;
import com.immymemine.kevin.skillshare.gcm.retrofit.RegistrationResponseBody;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        String deviceId = intent.getStringExtra("DEVICE_ID");
        String deviceName = intent.getStringExtra("DEVICE_NAME");

        try {
            // token 값 생성
            InstanceID instanceID = InstanceID.getInstance(this);
            String registrationId = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d("registrationId", registrationId);
            // 서버 통신
            registerDevice(deviceId, deviceName, registrationId);
        } catch (Exception e ) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void registerDevice(String deviceId, String deviceName, String registrationId) {
        // request body 세팅
        RegistrationRequestBody registrationRequestBody = new RegistrationRequestBody();
        registrationRequestBody.setDeviceId(deviceId);
        registrationRequestBody.setDeviceName(deviceName);
        registrationRequestBody.setRegistrationId(registrationId);

        // retrofit initiate
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // bind interface
        RegistrationInterface registration = retrofit.create(RegistrationInterface.class);

        // post
        Call<RegistrationResponseBody> c = registration.registerDevice(registrationRequestBody);
        c.enqueue(new Callback<RegistrationResponseBody>() {
            @Override
            public void onResponse(Call<RegistrationResponseBody> call, Response<RegistrationResponseBody> response) {
                RegistrationResponseBody registrationResponseBody = response.body();

                Intent intent = new Intent(ConstantUtil.REGISTRATION);
                intent.putExtra("result", registrationResponseBody.getResult());
                intent.putExtra("message", registrationResponseBody.getMessage());

                // 등록되어 있는 local broadcast 에 등록 결과 및 메시지를 방송으로 던져준다
                LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<RegistrationResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

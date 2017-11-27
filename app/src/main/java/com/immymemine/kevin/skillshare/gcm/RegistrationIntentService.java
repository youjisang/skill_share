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
import com.immymemine.kevin.skillshare.gcm.retrofit.RequestBody;
import com.immymemine.kevin.skillshare.gcm.retrofit.ResponseBody;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by quf93 on 2017-11-26.
 */

public class RegistrationIntentService extends IntentService {

    private final String TAG = "InstanceIDService";
    private final String SENDER_ID = getString(R.string.gcm_defaultSenderId);
    private final String URL = "http://~"; // TODO

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
            String registrationId = instanceID.getToken(SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            // 서버 통신
            registerDevice(deviceId, deviceName, registrationId);
        } catch (Exception e ) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void registerDevice(String deviceId, String deviceName, String registrationId) {
        // request body 세팅
        RequestBody requestBody = new RequestBody();
        requestBody.setDeviceId(deviceId);
        requestBody.setDeviceName(deviceName);
        requestBody.setRegistrationId(registrationId);

        // retrofit initiate
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                // .addConverterFactory(GsonConverterFactory.create())
                .build();

        // bind interface
        RegistrationInterface registration = retrofit.create(RegistrationInterface.class);

        // post
        Call<ResponseBody> c = registration.registerDevice(requestBody);
        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();

                Intent intent = new Intent(ConstantUtil.REGISTRATION);
                intent.putExtra("result", responseBody.getResult());
                intent.putExtra("message", responseBody.getMessage());

                // 등록되어 있는 local broadcast 에 등록 결과 및 메시지를 방송으로 던져준다
                LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

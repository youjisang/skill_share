package com.immymemine.kevin.skillshare.gcm;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by quf93 on 2017-11-26.
 */

public class NotificationHelper {

    NotificationManager mNotificationManager;
    NotificationChannel mChannel = null;

    Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String mChannelId, String channelName) {
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mChannel = new NotificationChannel(mChannelId, channelName,
                    NotificationManager.IMPORTANCE_LOW);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mNotificationManager.createNotificationChannel(mChannel);
    }

    private void changeNotificationSettings(String mChannelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, mChannelId);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        context.startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void deleteNotificationChannel(String mChannelId) {
        mNotificationManager.deleteNotificationChannel(mChannelId);
    }
}

package com.immymemine.kevin.skillshare.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmListenerService;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.MainActivity;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

/**
 * Created by quf93 on 2017-11-26.
 */

public class SkillShareGcmListenerService extends GcmListenerService {

    private final int REQUEST_CODE = 0;

    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        String message = bundle.getString("message");

        Intent intent = new Intent(ConstantUtil.RECEIVED);
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(SkillShareGcmListenerService.this).sendBroadcast(intent);

        sendNotification(message);
    }

    // notification 발송
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder notificationBuilder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(this,"default")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.noti_icon)
                    .setContentTitle("TEST_TITLE")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
        } else {
            notificationBuilder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.noti_icon)
                    .setContentTitle("TEST_TITLE")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}

package org.d3ifcool.nhernak.services;

import android.app.Notification;
import android.app.NotificationManager;

import com.google.firebase.messaging.RemoteMessage;

import org.d3ifcool.nhernak.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Pesan Baru")
                .setContentText("Anda mendapatkan pesan baru")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        int mNotificationId = (int) System.currentTimeMillis();
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, noti);

    }
}

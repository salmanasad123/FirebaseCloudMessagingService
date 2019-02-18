package com.example.teamas.firebasecloudmessagingservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseInstanceIDService extends FirebaseMessagingService {

    public static final String TAG = "MTAG";
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotifyUser(remoteMessage.getData().get("name"));
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed Token: " + token);
        SharedPrefManager.getInstance(getApplicationContext()).storeToken(token);
    }

    public void NotifyUser(String data) {
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultIntent.putExtra("data", data);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, 0);

        Notification notification = new Notification.Builder(getApplicationContext(), NotificationApp.CHANNEL_ID)
                .setAutoCancel(true)
                .setContentTitle("New Notification")
                .setContentText(data)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1, notification);

    }
}

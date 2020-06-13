package com.example.quizapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.quizapp.data.State;

public class Notifications {
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    public static void getDailyNotifications(Context context, State state){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel primaryChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Daily Quiz", NotificationManager.IMPORTANCE_DEFAULT);
            primaryChannel.enableLights(true);
            primaryChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(primaryChannel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String title = "Daily Quiz Time";
        String notificationContent = "What is the capital of "+state.getStateName()+" ?";

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(notificationContent)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID, nBuilder.build());
    }
}

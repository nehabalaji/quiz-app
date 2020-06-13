package com.example.quizapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

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
    }
}

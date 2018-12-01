package com.example.mdbook;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Required for proper reminder functionality. It builds a notification.
 * 
 * @author Raj Kapadia
 * @author Vanessa Peng
 * @see AddReminderActivity
 */
public class AlarmService extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {

            int notificationID = intent.getExtras().getInt("notificationID");

            Intent mainIntent = new Intent(context, AddReminderActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.iconlogo)
                    .setContentTitle("Reminder!")
                    .setContentText("Reminder to take pictures!")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent);

            notificationManager.notify(notificationID, builder.build());

        }
    }


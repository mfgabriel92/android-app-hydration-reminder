package com.example.android.hydrationreminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.android.hydrationreminder.sync.ReminderTask;
import com.example.android.hydrationreminder.sync.WaterReminderIntentService;

public class NotificationUtils {

    private static int WATER_REMINDER_PENDING_INTENT_ID = 1001;
    private static int WATER_REMINDER_NOTIFICATION_ID = 2002;
    private static String WATER_REMINDER_NOTIFICATION_CHANNEL_ID = "water_reminder_notification_channel";

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void reminderNotificationWhenCharging(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                WATER_REMINDER_NOTIFICATION_CHANNEL_ID,
                context.getString(R.string.main_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, WATER_REMINDER_NOTIFICATION_CHANNEL_ID)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setSmallIcon(R.drawable.ic_drink_notification)
            .setLargeIcon(largeIcon(context))
            .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
            .setContentText(context.getString(R.string.charging_reminder_notification_text))
            .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.charging_reminder_notification_title)))
            .setContentIntent(contentIntent(context))
            .addAction(drinkWaterReminderAction(context))
            .addAction(ignoreReminderAction(context))
            .setAutoCancel(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Log.d("NotificationUtils", "Should show this fucking notification.");
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, WaterReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTask.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
            context,
            WATER_REMINDER_PENDING_INTENT_ID,
            ignoreReminderIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new NotificationCompat.Action(
            R.drawable.ic_cancel_black_24px,
            context.getString(R.string.cancel_notification),
            ignoreReminderPendingIntent
        );
    }

    private static NotificationCompat.Action drinkWaterReminderAction(Context context) {
        Intent drinkWaterReminderIntent = new Intent(context, WaterReminderIntentService.class);
        drinkWaterReminderIntent.setAction(ReminderTask.ACTION_INCREMENT_WATER_TASK);

        PendingIntent drinkWaterReminderPendingIntent = PendingIntent.getService(
            context,
            WATER_REMINDER_PENDING_INTENT_ID,
            drinkWaterReminderIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        );

        return new NotificationCompat.Action(
            R.drawable.ic_local_drink_black_24px,
            context.getString(R.string.drink_water_notification),
            drinkWaterReminderPendingIntent
        );
    }

    private static PendingIntent contentIntent(Context context) {
        return PendingIntent.getActivity(
            context,
            WATER_REMINDER_PENDING_INTENT_ID,
            new Intent(context, MainActivity.class),
            PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static Bitmap largeIcon(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_local_drink_black_24px);
    }
}

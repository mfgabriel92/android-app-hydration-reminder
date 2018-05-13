package com.example.android.hydrationreminder.sync;

import android.content.Context;

import com.example.android.hydrationreminder.NotificationUtils;
import com.example.android.hydrationreminder.PreferenceUtilities;

public class ReminderTask {

    public static final String ACTION_INCREMENT_WATER_TASK = "increment-water-count";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String ACTION_CHARGING_REMINDER = "charging-reminder";

    public static void executeTask(Context context, String action) {
        switch (action) {
            case ACTION_INCREMENT_WATER_TASK:
                incrementWaterCount(context);
                break;
            case ACTION_DISMISS_NOTIFICATION:
                NotificationUtils.clearAllNotifications(context);
                break;
            case ACTION_CHARGING_REMINDER:
                issueChargingReminder(context);
                break;
            default:
                break;
        }
    }

    private static void incrementWaterCount(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
        NotificationUtils.clearAllNotifications(context);
    }

    private static void issueChargingReminder(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
        NotificationUtils.reminderNotificationWhenCharging(context);
    }
}

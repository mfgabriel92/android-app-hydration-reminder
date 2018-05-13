package com.example.android.hydrationreminder.sync;

import android.content.Context;

import com.example.android.hydrationreminder.PreferenceUtilities;

public class ReminderTask {

    public static final String ACTION_INCREMENT_WATER_TASK = "increment-water-count";

    public static void executeTask(Context context, String action) {
        switch (action) {
            case ACTION_INCREMENT_WATER_TASK:
                incrementWaterCount(context);
                break;
            default:
                break;
        }
    }

    private static void incrementWaterCount(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
    }
}

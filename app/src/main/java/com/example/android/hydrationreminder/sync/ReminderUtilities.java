package com.example.android.hydrationreminder.sync;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtilities {

    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;
    private static final String REMINDER_JOB_TAG = "hydration_reminder_tag";
    private static boolean sInitialized;

    synchronized public static void scheduleChargingReminder(final Context context) {
        if (sInitialized) {
            return;
        }

        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job job = firebaseJobDispatcher.newJobBuilder()
            .setService(WaterReminderFirebaseJobService.class)
            .setTag(REMINDER_JOB_TAG)
            .setConstraints(Constraint.DEVICE_CHARGING)
            .setLifetime(Lifetime.FOREVER)
            .setRecurring(true)
            .setTrigger(Trigger.executionWindow(
                REMINDER_INTERVAL_SECONDS,
                REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
            ))
            .setReplaceCurrent(true)
            .build();

        firebaseJobDispatcher.schedule(job);
        sInitialized = true;
    }
}

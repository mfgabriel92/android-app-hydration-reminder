package com.example.android.hydrationreminder.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class WaterReminderIntentService extends IntentService {

    public WaterReminderIntentService() {
        super(WaterReminderIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ReminderTask.executeTask(this, intent.getAction());
    }
}

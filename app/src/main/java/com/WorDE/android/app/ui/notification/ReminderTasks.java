package com.WorDE.android.app.ui.notification;

import android.content.Context;

public class ReminderTasks {

    public static void executeTask(Context context) {
            issueChargingReminder(context);
    }
    private static void issueChargingReminder(Context context) {
        NotificationUtilities.remindUserToComeBack(context);
    }
}
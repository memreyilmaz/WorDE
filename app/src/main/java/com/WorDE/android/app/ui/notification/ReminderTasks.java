package com.WorDE.android.app.ui.notification;

import android.content.Context;

public class ReminderTasks {

    public static void executeTask(Context context) {
            comeBackReminder(context);
    }
    private static void comeBackReminder(Context context) {
        NotificationUtilities.remindUserToComeBack(context);
    }
}
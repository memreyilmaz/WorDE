package com.example.android.worde.ui.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static com.example.android.worde.Config.LAST_TIME_LAUNCH;
import static com.example.android.worde.Config.LAUNCH_TIME;

public class ReminderUtilities {

    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;
    private static final String REMINDER_JOB_TAG = "come_back_reminder_tag";
    private static boolean sInitialized;
    private final Context context;
    private static Long MILLISECS_PER_DAY = 86400000L;
    private static Long MILLISECS_PER_MIN = 60000L;

    private static long DELAY = MILLISECS_PER_DAY * 2;   // 3 minutes (for testing)

    //private static long delay = MILLISECS_PER_DAY * 3;   // 3 days
    public ReminderUtilities(Context context) {
        this.context = context;
    }

    public void ADsetAlarm(){
        SharedPreferences mSharedPreferences = context.getSharedPreferences(LAUNCH_TIME, MODE_PRIVATE);
        mSharedPreferences.getLong(LAST_TIME_LAUNCH, 0);
        if (mSharedPreferences.getLong(LAST_TIME_LAUNCH, Long.MAX_VALUE) < System.currentTimeMillis() - DELAY);
       // Date date = Date.from()
//            sendNotification();
    }

   /* public void setAlarm(){
      //  Intent serviceIntent = new Intent(this, ReminderUtilities.class);
        PendingIntent pi = PendingIntent.getService(this, 131313, serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager= (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + DELAY, pi);

    }*/
    synchronized public static void setNotificationPlanner(@NonNull final Context context) {

        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(NotificationFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);
        sInitialized = true;
    }

}


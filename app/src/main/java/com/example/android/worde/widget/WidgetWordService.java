package com.example.android.worde.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.android.worde.R;

import java.util.Random;

public class WidgetWordService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

        int[] allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));

            RemoteViews remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.word_of_the_day_widget);

            // Register an onClickListener
            Intent clickIntent = new Intent(this.getApplicationContext(),
                    WordWidgetProvider.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(), 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_word_artikel_text, pendingIntent);

            //WordRepository mRepository = WordRepository.getInstance(getApplication());
            //Word word = mRepository.getRandomWordForWidget();

            Intent sintent = new Intent(this.getApplicationContext(), WidgetRemoteViewsService.class);
            sintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

            remoteViews.setRemoteAdapter(R.id.word_list_view_for_widget, sintent);

            getApplicationContext().startService(sintent);

            remoteViews.setTextViewText(R.id.appwidget_word_headline, getResources().getString(R.string.widget_headline));

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
package com.example.android.worde.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.worde.R;
import com.example.android.worde.ui.MainActivity;

public class WordWidgetProvider extends AppWidgetProvider {

   @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
           updateWidgets(context, appWidgetManager, appWidgetIds);
   }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        Intent detailLaunchIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailLaunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.word_of_the_day_widget);
        Intent remoteViewsIntent = new Intent(context, WidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.word_list_view_for_widget, remoteViewsIntent);
        views.setTextViewText(R.id.appwidget_word_headline, "Wort des Tages");
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
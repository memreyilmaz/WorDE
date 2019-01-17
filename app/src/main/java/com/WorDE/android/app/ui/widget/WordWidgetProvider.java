package com.WorDE.android.app.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.WorDE.android.app.R;
import com.WorDE.android.app.ui.MainActivity;

public class WordWidgetProvider extends AppWidgetProvider {

   @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       for (int appWidgetId : appWidgetIds) {
           updateAppWidget(context, appWidgetManager, appWidgetId);
       }
    }
    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent activityLaunchIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityLaunchIntent, 0);

        Intent activityLaunchIntentForListView = new Intent(context, MainActivity.class);
        PendingIntent pendingIntentForListView = PendingIntent.getActivity(context, 0, activityLaunchIntentForListView, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.word_of_the_day_widget);
        views.setPendingIntentTemplate(R.id.word_list_view_for_widget, pendingIntentForListView);
        Intent remoteViewsIntent = new Intent(context, WidgetRemoteViewsService.class);
        remoteViewsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        views.setRemoteAdapter(R.id.word_list_view_for_widget, remoteViewsIntent);
        views.setTextViewText(R.id.appwidget_word_headline, context.getString(R.string.widget_headline));
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.word_list_view_for_widget);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onEnabled(Context context) {
    }
    @Override
    public void onDisabled(Context context) {
    }
}
package com.example.android.worde;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.worde.ui.list.WordListActivity;

/**
 * Implementation of App Widget functionality.
 */
public class WordOfTheDayWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, WordListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.word_of_the_day_widget);
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.appwidget_word_text,pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        // Construct the RemoteViews object
        /*RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.word_of_the_day_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.appwidget_artikel_text, widgetText);
        views.setTextViewText(R.id.appwidget_word_text, widgetText);*/
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


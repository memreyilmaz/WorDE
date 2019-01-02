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
    /*@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context, WidgetWordService.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        Intent intent = new Intent(context.getApplicationContext(),
                WidgetWordService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        context.startService(intent);

      /*  for (int appWidgetId : appWidgetIds) {
            loadWordForWidget(context, appWidgetManager, appWidgetId);
        }*/
    //}*/

   @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
           updateWidgets(context, appWidgetManager, appWidgetIds);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        loadWordForWidget(context, appWidgetManager, appWidgetId);
    }

    private static void loadWordForWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        //ComponentName thisWidget = new ComponentName(context, WordWidgetProvider.class);
        //int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        // Build the intent to call the service
       // Intent intent = new Intent(context.getApplicationContext(), WidgetRemoteViewsService.class);

        // Update the widgets via the service
//        context.startService(intent);

        //Intent worddetailLaunchIntent = new Intent(context, WordListActivity.class);
        //Bundle extras = new Bundle();
        //extras.putParcelable(MainActivity.DETAIL_KEY, mCurrentRecipe);
        //worddetailLaunchIntent.putExtras(extras);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, worddetailLaunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //String widgetWordArtikel = mWord.getWordArtikel();
        //String widgetWordName = mWord.getWordName();
        //String widgetWordExample = mWord.getWordExample();
        //Intent sintent = new Intent(this.getApplicationContext(), WidgetRemoteViewsService.class);

        //sintent.putExtra("word", word);

        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.word_of_the_day_widget);
        //Intent intent = new Intent(context, WidgetRemoteViewsService.class);
        //intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        //intent.putExtra("artikel", widgetWordArtikel);
        //intent.putExtra("word", widgetWordName);
        //intent.putExtra("example", widgetWordExample);
        //views.setRemoteAdapter(R.id.word_list_view_for_widget, intent);
        //views.setTextViewText(R.id.appwidget_word_headline, "Wort des Tages");
        //views.setOnClickPendingIntent(R.id.layout_for_widget, pendingIntent);
        //appWidgetManager.updateAppWidget(appWidgetId, views);
        Intent detailLaunchIntent = new Intent(context, MainActivity.class);
        //Bundle extras = new Bundle();
        //extras.putParcelable(MainActivity.DETAIL_KEY, mCurrentRecipe);
        //detailLaunchIntent.putExtras(extras);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailLaunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.word_of_the_day_widget);
        Intent remoteViewsIntent = new Intent(context, WidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.word_list_view_for_widget, remoteViewsIntent);
        //views.setEmptyView(R.id.ingredients_list_view_for_widget, R.id.empty_view_for_widget);
        views.setTextViewText(R.id.appwidget_word_headline, "Wort des Tages");
        //views.setTextViewText(R.id.recipe_name_textview_for_widget, mCurrentRecipe.getName());
        views.setOnClickPendingIntent(R.id.intent, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
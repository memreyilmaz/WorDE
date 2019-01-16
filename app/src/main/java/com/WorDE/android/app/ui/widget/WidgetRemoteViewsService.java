package com.WorDE.android.app.ui.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.WorDE.android.app.R;
import com.WorDE.android.app.database.Word;
import com.WorDE.android.app.database.WordDatabase;

import java.util.ArrayList;

public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    static ArrayList<Word> words = new ArrayList<Word>();
    int mAppWidgetId;
    public RecipeRemoteViewsFactory(Context context, Intent intent){
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
    }
    @Override
    public void onDataSetChanged() {

        getWordForWidget(mContext);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getCount() {
        return words == null ? 0: words.size();

      /*  if (words != null){
            return 1;
        }
        else return 0;*/
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        String widgetWordArtikel = null;
        Word mWidgetWord = words.get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        if (!(mWidgetWord.getWordArtikel() == null || mWidgetWord.getWordArtikel().equals(""))){
            widgetWordArtikel = mWidgetWord.getWordArtikel();
        }
        String widgetWordLevel = mWidgetWord.getWordLevel();
        String widgetlevel = widgetWordLevel.substring(0, 1).toUpperCase() + widgetWordLevel.substring(1);
        String widgetWordName = mWidgetWord.getWordName();
        String widgetWordExample = mWidgetWord.getWordExample();
        views.setTextViewText(R.id.appwidget_word_level_text, widgetlevel);
        if (widgetWordArtikel != null){
            views.setViewVisibility(R.id.appwidget_word_artikel_text, View.VISIBLE);
            views.setTextViewText(R.id.appwidget_word_artikel_text, widgetWordArtikel);
        }
        views.setTextViewText(R.id.appwidget_word_name_text, widgetWordName);
        views.setTextViewText(R.id.appwidget_word_example_text, widgetWordExample);

        Intent fillInIntent = new Intent();
        views.setOnClickFillInIntent(R.id.widget_item_layout, fillInIntent);
        return views;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onDestroy() {
        words.clear();
    }

    @SuppressLint("StaticFieldLeak")
    private void getWordForWidget(Context context) {
        words.clear();

        new AsyncTask<Context, Void, Word>(){
            @Override
            protected Word doInBackground(Context... context) {
                Word word= null;
                WordDatabase database = WordDatabase.getInstance(context[0]);
                word = database.wordDao().getRandomWordForWidget();
                if(word != null){
                    words.add(word);
                }
                return word;
            }
            @Override
            protected void onPostExecute(Word word) {
                super.onPostExecute(word);
               // onDataSetChanged();
             //   AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
             //   ComponentName thisWidget = new ComponentName(context, WordWidgetProvider.class);
             //   int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
             //   appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.word_list_view_for_widget);
            }
        }.execute(context);
    }
}
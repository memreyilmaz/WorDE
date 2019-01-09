package com.example.android.worde.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordDatabase;

import java.util.ArrayList;

public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    static ArrayList<Word> words = new ArrayList<Word>();
    static Word mWord;

    public RecipeRemoteViewsFactory(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return words == null ? 0: words.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        String widgetWordArtikel = null;
        Word mWidgetWord = words.get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        if (!(mWidgetWord.getWordArtikel() == null || mWidgetWord.getWordArtikel().equals(""))){
            widgetWordArtikel = mWidgetWord.getWordArtikel();
        }
        String widgetWordName = mWidgetWord.getWordName();
        String widgetWordExample = mWidgetWord.getWordExample();
        if (widgetWordArtikel != null){
            views.setViewVisibility(R.id.appwidget_word_artikel_text, View.VISIBLE);
            views.setTextViewText(R.id.appwidget_word_artikel_text, widgetWordArtikel);
        }
        views.setTextViewText(R.id.appwidget_word_name_text, widgetWordName);
        views.setTextViewText(R.id.appwidget_word_example_text, widgetWordExample);
        return views;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
         getWordForWidget(mContext);
    }

    @Override
    public void onDestroy() {
        if (words != null) {
            words = null;}
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    public void getWordForWidget(Context context) {
        words.clear();
        WordDatabase mDb = WordDatabase.getInstance(context);
        WidgetExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mWord =  mDb.wordDao().getRandomWordForWidget();
                words.add(mWord);
            }
        });
    }
}
package com.example.android.worde.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;

import java.util.ArrayList;

public class WordService extends IntentService {

   /* public WordService(String name) {
        super(name);
    }*/

    public WordService() {
        super("WordService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WordRepository mRepository = WordRepository.getInstance(getApplication());
        Word word = mRepository.getRandomWordForWidget();
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(word);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WordWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.word_list_view_for_widget);
        WordWidgetProvider.updateWidgets(this, appWidgetManager, appWidgetIds);

    }

    public static void startToUpdateWidgets(Context context) {
        Intent intent = new Intent(context, WordService.class);
        context.startService(intent);
    }
}

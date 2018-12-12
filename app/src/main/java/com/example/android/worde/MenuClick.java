package com.example.android.worde;

import android.content.Context;
import android.content.Intent;

import com.example.android.worde.ui.Analytics;
import com.example.android.worde.ui.list.WordListActivity;

import static com.example.android.worde.Config.SELECTED_LEVEL;

public class MenuClick {
    Context context;
    public MenuClick(Context context){
        this.context = context;
    }
    public void launchWordListActivity(String wordLevel){
        Intent levelIntent = new Intent(context, WordListActivity.class);
        levelIntent.putExtra(SELECTED_LEVEL, wordLevel);
        context.startActivity(levelIntent);

        Analytics.logLevelEvent(context, wordLevel);
    }
}

package com.WorDE.android.app.ui.helper;

import android.content.Context;
import android.content.Intent;

import com.WorDE.android.app.ui.Analytics;
import com.WorDE.android.app.ui.list.WordListActivity;

import static com.WorDE.android.app.Config.SELECTED_LEVEL;

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

package com.example.android.worde;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.worde.ui.list.WordListActivity;


public class MenuClick {
    Context context;
    Bundle levelBundle;
    public static final String SELECTED_LEVEL_BUNDLE = "SELECTED_LEVEL_BUNDLE";
    public MenuClick(Context context){
        this.context = context;
    }

    public void launchWordListActivity(String wordLevel){
        Intent levelIntent = new Intent(context, WordListActivity.class);
        levelIntent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
        //levelBundle = new Bundle();
        //levelBundle.putString(WordListActivity.SELECTED_LEVEL, wordLevel);
        //context.startActivity(new Intent(context, WordListActivity.class).putExtra(SELECTED_LEVEL_BUNDLE, levelBundle));
        context.startActivity(levelIntent);
    }
}

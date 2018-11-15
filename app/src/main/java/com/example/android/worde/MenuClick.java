package com.example.android.worde;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.worde.ui.favourite.FavouritesActivity;
import com.example.android.worde.ui.list.WordListActivity;


public class MenuClick {
    Context context;
    public MenuClick(Context context){
        this.context = context;
    }

    public void launchWordListActivity(String wordLevel){
        Intent levelIntent = new Intent(context, WordListActivity.class);
        levelIntent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
        context.startActivity(levelIntent);
    }
    public void launchFavouriteWordsActivity(View view){
        Intent favouriteIntent = new Intent(view.getContext(), FavouritesActivity.class);
        view.getContext().startActivity(favouriteIntent);
    }
}

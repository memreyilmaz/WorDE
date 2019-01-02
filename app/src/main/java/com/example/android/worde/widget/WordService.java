package com.example.android.worde.widget;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class WordService extends IntentService {

   /* public WordService(String name) {
        super(name);
    }*/

    public WordService() {
        super("WordService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}

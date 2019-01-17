package com.WorDE.android.app.ui;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

    public static void logLevelEvent(Context context, String selectedLevel){
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.LEVEL_NAME, selectedLevel);
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Param.LEVEL_NAME, params);
    }
    public static void logSearchEvent(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SEARCH,null);
    }
    public static void logShareEvent(Context context){
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SHARE,null);
    }
}

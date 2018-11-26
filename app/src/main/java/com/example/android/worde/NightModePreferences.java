package com.example.android.worde;

import android.content.Context;
import android.content.SharedPreferences;

public class NightModePreferences {
    private SharedPreferences mSharedPreferences ;
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String DAY_NIGHT = "DAY_NIGHT_PREFS";
    public NightModePreferences(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(DAY_NIGHT,Context.MODE_PRIVATE);
    }
    public void setNightModeState(Boolean nightModeState) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(NIGHT_MODE,nightModeState);
        editor.apply();
    }
    public boolean loadNightModeState (){
        boolean nightModeState = mSharedPreferences.getBoolean(NIGHT_MODE,false);
        return nightModeState;
    }
}

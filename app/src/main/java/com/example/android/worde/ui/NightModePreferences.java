package com.example.android.worde.ui;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.android.worde.Config.DAY_NIGHT;
import static com.example.android.worde.Config.NIGHT_MODE;

public class NightModePreferences {
    private SharedPreferences mSharedPreferences ;

    public NightModePreferences(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(DAY_NIGHT,Context.MODE_PRIVATE);
    }
    public void setNightModeState(Boolean nightModeState) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(NIGHT_MODE,nightModeState);
        editor.apply();
    }
    public boolean loadNightModeState (){
        return mSharedPreferences.getBoolean(NIGHT_MODE,false);
    }
}

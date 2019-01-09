package com.example.android.worde.ui;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.ViewGroup;

import com.example.android.worde.R;

public class SnackbarShaper {

    public static void configSnackbar(Context context, Snackbar snackbar) {
        addMargins(snackbar);
        setRoundBorders(context, snackbar);
        setSnackbarTextColor(context, snackbar);
        ViewCompat.setElevation(snackbar.getView(), 6f);
    }
    private static void addMargins(Snackbar snackbar) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snackbar.getView()
                .getLayoutParams();
        params.setMargins(16, 16, 16, 16);
        snackbar.getView().setLayoutParams(params);
    }
    private static void setRoundBorders(Context context, Snackbar snackbar) {
        snackbar.getView().setBackground(context.getDrawable(R.drawable.config_snackbar));
    }
    private static void setSnackbarTextColor(Context context, Snackbar snackbar) {
        snackbar.setActionTextColor(ContextCompat.getColor(context,
                R.color.snackbarActionTextColor));
    }
}
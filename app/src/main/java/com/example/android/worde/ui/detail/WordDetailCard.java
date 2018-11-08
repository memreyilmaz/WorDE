package com.example.android.worde.ui.detail;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.android.worde.R;


public class WordDetailCard extends CardView {

    public WordDetailCard(Context context) {
        super(context);
        initialize(context);
    }
    public WordDetailCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }
    public void initialize(Context context){
        LayoutInflater.from(context).inflate(R.layout.word_detail_card_view, this);
    }
}

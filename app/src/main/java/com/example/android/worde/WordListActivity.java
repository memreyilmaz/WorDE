package com.example.android.worde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WordListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        WordListFragment fragment = new WordListFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_list_container, fragment).commit();

    }
}

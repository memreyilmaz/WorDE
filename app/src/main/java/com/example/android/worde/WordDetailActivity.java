package com.example.android.worde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.android.worde.WordListActivity.EXTRA_DATA_ID;

public class WordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        Bundle extras = getIntent().getExtras();
        extras.getString(EXTRA_DATA_ID);
        WordDetailFragment fragment = new WordDetailFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_detail_container, fragment).commit();

    }
}

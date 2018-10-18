package com.example.android.worde.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.ui.detail.WordDetailActivity;
import com.example.android.worde.database.WordRepository;

import java.util.List;

public class WordListActivity extends AppCompatActivity {
    public static final String SELECTED_LEVEL = "SELECTED_LEVEL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        String selectedLevel = getIntent().getStringExtra(SELECTED_LEVEL);
        WordRepository mRepository = new WordRepository(this.getApplication());

        WordListFragment fragment = new WordListFragment();
        WordListAdapter mAdapter = new WordListAdapter(this);
        fragment.setWordListAdapter(mAdapter);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_list_container, fragment).commit();

        WordLevelViewModelFactory factory = new WordLevelViewModelFactory(mRepository, selectedLevel);
        LevelViewModel mViewModel = ViewModelProviders.of(this, factory).get(LevelViewModel.class);

        mViewModel.getWordsByLevels().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                mAdapter.setWords(words);
            }
        });
        mAdapter.setOnItemClickListener(new WordListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Word word = mAdapter.getWordAtPosition(position);
                launchUpdateWordActivity(word);
            }
        });
    }

    public void launchUpdateWordActivity( Word word) {
        Intent intent = new Intent(this, WordDetailActivity.class);
       // intent.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWord());
       // intent.putExtra(EXTRA_DATA_ID, word.getWordId());
      //  startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

}

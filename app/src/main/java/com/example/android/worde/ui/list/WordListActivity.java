package com.example.android.worde.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;
import com.example.android.worde.ui.detail.WordDetailActivity;

public class WordListActivity extends AppCompatActivity {
    public static final String SELECTED_LEVEL = "SELECTED_LEVEL";
    int selectedWordId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        String selectedLevel = getIntent().getStringExtra(SELECTED_LEVEL);
        WordRepository mRepository = new WordRepository(this.getApplication());

        WordListFragment fragment = new WordListFragment();
        WordListAdapter mAdapter = new WordListAdapter();
        fragment.setWordListAdapter(mAdapter);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_list_container, fragment).commit();

        WordLevelViewModelFactory factory = new WordLevelViewModelFactory(mRepository, selectedLevel);
        LevelViewModel mViewModel = ViewModelProviders.of(this, factory).get(LevelViewModel.class);

        mViewModel.getWordsByLevels().observe(this, new Observer<PagedList<Word>>() {
            @Override
            public void onChanged(@Nullable PagedList<Word> words) {
                mAdapter.setWords(words);
            }
        });
        mAdapter.setOnItemClickListener(new WordListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Word word = mAdapter.getWordAtPosition(position);
                selectedWordId = word.getWordId();
                launchUpdateWordActivity();
            }
        });
    }

    public void launchUpdateWordActivity() {
        Intent intent = new Intent(this, WordDetailActivity.class);
        intent.putExtra(WordDetailActivity.SELECTED_WORD, selectedWordId);
        startActivity(intent);
    }
}

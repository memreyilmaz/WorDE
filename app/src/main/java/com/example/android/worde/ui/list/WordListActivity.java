package com.example.android.worde.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;
import com.example.android.worde.ui.detail.WordDetailActivity;
import com.example.android.worde.ui.favourite.AddFavouriteViewModel;

import java.util.List;

public class WordListActivity extends AppCompatActivity {
    public static final String SELECTED_LEVEL = "SELECTED_LEVEL";
    int selectedWordId;
    WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        String selectedLevel = getIntent().getStringExtra(SELECTED_LEVEL);
        WordRepository mRepository = new WordRepository(this.getApplication());
        WordListFragment fragment = new WordListFragment();
        mAdapter = new WordListAdapter();
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
                selectedWordId = word.getWordId();
                launchUpdateWordActivity();
            }
            @Override
            public void onFavouriteClick(View v, int position) {
                addToFavourites(position);
            }
        });
    }
    public void launchUpdateWordActivity() {
        Intent intent = new Intent(this, WordDetailActivity.class);
        intent.putExtra(WordDetailActivity.SELECTED_WORD, selectedWordId);
        startActivity(intent);
    }
    public void addToFavourites(int position){
        AddFavouriteViewModel mFavViewModel = ViewModelProviders.of(this).get(AddFavouriteViewModel.class);
        Word word = mAdapter.getWordAtPosition(position);
        int mWordID = word.getWordId();
        boolean mWordFavouriteStatus = word.getWordFavourite();

        if (!mWordFavouriteStatus) {
            mFavViewModel.setFavouriteStatus(1, mWordID);
            Toast.makeText(this, R.string.added_to_favourites, Toast.LENGTH_LONG).show();
        } else {
            mFavViewModel.setFavouriteStatus(0, mWordID);
            Toast.makeText(this, R.string.removed_from_favourites, Toast.LENGTH_LONG).show();
        }
    }
}

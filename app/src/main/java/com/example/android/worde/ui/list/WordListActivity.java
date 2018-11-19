package com.example.android.worde.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;
import com.example.android.worde.ui.DrawerActivity;
import com.example.android.worde.ui.detail.WordDetailActivity;
import com.example.android.worde.ui.favourite.AddFavouriteViewModel;
import com.example.android.worde.ui.favourite.AddFavouriteViewModelFactory;

import java.util.List;

public class WordListActivity extends DrawerActivity {
    public static final String SELECTED_LEVEL = "SELECTED_LEVEL";
    int selectedWordId;
    String titleLevel;
    String selectedLevel;
    WordListAdapter mAdapter;
    AddFavouriteViewModel mFavViewModel;
    boolean mWordFavouriteStatus;
    WordRepository mRepository;
    int mWordID;
    FrameLayout frameLayout;
    View snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_word_list, frameLayout);
        snackBar = findViewById(R.id.app_bar_main);
        Toolbar toolbar = findViewById(R.id.list_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.wordelogosmalltransparent);
        selectedLevel = getIntent().getStringExtra(SELECTED_LEVEL);
        setTitle(assignTitleLevel());

        mRepository = new WordRepository(this.getApplication());
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
                launchWordDetailActivity();
            }
            @Override
            public void onFavouriteClick(View v, int position) {
                addToFavourites(position);
            }
        });
    }
    public void launchWordDetailActivity() {
        startActivity (new Intent(this, WordDetailActivity.class)
                .putExtra(WordDetailActivity.SELECTED_WORD, selectedWordId));
    }
    public void addToFavourites(int position){
        Word word = mAdapter.getWordAtPosition(position);
        mWordID = word.getWordId();
        int mWordFavourite = mWordFavouriteStatus ? 1 : 0;
        AddFavouriteViewModelFactory factory = new AddFavouriteViewModelFactory(mRepository,mWordFavourite, mWordID);
        mFavViewModel = ViewModelProviders.of(this,factory).get(AddFavouriteViewModel.class);
        if (!mWordFavouriteStatus) {
            mFavViewModel.setFavouriteStatus(1, mWordID);
            Snackbar.make(snackBar, R.string.added_to_favourites, Snackbar.LENGTH_LONG).show();
        } else {
            mFavViewModel.setFavouriteStatus(0, mWordID);
            Snackbar.make(snackBar, R.string.removed_from_favourites, Snackbar.LENGTH_LONG).show();
        }
    }
    public String assignTitleLevel(){
        switch (selectedLevel){
            case "a1":
            titleLevel = getString(R.string.a1);
            break;
            case "a2":
            titleLevel = getString(R.string.a2);
            break;
            case "b1":
            titleLevel = getString(R.string.b1);
            break;
            }
        return titleLevel;
    }
}

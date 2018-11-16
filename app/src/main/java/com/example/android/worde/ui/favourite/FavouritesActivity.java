package com.example.android.worde.ui.favourite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;
import com.example.android.worde.ui.DrawerActivity;
import com.example.android.worde.ui.detail.WordDetailActivity;
import com.example.android.worde.ui.list.WordListAdapter;
import com.example.android.worde.ui.list.WordListFragment;

import java.util.List;

public class FavouritesActivity extends DrawerActivity {
    int selectedWordId;
    LoadFavouritesViewModel mViewModel;
    WordListAdapter mAdapter;
    WordRepository mRepository;
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
        setTitle(getString(R.string.user_favourites));
        mRepository = new WordRepository(this.getApplication());

        WordListFragment fragment = new WordListFragment();
        mAdapter = new WordListAdapter();
        fragment.setWordListAdapter(mAdapter);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_list_container, fragment).commit();

        mViewModel = ViewModelProviders.of(this).get(LoadFavouritesViewModel.class);
        mViewModel.getFavouriteWords().observe(this, new Observer<List<Word>>() {
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
                removeFromFavourites(position);
            }
        });
    }

    public void launchUpdateWordActivity() {
        Intent intent = new Intent(this, WordDetailActivity.class);
        intent.putExtra(WordDetailActivity.SELECTED_WORD, selectedWordId);
        startActivity(intent);
    }
    public void removeFromFavourites(int position){
        Word word = mAdapter.getWordAtPosition(position);
        int mWordID = word.getWordId();
        boolean mWordFavouriteStatus = word.getWordFavourite();
        int mWordFavourite = mWordFavouriteStatus ? 1 : 0;
        AddFavouriteViewModelFactory factory = new AddFavouriteViewModelFactory(mRepository,mWordFavourite, mWordID);
        AddFavouriteViewModel mFavViewModel = ViewModelProviders.of(this,factory).get(AddFavouriteViewModel.class);

        mFavViewModel.setFavouriteStatus(0, mWordID);

        Snackbar.make(snackBar, R.string.removed_from_favourites, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mFavViewModel.setFavouriteStatus(1, mWordID);
                        Snackbar.make(view, R.string.added_to_favourites_again, Snackbar.LENGTH_LONG).show();
                    }
                }).setActionTextColor(ContextCompat.getColor(getApplicationContext(),R.color.wordRed)).show();
    }
}
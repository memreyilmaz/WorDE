package com.example.android.worde.ui.detail;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;
import com.example.android.worde.ui.DrawerActivity;
import com.example.android.worde.ui.favourite.AddFavouriteViewModel;
import com.example.android.worde.ui.favourite.AddFavouriteViewModelFactory;

public class WordDetailActivity extends DrawerActivity {
    public static final String SELECTED_WORD = "SELECTED_WORD";
    int selectedWord;
    WordRepository mRepository;
    int mWordID;
    boolean mWordFavouriteStatus;
    String mWordArtikel;
    String mWordName;
    String mWordExample;
    WordDetailAdapter mAdapter;
    Word mCurrentWord;
    AddFavouriteViewModel mFavViewModel;
    ImageView mFavouriteImageView;
    FrameLayout frameLayout;
    View snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        frameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_word_detail, frameLayout);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        Toolbar toolbar = findViewById(R.id.detail_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        snackBar = findViewById(R.id.word_detail_container);

        selectedWord = getIntent().getIntExtra(SELECTED_WORD, 0);

        mRepository = new WordRepository(this.getApplication());
        DetailViewModelFactory factory = new DetailViewModelFactory(mRepository, selectedWord);
        DetailViewModel mViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);

        mViewModel.getWordById().observe(this, new Observer<Word>() {
            @Override
            public void onChanged(@Nullable Word word) {

                WordDetailFragment fragment = new WordDetailFragment();
                mAdapter = new WordDetailAdapter(word);
                fragment.setWordDetailAdapter(mAdapter);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.word_detail_container, fragment).commit();
                mAdapter.setWord(word);
                getWordDetails();

                mAdapter.setOnItemClickListener(new WordDetailAdapter.ClickListener()  {
                    @Override
                    public void onFavouriteClick(View v) {
                       addToFavourites();
                    }
                });

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_search_icon:
                String wordToSearch;
                if (mWordName.contains("\n")) {
                    wordToSearch = mWordName.substring(0, mWordName.indexOf("\n"));
                }else if (mWordName.contains("/")){
                    wordToSearch = mWordName.substring(0, mWordName.indexOf("/"));
                }else {
                    wordToSearch = mWordName;
                }
                Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, wordToSearch);
                startActivity(searchIntent);
                return true;
            case R.id.action_bar_share_icon:
                StringBuilder shareStringBuilder = new StringBuilder();
                shareStringBuilder.append(getResources().getString(R.string.share_word_headline)).append("\n").append("\n")
                  .append(mWordArtikel).append(" ")
                  .append(mWordName).append("\n")
                  .append(mWordExample);

                String wordToShare = shareStringBuilder.toString();

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, wordToShare);
                shareIntent.setType("text/plain");

                startActivity(Intent.createChooser(shareIntent,getResources().getText(R.string.share_with)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addToFavourites(){
        mFavouriteImageView = findViewById(R.id.add_to_favourites_image_view_card_view);
        int mWordFavourite = mWordFavouriteStatus ? 1 : 0;
        AddFavouriteViewModelFactory factory = new AddFavouriteViewModelFactory(mRepository,mWordFavourite, mWordID);
        mFavViewModel = ViewModelProviders.of(this,factory).get(AddFavouriteViewModel.class);
        if (!mWordFavouriteStatus) {
            mFavViewModel.setFavouriteStatus(1, mWordID);
            mFavouriteImageView = findViewById(R.id.add_to_favourites_image_view_card_view);
            mFavouriteImageView.setImageResource(R.drawable.ic_favorite_red);
            Snackbar.make(snackBar, R.string.added_to_favourites, Snackbar.LENGTH_LONG)
                    .setActionTextColor(ContextCompat.getColor(getApplicationContext()
                            ,R.color.wordRed)).show();
        } else {
            mFavViewModel.setFavouriteStatus(0, mWordID);
            mFavouriteImageView.setImageResource(R.drawable.ic_favorite_border_red);
            Snackbar.make(snackBar, R.string.removed_from_favourites, Snackbar.LENGTH_LONG)
                    .setActionTextColor(ContextCompat.getColor(getApplicationContext()
                            ,R.color.wordRed)).show();
        }
    }

    public void getWordDetails(){
        mCurrentWord = mAdapter.getWord();

        mWordID = mCurrentWord.getWordId();
        mWordArtikel = mCurrentWord.getWordArtikel();
        mWordName = mCurrentWord.getWordName();
        mWordExample = mCurrentWord.getWordExample();
        mWordFavouriteStatus = mCurrentWord.getWordFavourite();
    }
}

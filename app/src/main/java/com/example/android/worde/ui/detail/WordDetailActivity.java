package com.example.android.worde.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;

public class WordDetailActivity extends AppCompatActivity {
    public static final String SELECTED_WORD = "SELECTED_WORD";
    int selectedWord;
    WordRepository mRepository;
    int mWordID;
    int mWordFavouriteStatus;
    String mWordArtikel;
    String mWordName;
    String mWordExample;

    // FavouriteViewModel mFavViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        selectedWord = getIntent().getIntExtra(SELECTED_WORD, 0);

        WordDetailFragment fragment = new WordDetailFragment();
        WordDetailAdapter mAdapter = new WordDetailAdapter();
        fragment.setWordDetailAdapter(mAdapter);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_detail_container, fragment).commit();
        mRepository = new WordRepository(this.getApplication());
        DetailViewModelFactory factory = new DetailViewModelFactory(mRepository, selectedWord);
        DetailViewModel mViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);

        mViewModel.getWordById().observe(this, new Observer<Word>() {
            @Override
            public void onChanged(@Nullable Word word) {
                mAdapter.setWord(word);
              ///  mArtikel.setText(word.getWordArtikel());
              //  mWordName.setText(word.getWordName());
               // mExample.setText(word.getWordExample());
                mWordID = word.getWordId();
                mWordFavouriteStatus = word.getWordFavourite();
                mWordArtikel = word.getWordArtikel();
                mWordName = word.getWordName();
                mWordExample = word.getWordExample();
            }
        });


        /*
        Bundle bundle=new Bundle();
        bundle.putInt("name", selectedWord);
        fragment.setArguments(bundle);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_fav_icon:
              //  addToFavourites();
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

   /* public void addToFavourites(){
        mFavViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        //mFavViewModel.setFavouriteStatus;

        if (mWordFavouriteStatus == 0){
            mFavViewModel.setFavouriteStatus(1,mWordID);
           Toast.makeText(this, R.string.added_to_favourites, Toast.LENGTH_LONG).show();
        }else {
            mFavViewModel.setFavouriteStatus(0,mWordID);
            Toast.makeText(this, R.string.removed_from_favourites, Toast.LENGTH_LONG).show();

        }*/
        //noinspection unchecked
        /*mViewModel.setFavouriteStatus().observe(this, new Observer<Word>() {
            @Override
            public void onChanged(@Nullable Word word) {
                int favourite = word.getWordFavourite();
                int wordId = word.getWordId();
                int isFavourite = 1;
                int notFavourite = 0;
                if (favourite == 0){
                    //noinspection unchecked
                    mViewModel.setFavouriteStatus().setValue(wordId,isFavourite);
                 //   Toast.makeText(this, R.string.added_to_favourites, Toast.LENGTH_LONG).show();
                } else
                    //noinspection unchecked
                    mViewModel.setFavouriteStatus().setValue(wordId,notFavourite);
                   // Toast.makeText(this, R.string.removed_from_favourites, Toast.LENGTH_LONG).show();
            }
        });*/
    //}
}

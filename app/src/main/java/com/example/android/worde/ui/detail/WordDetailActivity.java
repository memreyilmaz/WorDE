package com.example.android.worde.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);


        int selectedWord = getIntent().getIntExtra(SELECTED_WORD, 0);

        WordDetailFragment fragment = new WordDetailFragment();
        WordDetailAdapter mAdapter = new WordDetailAdapter();
        fragment.setWordDetailAdapter(mAdapter);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_detail_container, fragment).commit();
        WordRepository mRepository = new WordRepository(this.getApplication());
        DetailViewModelFactory factory = new DetailViewModelFactory(mRepository, selectedWord);
        DetailViewModel mViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);

        mViewModel.getWordById().observe(this, new Observer<Word>() {
            @Override
            public void onChanged(@Nullable Word word) {
                mAdapter.setWord(word);
              ///  mArtikel.setText(word.getWordArtikel());
              //  mWordName.setText(word.getWordName());
               // mExample.setText(word.getWordExample());
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
             //   addToFavourites();
                return true;
            case R.id.action_bar_share_icon:
             //   shareWord();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

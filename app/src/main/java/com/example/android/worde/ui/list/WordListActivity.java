package com.example.android.worde.ui.list;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.worde.OnFragmentInteractionListener;
import com.example.android.worde.R;
import com.example.android.worde.ui.DrawerActivity;
import com.example.android.worde.ui.detail.WordDetailFragment;

import static com.example.android.worde.Config.A1;
import static com.example.android.worde.Config.A2;
import static com.example.android.worde.Config.B1;
import static com.example.android.worde.Config.FAV;
import static com.example.android.worde.Config.FRAGMENT_DETAIL;
import static com.example.android.worde.Config.FRAGMENT_LIST;
import static com.example.android.worde.Config.SELECTED_LEVEL;
import static com.example.android.worde.Config.SELECTED_WORD;

public class WordListActivity extends DrawerActivity implements OnFragmentInteractionListener {
   // public static final String SELECTED_LEVEL = "SELECTED_LEVEL";
    FrameLayout frameLayout;
    View snackBar;
    boolean mTabletLayout;
    WordListFragment wordListFragment;
    WordDetailFragment wordDetailFragment;
    String selectedLevel;
    String titleLevel;
    Bundle selectedLevelBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_word_list, frameLayout);
        selectedLevel = getIntent().getStringExtra(SELECTED_LEVEL);
        setTitle(assignTitle());

        snackBar = findViewById(R.id.app_bar_main);
        //TODO TEST IN TABLET !!!! NOT TESTED ON TABLET YET
        if (savedInstanceState == null) {
            if (findViewById(R.id.word_list_container_land) != null) {
                mTabletLayout = true;
                setWordListFragment();
                setWordDetailFragment();
            } else {
                mTabletLayout = false;
                setWordListFragment();
            }
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.findFragmentByTag(FRAGMENT_LIST) != null){
                wordListFragment = (WordListFragment) fragmentManager.findFragmentByTag(FRAGMENT_LIST);
                if (mTabletLayout) {
                    wordDetailFragment = (WordDetailFragment) fragmentManager.findFragmentByTag(FRAGMENT_DETAIL);
                }
            }else if (fragmentManager.findFragmentByTag(FRAGMENT_DETAIL) != null){//todo null gosteriyor her kosulda neden?
                //TODO DUZELDIKTEN SONRA NIGHT MODE ICIN DE KONTROL EDILMELI
                wordDetailFragment = (WordDetailFragment) fragmentManager.findFragmentByTag(FRAGMENT_DETAIL);
            }
        }
        Toolbar toolbar = findViewById(R.id.list_activity_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (mTabletLayout) {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.wordelogosmalltransparent);
            }
        }
    }
    public void setWordListFragment(){
        wordListFragment = new WordListFragment();
        selectedLevelBundle = new Bundle();
        selectedLevelBundle.putString("key", selectedLevel);
        wordListFragment.setArguments(selectedLevelBundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_list_container, wordListFragment, FRAGMENT_LIST).commit();
    }
    public void setWordDetailFragment(){
        wordDetailFragment = new WordDetailFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_detail_container, wordDetailFragment, FRAGMENT_DETAIL )
                .commit();
    }
    @Override
    public void changeFragment(int selectedWordId) {
        wordDetailFragment= new WordDetailFragment();
        Bundle args = new Bundle();
        args.putInt(SELECTED_WORD, selectedWordId);
        wordDetailFragment.setArguments(args);
        if (!mTabletLayout) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.word_list_container, wordDetailFragment,FRAGMENT_DETAIL)
                    .addToBackStack(null)
                    .commit();
        }else {
           getSupportFragmentManager().beginTransaction().detach(wordDetailFragment)
                   .attach(wordDetailFragment).commit();
        }

        /*   if (string.equals(WordListFragment.TAG)) {

                WordDetailFragment wordDetailFragment= new WordDetailFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
              //  wordDetailFragment.setArguments());
                //.putExtra(WordDetailActivity.SELECTED_WORD, selectedWordId));

                fragmentTransaction.replace(R.id.mainFrame, wordDetailFragment);
                fragmentTransaction.commit();
            }
            else if (string.equals("2")) {
                WordListFragment wordListFragment = new WordListFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainFrame, wordListFragment);
                fragmentTransaction.commit();
            }*/
    }
    /*@Override
    public void passData(int selectedWordId) {
        WordDetailFragment wordDetailFragment= new WordDetailFragment();

        Bundle args = new Bundle();
        args.putInt(wordDetailFragment.SELECTED_WORD, selectedWordId);
        wordDetailFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.word_list_container, wordDetailFragment )
                .commit();
    }*/
    public String assignTitle(){
        switch (selectedLevel){
            case A1:
                titleLevel = getString(R.string.a1);
                break;
            case A2:
                titleLevel = getString(R.string.a2);
                break;
            case B1:
                titleLevel = getString(R.string.b1);
                break;
            case FAV:
                titleLevel = getString(R.string.user_favourites);
                break;
        }
        return titleLevel;
    }
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
                super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!mTabletLayout) {
            switch (item.getItemId()) {
                case android.R.id.home:
                   onBackPressed();
                   return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

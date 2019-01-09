package com.example.android.worde.ui.list;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.worde.R;
import com.example.android.worde.WordItemSwipeListener;
import com.example.android.worde.WordListItemOnClickListener;
import com.example.android.worde.ui.AssignTitle;
import com.example.android.worde.ui.DrawerActivity;
import com.example.android.worde.ui.detail.WordDetailFragment;

import static com.example.android.worde.Config.FRAGMENT_DETAIL;
import static com.example.android.worde.Config.FRAGMENT_LIST;
import static com.example.android.worde.Config.SELECTED_LEVEL;
import static com.example.android.worde.Config.SELECTED_WORD;

public class WordListActivity extends DrawerActivity implements WordListItemOnClickListener, WordItemSwipeListener {
    FrameLayout frameLayout;
    View snackBar;
    boolean mTwoPaneLayout;
    WordListFragment wordListFragment;
    WordDetailFragment wordDetailFragment;
    String selectedLevel;
    Bundle selectedLevelBundle;
    FragmentManager fragmentManager = getSupportFragmentManager();
    AssignTitle assignTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_word_list, frameLayout);
        selectedLevel = getIntent().getStringExtra(SELECTED_LEVEL);
        assignTitle = new AssignTitle(getApplicationContext());
        setTitle(assignTitle.assignTitle(selectedLevel));
        snackBar = findViewById(R.id.app_bar_main);

        hideNightModeMenuItem();

        mTwoPaneLayout = getResources().getBoolean(R.bool.isTwoPane);

        if (savedInstanceState == null) {
            setWordListFragment();
            if (mTwoPaneLayout){
                setWordDetailFragment();
            }

        } else {
            if (fragmentManager.findFragmentByTag(FRAGMENT_LIST) != null){
                wordListFragment = (WordListFragment) fragmentManager.findFragmentByTag(FRAGMENT_LIST);
            }
            if (fragmentManager.findFragmentByTag(FRAGMENT_DETAIL) != null){
                wordDetailFragment = (WordDetailFragment) fragmentManager.findFragmentByTag(FRAGMENT_DETAIL);
                if (mTwoPaneLayout) {
                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction().remove(wordDetailFragment).commit();
                    fragmentManager.executePendingTransactions();
                    fragmentManager.beginTransaction().replace(R.id.detail_container, wordDetailFragment, FRAGMENT_DETAIL).commit();
                } else {
                   fragmentManager.beginTransaction().remove(wordDetailFragment).commit();
                }
            }else {
                if (mTwoPaneLayout) {
                    setWordDetailFragment();
                }
            }
        }
        setToolbar();
    }
    // Method for hiding Night Mode item in navigation drawer
    private void hideNightModeMenuItem() {
        NavigationView navigationView = findViewById(R.id.nav_view_base);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_night_mode).setVisible(false);
    }
    public void setToolbar(){
        Toolbar toolbar = findViewById(R.id.list_activity_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (mTwoPaneLayout) {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.wordelogosmalltransparent);
            }
        }
    }
    public void setWordListFragment(){
        wordListFragment = new WordListFragment();
        selectedLevelBundle = new Bundle();
        selectedLevelBundle.putString(SELECTED_LEVEL, selectedLevel);
        wordListFragment.setArguments(selectedLevelBundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_container, wordListFragment, FRAGMENT_LIST).commit();
    }
    public void setWordDetailFragment(){
        wordDetailFragment = new WordDetailFragment();
        selectedLevelBundle = new Bundle();
        selectedLevelBundle.putString(SELECTED_LEVEL, selectedLevel);
        wordDetailFragment.setArguments(selectedLevelBundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, wordDetailFragment, FRAGMENT_DETAIL)
                .commit();
    }
    @Override
    public void showWordDetails(int selectedWordId) {
        wordDetailFragment= new WordDetailFragment();
        Bundle selectedWordBundle = new Bundle();
        selectedWordBundle.putInt(SELECTED_WORD, selectedWordId);
        selectedWordBundle.putString(SELECTED_LEVEL, selectedLevel);
        wordDetailFragment.setArguments(selectedWordBundle);
        if (!mTwoPaneLayout) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, wordDetailFragment,FRAGMENT_DETAIL)
                    .addToBackStack(null)
                    .commit();
        }else {
           wordDetailFragment = (WordDetailFragment) fragmentManager.findFragmentByTag(FRAGMENT_DETAIL);
           if (wordDetailFragment != null){
               wordDetailFragment.setWordForTabletLayout(selectedWordId);
           }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!mTwoPaneLayout) {
            switch (item.getItemId()) {
                case android.R.id.home:
                   onBackPressed();
                   return true;
            }
        }
        return super.onOptionsItemSelected(item);
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
    public void scrollToNextItem() {
        wordListFragment.scrollToNext();
    }
    @Override
    public void scrollToPreviousItem() {
        wordListFragment.scrollToPrevious();
    }
}
package com.example.android.worde.ui.list;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.worde.OnFragmentInteractionListener;
import com.example.android.worde.R;
import com.example.android.worde.ui.DrawerActivity;
import com.example.android.worde.ui.detail.WordDetailFragment;

public class WordListActivity extends DrawerActivity implements OnFragmentInteractionListener {
    public static final String SELECTED_LEVEL = "SELECTED_LEVEL";
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
        if (findViewById(R.id.word_list_container_land) != null) {
            mTabletLayout = true;
            setWordListFragment();
            setWordDetailFragment();
        } else {
            mTabletLayout = false;
            setWordListFragment();
        }

        Toolbar toolbar = findViewById(R.id.list_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.wordelogosmalltransparent);

        /*WordListFragment fragment = new WordListFragment();
        fragment.setArguments(getIntent().getBundleExtra(MenuClick.SELECTED_LEVEL_BUNDLE));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_list_container, fragment).commit();*/
        }
    public void setWordListFragment(){
        wordListFragment = new WordListFragment();
        selectedLevelBundle = new Bundle();
        selectedLevelBundle.putString("key", selectedLevel);
        wordListFragment.setArguments(selectedLevelBundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_list_container, wordListFragment).commit();
    }
    public void setWordDetailFragment(){
        wordDetailFragment = new WordDetailFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.word_detail_container, wordDetailFragment )
                .commit();
    }
    @Override
    public void changeFragment(int selectedWordId) {
        wordDetailFragment= new WordDetailFragment();
        Bundle args = new Bundle();
        args.putInt(wordDetailFragment.SELECTED_WORD, selectedWordId);
        wordDetailFragment.setArguments(args);
        if (!mTabletLayout) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.word_list_container, wordDetailFragment )
                    .commit();
        }else {
           getSupportFragmentManager().beginTransaction().detach(wordDetailFragment).attach(wordDetailFragment).commit();
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
            case "a1":
                titleLevel = getString(R.string.a1);
                break;
            case "a2":
                titleLevel = getString(R.string.a2);
                break;
            case "b1":
                titleLevel = getString(R.string.b1);
                break;
            case "fav":
                titleLevel = getString(R.string.user_favourites);
                break;
        }
        return titleLevel;
    }
}

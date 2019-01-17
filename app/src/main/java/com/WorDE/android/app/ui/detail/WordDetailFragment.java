package com.WorDE.android.app.ui.detail;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.WorDE.android.app.R;
import com.WorDE.android.app.ui.helper.SnackbarShaper;
import com.WorDE.android.app.ui.helper.WordItemSwipeListener;
import com.WorDE.android.app.database.Word;
import com.WorDE.android.app.database.WordRepository;
import com.WorDE.android.app.ui.Analytics;
import com.WorDE.android.app.ui.helper.OnSwipeTouchListener;
import com.WorDE.android.app.ui.favourite.AddFavouriteViewModel;
import com.WorDE.android.app.ui.favourite.AddFavouriteViewModelFactory;

import static com.WorDE.android.app.Config.FAV;
import static com.WorDE.android.app.Config.SELECTED_LEVEL;
import static com.WorDE.android.app.Config.SELECTED_WORD;

public class WordDetailFragment extends Fragment {

    RecyclerView wordDetailCardView;
    WordDetailAdapter mAdapter;
    int selectedWord;
    WordRepository mRepository;
    boolean mWordFavouriteStatus;
    Word mCurrentWord;
    int mWordID;
    String mWordLevel;
    String mWordArtikel;
    String mWordName;
    String mWordExample;
    String selectedLevel;
    AddFavouriteViewModel mFavViewModel;
    ImageView mFavouriteImageView;
    View snackBarView;
    DetailViewModel mViewModel;
    Snackbar snackbar;
    int lastWordOfDbId;
    int firstWordOfDbId;
    int firstWordOfLevelId;
    int lastWordOfLevelId;
    int lastWordOfFavouritesId;
    Word firstWordOfLevel;
    Word firstWordOfFavouritesList;
    boolean twoPane;
    private WordItemSwipeListener mListener;
    boolean hasPreviousOrNext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twoPane = getResources().getBoolean(R.bool.isTwoPane);

        mRepository =  WordRepository.getInstance(getActivity().getApplication());

        Bundle args = getArguments();
        if (args != null) {
            selectedWord = args.getInt(SELECTED_WORD);
            selectedLevel = args.getString(SELECTED_LEVEL);
            if (selectedWord == 0) {
                if (selectedLevel.equals(FAV)){
                    getFirstWordOnFavouritesList();
                    selectedWord = firstWordOfFavouritesList.getWordId();
                } else {
                    getFirstWordOnSelectedLevel(selectedLevel);
                    selectedWord = firstWordOfLevel.getWordId();
                }
            }
        }
        DetailViewModelFactory factory = new DetailViewModelFactory(mRepository);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        mViewModel.setCurrentWordId(selectedWord);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);
        snackBarView = view.findViewById(R.id.word_detail_fragment);
        loadSelectedWord();
        LinearLayoutManager wordDetailLayoutManager = new LinearLayoutManager(getContext());
        wordDetailCardView = view.findViewById(R.id.word_detail_recyclerview);
        wordDetailCardView.setLayoutManager(wordDetailLayoutManager);
        mAdapter = new WordDetailAdapter(mCurrentWord);
        wordDetailCardView.setAdapter(mAdapter);
        wordDetailCardView.setHasFixedSize(true);
        if (!selectedLevel.equals(FAV)){
            getFirstWordOnSelectedLevel(selectedLevel);
            getLastWordOnSelectedLevel(selectedLevel);
            getFirstWordIdOnDb();
            getLastWordIdOnDb();
        }

        mAdapter.setOnItemClickListener(new WordDetailAdapter.ClickListener()  {
            @Override
            public void onFavouriteClick(View v) {
                    addToFavourites();
                }
            });

        wordDetailCardView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                    loadNextWord();

                if (twoPane && hasPreviousOrNext){
                    mListener.scrollToNextItem();
                    hasPreviousOrNext = false;
                }
            }
            @Override
            public void onSwipeRight() {
                    loadPreviousWord();
                if (twoPane && hasPreviousOrNext){
                    mListener.scrollToPreviousItem();
                    hasPreviousOrNext = false;
                }
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            selectedWord = savedInstanceState.getInt(SELECTED_WORD);
        }
    }
    //Method for passing chosen word id from list fragment to detail fragment and setting ui on two pane layout
    public void setWordForTabletLayout(int selectedWordIdOnListFragment){
        selectedWord = selectedWordIdOnListFragment;
        mViewModel.setCurrentWordId(selectedWord);
        loadSelectedWord();
    }
    //Method for loading selected word on list fragment and passsing it to adapter
    public void loadSelectedWord(){
        mViewModel.mSelectedWord.observe(this, new Observer<Word>() {
            @Override
            public void onChanged(@Nullable Word word) {
                mAdapter.setWord(word);
                getWordDetails(word);
            }
        });
    }
    //Method for getting current word details for using it on fragment
    public void getWordDetails(Word word){
        mCurrentWord = word;
        mWordID = mCurrentWord.getWordId();
        mWordLevel = mCurrentWord.getWordLevel();
        mWordArtikel = mCurrentWord.getWordArtikel();
        mWordName = mCurrentWord.getWordName();
        mWordExample = mCurrentWord.getWordExample();
        mWordFavouriteStatus = mCurrentWord.getWordFavourite();
    }
    //Method for adding or removing current word to/from favourites
    public void addToFavourites(){
        mFavouriteImageView = getView().findViewById(R.id.add_to_favourites_image_view_card_view);
        int mWordFavourite = mWordFavouriteStatus ? 1 : 0;
        AddFavouriteViewModelFactory factory = new AddFavouriteViewModelFactory(mRepository,
                mWordFavourite, mWordID);
        mFavViewModel = ViewModelProviders.of(this,factory).get(AddFavouriteViewModel.class);
        if (!mWordFavouriteStatus) {
            mFavViewModel.setFavouriteStatus(1, mWordID);
            mFavouriteImageView = getView().findViewById(R.id.add_to_favourites_image_view_card_view);
            mFavouriteImageView.setImageResource(R.drawable.ic_favorite);
            snackbar = Snackbar.make(snackBarView, R.string.added_to_favourites,
                    Snackbar.LENGTH_LONG);
            SnackbarShaper.configSnackbar(getContext(),snackbar); snackbar.show();
        } else {
            mFavViewModel.setFavouriteStatus(0, mWordID);
            mFavouriteImageView.setImageResource(R.drawable.ic_favorite_border);
            snackbar = Snackbar.make(snackBarView, R.string.removed_from_favourites,
                    Snackbar.LENGTH_LONG);
            SnackbarShaper.configSnackbar(getContext(),snackbar); snackbar.show();
            snackbar.show();
        }
    }
    //Method for loading getting first word of current level to use in loadPreviousWord method
    public void getFirstWordOnSelectedLevel(String level){
        selectedLevel = level;
        firstWordOfLevel = mRepository.getFirstWordOfLevel(level);
        firstWordOfLevelId = firstWordOfLevel.getWordId();
    }
    //Method for loading getting last word of current level to use in loadNextWord method
    public void getLastWordOnSelectedLevel(String level){
        selectedLevel = level;
        Word lastWordOfLevel = mRepository.getLastWordOfLevel(level);
        lastWordOfLevelId = lastWordOfLevel.getWordId();
    }
    //Method for loading getting first word of db to use in loadPreviousWord method
    public void getFirstWordIdOnDb(){
        Word firstWord = mRepository.getFirstWordOnDb();
        firstWordOfDbId = firstWord.getWordId();
    }
    //Method for loading getting last word of db to use in loadNextWord method
    public void getLastWordIdOnDb(){
       Word lastWord = mRepository.getLastWordOnDb();
       lastWordOfDbId = lastWord.getWordId();
    }
    //Method for loading getting first word of favourites list on db to use on two pane layout
    private void getFirstWordOnFavouritesList() {
        firstWordOfFavouritesList = mRepository.getFirstWordOfFavouritesList();
        lastWordOfFavouritesId = firstWordOfFavouritesList.getWordId();
    }
    //Main Method for loading previous word when cardview swiped left
    public void loadNextWord(){
        if (!selectedLevel.equals(FAV)){
            if (mWordID != lastWordOfDbId && mWordID != lastWordOfLevelId){
                selectedWord = (mWordID + 1);
                mViewModel.setCurrentWordId(selectedWord);
                loadSelectedWord();
                hasPreviousOrNext = true;
            }
        }else {
            getNextFavouriteWord(mWordID);
        }
    }
    //Main Method for loading previous word when cardview swiped right
    public void loadPreviousWord(){
        if (!selectedLevel.equals(FAV)){
            if (mWordID != firstWordOfDbId && mWordID != firstWordOfLevelId) {
                selectedWord = (mWordID - 1);
                mViewModel.setCurrentWordId(selectedWord);
                loadSelectedWord();
                hasPreviousOrNext = true;
            }
        }else {
            getPreviousFavouriteWord(mWordID);
        }
    }
    //Method for loading next favourite word when cardview swiped right
    public void getNextFavouriteWord(int id){
        Word nextFravouriteWord = mRepository.getNextWordOfFavouritesList(id);
        if (nextFravouriteWord != null) {
            mAdapter.setWord(nextFravouriteWord);
            getWordDetails(nextFravouriteWord);
            hasPreviousOrNext = true;
        }else {
            hasPreviousOrNext = false;
        }
    }
    //Method for loading previous favourite word when cardview swiped right
    public void getPreviousFavouriteWord(int id){
        Word previousFravouriteWord = mRepository.getPreviousWordOfFavouritesList(id);
        if (previousFravouriteWord != null){
            mAdapter.setWord(previousFravouriteWord);
            getWordDetails(previousFravouriteWord);
            hasPreviousOrNext = true;
        }else {
            hasPreviousOrNext = false;
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        inflater.inflate(R.menu.activity_detail_actions, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Intent for searching current word on Google
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

                Analytics.logSearchEvent(getContext());
                return true;
            //Intent for sharing current word
            case R.id.action_bar_share_icon:
                String wordArtikelForShare;
                if (mWordArtikel == null) {
                  wordArtikelForShare = "";
                } else {
                  StringBuilder artikelBuilder = new StringBuilder();
                  artikelBuilder.append(mWordArtikel).append(" ");
                  wordArtikelForShare = artikelBuilder.toString();
                }
                StringBuilder shareStringBuilder = new StringBuilder();
                shareStringBuilder.append(getResources().getString(R.string.share_word_headline)).append("\n").append("\n")
                        .append(wordArtikelForShare)
                        .append(mWordName).append("\n")
                        .append(mWordExample);

                String wordToShare = shareStringBuilder.toString();

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, wordToShare);
                shareIntent.setType("text/plain");

                startActivity(Intent.createChooser(shareIntent,getResources().getText(R.string.share_with)));

                Analytics.logShareEvent(getContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_WORD, selectedWord);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (WordItemSwipeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement WordItemSwipeListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
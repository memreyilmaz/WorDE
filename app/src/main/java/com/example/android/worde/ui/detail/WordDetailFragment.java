package com.example.android.worde.ui.detail;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import com.example.android.worde.R;
import com.example.android.worde.SnackbarShaper;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;
import com.example.android.worde.ui.OnSwipeTouchListener;
import com.example.android.worde.ui.favourite.AddFavouriteViewModel;
import com.example.android.worde.ui.favourite.AddFavouriteViewModelFactory;

import static com.example.android.worde.Config.SELECTED_WORD;

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

    String currentLevel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = new WordRepository(getActivity().getApplication());
        Bundle args = getArguments();
        if (args != null) {
            selectedWord = args.getInt(SELECTED_WORD);
         //   selectedLevel = args.getString(SELECTED_LEVEL);
        } else {
            selectedWord = mRepository.getFirstWordOfSelectedLevel("a2");
        }

        DetailViewModelFactory factory = new DetailViewModelFactory(mRepository);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        mViewModel.setCurrentWordId(selectedWord);
      //  getActivity().setTitle(mWordLevel.toUpperCase());  TODO

        setRetainInstance(true);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);
        snackBarView = view.findViewById(R.id.word_detail_fragment);
        loadSelectedWord();
        //mAdapter = new WordDetailAdapter(mCurrentWord);
        LinearLayoutManager wordDetailLayoutManager = new LinearLayoutManager(getContext());
        wordDetailCardView = view.findViewById(R.id.word_detail_recyclerview);
        wordDetailCardView.setLayoutManager(wordDetailLayoutManager);

        //wordDetailCardView.setHasFixedSize(false);
        //wordDetailCardView.setAdapter(mAdapter);
        wordDetailCardView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
               // currentLevel = mCurrentWord.getWordLevel();
                loadNextWord();
            }
            @Override
            public void onSwipeRight() {
             //   currentLevel = mCurrentWord.getWordLevel();
                loadPreviousWord();
            }

        });
     /*   String newLevel = mCurrentWord.getWordLevel();
        if (!currentLevel.equals(newLevel)){
            // getActivity().ass();
            AssignTitle at = new AssignTitle(getContext());
            String st = at.assignTitle(newLevel);
            getActivity().setTitle(st);
        }*/
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
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
                mAdapter = new WordDetailAdapter(word);
                wordDetailCardView.setAdapter(mAdapter);
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
    //Method for getting current word details for using it on fragment
    public void getWordDetails(){
        mCurrentWord = mAdapter.getWord();

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
    //Method for loading next word and setting ui when cardview swiped left
    public void loadNextWord(){
        //TODO find last id no.
        if (mWordID == 500000000){
            selectedWord = mWordID;
        }else {
            selectedWord = (mWordID + 1);
        }
        mViewModel.setCurrentWordId(selectedWord);
        loadSelectedWord();
    }
    //Method for loading previous word and setting ui when cardview swiped right
    public void loadPreviousWord(){
        if (mWordID == 1){
            selectedWord = mWordID;
        }else {
            selectedWord = (mWordID - 1);
        }
        mViewModel.setCurrentWordId(selectedWord);
        loadSelectedWord();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_detail_actions, menu);
        super.onCreateOptionsMenu(menu,inflater);
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
                return true;
            //Intent for sharing current word
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
}

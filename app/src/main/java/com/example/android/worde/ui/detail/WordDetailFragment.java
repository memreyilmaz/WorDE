package com.example.android.worde.ui.detail;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.worde.OnFragmentInteractionListener;
import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;
import com.example.android.worde.ui.favourite.AddFavouriteViewModel;
import com.example.android.worde.ui.favourite.AddFavouriteViewModelFactory;

public class WordDetailFragment extends Fragment {
    //private static final String TAG = WordDetailFragment.class.getSimpleName();
    RecyclerView wordDetailCardView;
    WordDetailAdapter mAdapter;
    public static final String SELECTED_WORD = "SELECTED_WORD";
    int selectedWord;
    int previousWord;
    int nextWord;
    WordRepository mRepository;
    int mWordID;
    boolean mWordFavouriteStatus;
    String mWordLevel;
    String mWordArtikel;
    String mWordName;
    String mWordExample;
    Word mCurrentWord;
    AddFavouriteViewModel mFavViewModel;
    ImageView mFavouriteImageView;
    FrameLayout frameLayout;
    View snackBar;
    DetailViewModel mViewModel;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            selectedWord = args.getInt(WordDetailFragment.SELECTED_WORD);
        } else {
            selectedWord = 1;
            }
        //selectedWord = getActivity().getIntent().getIntExtra(SELECTED_WORD, 0);
        mRepository = new WordRepository(getActivity().getApplication());
        DetailViewModelFactory factory = new DetailViewModelFactory(mRepository, selectedWord);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        setHasOptionsMenu(true);
      //  getActivity().setTitle(mWordLevel.toUpperCase());  TODO
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);
        snackBar = view.findViewById(R.id.word_detail_fragment);
        loadSelectedWord();
        //mAdapter = new WordDetailAdapter(mCurrentWord);
        LinearLayoutManager wordDetailLayoutManager = new LinearLayoutManager(getContext());
        wordDetailCardView = view.findViewById(R.id.word_detail_recyclerview);
        wordDetailCardView.setLayoutManager(wordDetailLayoutManager);
        //wordDetailCardView.setHasFixedSize(false);
        //wordDetailCardView.setAdapter(mAdapter);
        /*wordDetailCardView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {

                selectedWord = (mWordID - 1);
                loadSelectedWord();
               Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onSwipeRight() {
                selectedWord = (mWordID + 1);
                loadSelectedWord();
                Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;
    }
    public void loadSelectedWord(){
        mViewModel.getWordById().observe(this, new Observer<Word>() {
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
    public void getWordDetails(){
        mCurrentWord = mAdapter.getWord();

        mWordID = mCurrentWord.getWordId();
        mWordLevel = mCurrentWord.getWordLevel();
        mWordArtikel = mCurrentWord.getWordArtikel();
        mWordName = mCurrentWord.getWordName();
        mWordExample = mCurrentWord.getWordExample();
        mWordFavouriteStatus = mCurrentWord.getWordFavourite();
    }

    public void addToFavourites(){
        mFavouriteImageView = getView().findViewById(R.id.add_to_favourites_image_view_card_view);
        int mWordFavourite = mWordFavouriteStatus ? 1 : 0;
        AddFavouriteViewModelFactory factory = new AddFavouriteViewModelFactory(mRepository,mWordFavourite, mWordID);
        mFavViewModel = ViewModelProviders.of(this,factory).get(AddFavouriteViewModel.class);
        if (!mWordFavouriteStatus) {
            mFavViewModel.setFavouriteStatus(1, mWordID);
            mFavouriteImageView = getView().findViewById(R.id.add_to_favourites_image_view_card_view);
            mFavouriteImageView.setImageResource(R.drawable.ic_favorite);
            Snackbar.make(snackBar, R.string.added_to_favourites, Snackbar.LENGTH_LONG)
                    .setActionTextColor(ContextCompat.getColor(getContext()
                            ,R.color.wordRed)).show();
        } else {
            mFavViewModel.setFavouriteStatus(0, mWordID);
            mFavouriteImageView.setImageResource(R.drawable.ic_favorite_border);
            Snackbar.make(snackBar, R.string.removed_from_favourites, Snackbar.LENGTH_LONG)
                    .setActionTextColor(ContextCompat.getColor(getContext()
                            ,R.color.wordRed)).show();
        }
    }
    public void loadNextWord(){
        //TODO find last id no.
        if (mWordID == 500000000){
            selectedWord = mWordID;
        }else {
            selectedWord = (mWordID + 1);
        }


        Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();

    }

    public void loadPreviousWord(){
        if (mWordID == 1){
            selectedWord = mWordID;
        }else {
            selectedWord = (mWordID - 1);
        }

        Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_detail_actions, menu);
        super.onCreateOptionsMenu(menu,inflater);
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


   /* //TODO BACK BUTTON IMPLEMENT
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/
}

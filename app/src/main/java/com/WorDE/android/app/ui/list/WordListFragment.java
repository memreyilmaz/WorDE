package com.WorDE.android.app.ui.list;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.WorDE.android.app.R;
import com.WorDE.android.app.database.Word;
import com.WorDE.android.app.database.WordRepository;
import com.WorDE.android.app.ui.favourite.AddFavouriteViewModel;
import com.WorDE.android.app.ui.favourite.AddFavouriteViewModelFactory;
import com.WorDE.android.app.ui.favourite.LoadFavouritesViewModel;
import com.WorDE.android.app.ui.helper.SnackbarShaper;
import com.WorDE.android.app.ui.helper.WordListItemOnClickListener;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.List;

import static com.WorDE.android.app.Config.ADDED;
import static com.WorDE.android.app.Config.ADDED_BACK;
import static com.WorDE.android.app.Config.FAV;
import static com.WorDE.android.app.Config.RECYCLERVIEW_POSITION;
import static com.WorDE.android.app.Config.REMOVED;
import static com.WorDE.android.app.Config.SELECTED_LEVEL;

public class WordListFragment extends Fragment {
    RecyclerView wordListRecyclerView;
    WordListAdapter mAdapter;
    String selectedLevel;
    AddFavouriteViewModel mFavViewModel;
    LoadFavouritesViewModel mViewModel;
    boolean mWordFavouriteStatus;
    WordRepository mRepository;
    int mWordID;
    Word mSelectedWord;
    View snackBarView;
    private WordListItemOnClickListener mListener;
    LevelViewModel mLevelViewModel;
    LinearLayoutManager wordListLayoutManager;
    TextView mEmptyListTextView;

    public WordListFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle levelbundle = getArguments();
        if (levelbundle != null){
            selectedLevel = levelbundle.getString(SELECTED_LEVEL);
        }
        mRepository =  WordRepository.getInstance(getActivity().getApplication());

        WordLevelViewModelFactory factory = new WordLevelViewModelFactory(mRepository, selectedLevel);
        mLevelViewModel = ViewModelProviders.of(this, factory).get(LevelViewModel.class);
        mAdapter = new WordListAdapter();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        snackBarView = view.findViewById(R.id.word_list_coordinator_layout);
        mEmptyListTextView = view.findViewById(R.id.word_list_fragment_empty_view);
        if (selectedLevel.equals(FAV)){
            setFavouriteWordList();
        } else {
            setLevelWordList();
        }
        wordListLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        wordListRecyclerView = view.findViewById(R.id.word_list_recyclerview);
        wordListRecyclerView.setLayoutManager(wordListLayoutManager);
        wordListRecyclerView.setAdapter(mAdapter);

        wordListRecyclerView.setHasFixedSize(true);
        FastScroller fastScroller = (FastScroller) view.findViewById(R.id.fastscroll);
        fastScroller.setRecyclerView(wordListRecyclerView);

        return view;
    }
    public void setLevelWordList(){
        mLevelViewModel.getWordsByLevels().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                mAdapter.setWords(words);
                setAdapterClickListener();
            }
        });
    }
    public void addToFavourites(int position){
        mWordID = getSelectedWordID(position);
        mWordFavouriteStatus = getSelectedWordFavouriteStatus(position);
        int mWordFavourite = mWordFavouriteStatus ? 1 : 0;
        AddFavouriteViewModelFactory factory =
                new AddFavouriteViewModelFactory(mRepository, mWordFavourite, mWordID);
        mFavViewModel = ViewModelProviders.of(this,factory)
                .get(AddFavouriteViewModel.class);
        if (!mWordFavouriteStatus) {
            mFavViewModel.setFavouriteStatus(1, mWordID);
            setSnackBar(ADDED);
        } else {
            mFavViewModel.setFavouriteStatus(0, mWordID);
            if (selectedLevel.equals(FAV)){
                setSnackBar(ADDED_BACK);
            } else {
                setSnackBar(REMOVED);
            }
        }
    }
    public void setFavouriteWordList(){
        mViewModel = ViewModelProviders.of(this).get(LoadFavouritesViewModel.class);
        mViewModel.getFavouriteWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                mAdapter.setWords(words);
                setAdapterClickListener();
                if (words.isEmpty()){
                    mEmptyListTextView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyListTextView.setVisibility(View.GONE);
                }
            }
        });
    }
    public void setAdapterClickListener(){
        mAdapter.setOnItemClickListener(new WordListAdapter.ClickListener()  {
            @Override
            public void onItemClick(View v, int position) {
                int wordId = getSelectedWordID(position);
                mListener.showWordDetails(wordId);
            }
            @Override
            public void onFavouriteClick(View v, int position) {
                addToFavourites(position);
            }
        });
    }
    public int getSelectedWordID(int position){
        mSelectedWord = mAdapter.getWordAtPosition(position);
        return mSelectedWord.getWordId();
    }
    public boolean getSelectedWordFavouriteStatus(int position){
        mSelectedWord = mAdapter.getWordAtPosition(position);
        return mSelectedWord.getWordFavourite();
    }
    public void setSnackBar(String condition){
        Snackbar snackbar;
        switch (condition){
            case ADDED:
                snackbar = Snackbar.make(snackBarView, R.string.added_to_favourites,
                        Snackbar.LENGTH_LONG);
                SnackbarShaper.configSnackbar(getContext(),snackbar); snackbar.show();
                break;
            case REMOVED:
                snackbar = Snackbar.make(snackBarView, R.string.removed_from_favourites,
                        Snackbar.LENGTH_LONG);
                SnackbarShaper.configSnackbar(getContext(),snackbar); snackbar.show();
                        snackbar.show();
                break;
            case ADDED_BACK:
               snackbar = Snackbar.make(snackBarView, R.string.removed_from_favourites,
                        Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mFavViewModel.setFavouriteStatus(1, mWordID);
                                Snackbar snackbar = Snackbar.make(view,
                                        R.string.added_to_favourites_again,
                                        Snackbar.LENGTH_LONG);
                                        SnackbarShaper.configSnackbar(getContext(),snackbar);
                                        snackbar.show();
                            }
                        });
                        SnackbarShaper.configSnackbar(getContext(),snackbar);
                        snackbar.show();
                break;
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (WordListItemOnClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement WordListItemOnClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void scrollToPrevious() {
        if (hasPreviousItem()){
            scrollToPreviousItem();
        }
    }
    public void scrollToNext() {
        if (hasNextItem()){
            scrollToNextItem();
        }
    }
    public boolean hasPreviousItem() {
        return getFirstItemForPrevious() > 0;
    }

    public boolean hasNextItem() {
        return wordListRecyclerView.getAdapter() != null && getLastItemForNext() < (wordListRecyclerView.getAdapter().getItemCount()- 1);
    }

    public void scrollToPreviousItem() {
        int position = getFirstItemForPrevious();
        if (position > 0)
            setCurrentItem(position -1);
    }

    public void scrollToNextItem() {
        RecyclerView.Adapter adapter = wordListRecyclerView.getAdapter();
        if (adapter == null)
            return;

        int position = getLastItemForNext();
        int count = adapter.getItemCount();
        if (position < (count -1))
            setCurrentItem(position + 1);
    }

    private int getFirstItemForPrevious(){
        return ((LinearLayoutManager)wordListRecyclerView.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition();
    }

    private int getLastItemForNext(){
        return ((LinearLayoutManager)wordListRecyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
    }

    private void setCurrentItem(int position){
        wordListRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_LEVEL, selectedLevel);
        outState.putParcelable(RECYCLERVIEW_POSITION, wordListRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            selectedLevel = savedInstanceState.getString(SELECTED_LEVEL);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLERVIEW_POSITION);
            wordListRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }
}
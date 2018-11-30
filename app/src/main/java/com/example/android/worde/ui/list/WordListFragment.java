package com.example.android.worde.ui.list;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.worde.OnFragmentInteractionListener;
import com.example.android.worde.R;
import com.example.android.worde.SnackbarShaper;
import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;
import com.example.android.worde.ui.favourite.AddFavouriteViewModel;
import com.example.android.worde.ui.favourite.AddFavouriteViewModelFactory;
import com.example.android.worde.ui.favourite.LoadFavouritesViewModel;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.List;

public class WordListFragment extends Fragment {
    // getActivity().getClass().getSimpleName(); //TODO
    RecyclerView wordListRecyclerView;
    WordListAdapter mAdapter;
    private static final String TAG = WordListFragment.class.getSimpleName();
    public static final String SELECTED_LEVEL = "SELECTED_LEVEL";
    //int selectedWordId;
    String selectedLevel;
    AddFavouriteViewModel mFavViewModel;
    LoadFavouritesViewModel mViewModel;
    boolean mWordFavouriteStatus;
    WordRepository mRepository;
    int mWordID;
    View snackBarView;
    private OnFragmentInteractionListener mListener;
    //DataPassListener mCallback;
    List<Word> words;
    LevelViewModel mLevelViewModel;
    /*public interface DataPassListener{
        public void passData(int selectedWordId);
    }*/

    public WordListFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        selectedLevel = bundle.getString("key");
        mRepository = new WordRepository(getActivity().getApplication());
        WordLevelViewModelFactory factory = new WordLevelViewModelFactory(mRepository, selectedLevel);
        mLevelViewModel = ViewModelProviders.of(this, factory).get(LevelViewModel.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        snackBarView = view.findViewById(R.id.word_list_coordinator_layout);
        if (selectedLevel.equals("fav")){
            setFavouriteWordList();
        } else {
            setLevelWordList();
        }
        LinearLayoutManager wordListLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        wordListRecyclerView = view.findViewById(R.id.word_list_recyclerview);
        wordListRecyclerView.setLayoutManager(wordListLayoutManager);
//        wordListRecyclerView.setHasFixedSize(false);

        FastScroller fastScroller = (FastScroller) view.findViewById(R.id.fastscroll);
        fastScroller.setRecyclerView(wordListRecyclerView);

        return view;
    }
    public void setLevelWordList(){
        mLevelViewModel.getWordsByLevels().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                mAdapter = new WordListAdapter();
                wordListRecyclerView.setAdapter(mAdapter);
                mAdapter.setWords(words);
                setAdapterClickListener();
            }
        });
    }
    public void addToFavourites(int position){
        Word word = mAdapter.getWordAtPosition(position);
        mWordID = word.getWordId();
        mWordFavouriteStatus = word.getWordFavourite();
        int mWordFavourite = mWordFavouriteStatus ? 1 : 0;
        AddFavouriteViewModelFactory factory =
                new AddFavouriteViewModelFactory(mRepository,mWordFavourite, mWordID);
        mFavViewModel = ViewModelProviders.of(this,factory)
                .get(AddFavouriteViewModel.class);
        if (!mWordFavouriteStatus) {
            mFavViewModel.setFavouriteStatus(1, mWordID);
            setSnackBar("added");
        } else {
            mFavViewModel.setFavouriteStatus(0, mWordID);
            if (selectedLevel.equals("fav")){
                setSnackBar("addedback");
            } else {
                setSnackBar("removed");
            }
        }
    }
    public void setFavouriteWordList(){
        mViewModel = ViewModelProviders.of(this).get(LoadFavouritesViewModel.class);
        mViewModel.getFavouriteWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                mAdapter = new WordListAdapter();
                wordListRecyclerView.setAdapter(mAdapter);
                mAdapter.setWords(words);
                setAdapterClickListener();
            }
        });
    }
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
    }
    /*@Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mCallback = (DataPassListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }*/
    public void setAdapterClickListener(){
        mAdapter.setOnItemClickListener(new WordListAdapter.ClickListener()  {
            @Override
            public void onItemClick(View v, int position) {
                Word word = mAdapter.getWordAtPosition(position);
                int  selectedWordId = word.getWordId();
                mListener.changeFragment(selectedWordId);
            }
            @Override
            public void onFavouriteClick(View v, int position) {
                addToFavourites(position);
            }
        });
    }
    public void setSnackBar(String condition){
        Snackbar snackbar;
        switch (condition){
            case "added":
                snackbar = Snackbar.make(snackBarView, R.string.added_to_favourites, Snackbar.LENGTH_LONG);
                SnackbarShaper.configSnackbar(getContext(),snackbar); snackbar.show();
                break;
            case "removed":
                snackbar = Snackbar.make(snackBarView, R.string.removed_from_favourites,Snackbar.LENGTH_LONG);
                SnackbarShaper.configSnackbar(getContext(),snackbar); snackbar.show();
                        snackbar.show();
                break;
            case "addedback":
               snackbar = Snackbar.make(snackBarView, R.string.removed_from_favourites, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mFavViewModel.setFavouriteStatus(1, mWordID);
                                Snackbar snackbar = Snackbar.make(view, R.string.added_to_favourites_again,
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
}

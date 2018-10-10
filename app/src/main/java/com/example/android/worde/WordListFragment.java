package com.example.android.worde;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordListFragment extends Fragment {
    RecyclerView wordListRecyclerView;
    WordListAdapter mAdapter;

    public WordListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager wordListLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        wordListRecyclerView = view.findViewById(R.id.word_list_recyclerview);
        mAdapter = new WordListAdapter(getContext());
        wordListRecyclerView.setLayoutManager(wordListLayoutManager);
        wordListRecyclerView.setHasFixedSize(true);
        wordListRecyclerView.setAdapter(mAdapter);
        //ViewModelFactory viewModelFactory = ViewModelFactory.createFactory(getActivity());

        WordViewModel mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        mViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setWords(words);
            }
        });

        return view;
    }

}

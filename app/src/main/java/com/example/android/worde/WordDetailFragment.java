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

public class WordDetailFragment extends Fragment {
    RecyclerView wordDetailCardView;
    WordDetailAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager wordListLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        wordDetailCardView = view.findViewById(R.id.word_detail_recyclerview);
        mAdapter = new WordDetailAdapter(getContext());
        wordDetailCardView.setLayoutManager(wordListLayoutManager);
        wordDetailCardView.setHasFixedSize(true);
        wordDetailCardView.setAdapter(mAdapter);

        WordViewModel mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        mViewModel.getWordById().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setWordDetails(words);
            }
        });

        return view;
    }
}

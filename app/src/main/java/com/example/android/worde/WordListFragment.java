package com.example.android.worde;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordListFragment extends Fragment {
    @BindView(R.id.word_list_recyclerview) RecyclerView wordListRecyclerView;
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
        wordListRecyclerView.setLayoutManager(wordListLayoutManager);
        wordListRecyclerView.setHasFixedSize(true);
        wordListRecyclerView.setAdapter(mAdapter);

        return view;
    }

}

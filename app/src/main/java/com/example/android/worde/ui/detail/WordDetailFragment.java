package com.example.android.worde.ui.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.worde.R;

public class WordDetailFragment extends Fragment {
    RecyclerView wordDetailCardView;
    WordDetailAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);
        LinearLayoutManager wordDetailLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        wordDetailCardView = view.findViewById(R.id.word_detail_recyclerview);
        wordDetailCardView.setLayoutManager(wordDetailLayoutManager);
        wordDetailCardView.setHasFixedSize(true);
        wordDetailCardView.setAdapter(mAdapter);

        return view;
    }
    public void setWordDetailAdapter(WordDetailAdapter adapter){
        mAdapter = adapter;
    }
}

package com.example.android.worde.ui.list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.android.worde.R;

public class WordListFragment extends Fragment {
    RecyclerView wordListRecyclerView;
    WordListAdapter mAdapter;

    public WordListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        LinearLayoutManager wordListLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        wordListRecyclerView = view.findViewById(R.id.word_list_recyclerview);
        wordListRecyclerView.setLayoutManager(wordListLayoutManager);
        wordListRecyclerView.setHasFixedSize(true);
        wordListRecyclerView.setAdapter(mAdapter);

        return view;
    }
    public void setWordListAdapter(WordListAdapter adapter){
        mAdapter = adapter;
    }
}

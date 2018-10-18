package com.example.android.worde.ui.detail;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;

import java.util.List;

public class WordDetailAdapter extends RecyclerView.Adapter<WordDetailAdapter.WordDetailViewHolder> {
    private List<Word> mWords;
    private final LayoutInflater mInflater;

    public WordDetailAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_view_item, parent, false);
        return new WordDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordDetailViewHolder holder, int position) {
        Word word = mWords.get(position);
        holder.bindTo(word);
    }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    public void setWordDetails(List<Word> words){
        this.mWords = words;
        notifyDataSetChanged();
    }

    public class WordDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView mArtikel;
        private TextView mWordName;
        private TextView mExample;
        private Word mWord;

        WordDetailViewHolder(View itemView) {
            super(itemView);
            mArtikel = itemView.findViewById(R.id.word_detail_artikel_text_view);
            mWordName = itemView.findViewById(R.id.word_detail_word_text_view);
            mExample = itemView.findViewById(R.id.word_detail_example_text_view);

        }

        public Word getWord() {
            return mWord;
        }

        void bindTo(Word word) {
            mWord = word;
            mArtikel.setText(word.getWordArtikel());
            mWordName.setText(word.getWordName());
            mExample.setText(word.getWordExample());
        }
    }

}
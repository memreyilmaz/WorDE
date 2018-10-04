package com.example.android.worde;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
        private List<Word> mWords;
        public WordListAdapter(List<Word> words) {
            this.mWords = words;
        }

        @NonNull
        @Override
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item, parent, false);
            return new WordViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
            Word word = mWords.get(position);
            holder.bindTo(word);
        }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

            private TextView mArtikel;
            private TextView mWordName;
            private TextView mExample;
            private Word mWord;

            WordViewHolder(View itemView) {
                super(itemView);
                mArtikel = itemView.findViewById(R.id.artikel_text_view);
                mWordName = itemView.findViewById(R.id.word_text_view);
                mExample = itemView.findViewById(R.id.example_text_view);

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



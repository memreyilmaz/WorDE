package com.example.android.worde.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;

public class WordDetailAdapter extends RecyclerView.Adapter<WordDetailAdapter.WordDetailViewHolder> {
    private Word mWord;
    private static ClickListener clickListener;

    public WordDetailAdapter(Word word) {
        mWord = word; }

    @NonNull
    @Override
    public WordDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordDetailViewHolder(new WordDetailCard(parent.getContext()));
    }
    @Override
    public void onBindViewHolder(@NonNull WordDetailViewHolder holder, int position) {
        Word word = mWord;
        holder.bindTo(word);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class WordDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView mArtikel;
        private TextView mWordName;
        private TextView mExample;
        private ImageView mAddFavourite;
        private Word mWord;

        public WordDetailViewHolder(View itemView) {
            super(itemView);
            mArtikel =itemView.findViewById(R.id.word_detail_artikel_text_view);
            mWordName = itemView.findViewById(R.id.word_detail_word_text_view);
            mExample = itemView.findViewById(R.id.word_detail_example_text_view);
            mAddFavourite = itemView.findViewById(R.id.add_to_favourites_image_view_card_view);
            mAddFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onFavouriteClick(view);
                }
            });
        }
        public Word getWord() {
            return mWord;
        }
        void bindTo(Word word) {
            mWord = word;
            mArtikel.setText(word.getWordArtikel());
            mWordName.setText(word.getWordName());
            mExample.setText(word.getWordExample());
            if (!word.getWordFavourite()){
                mAddFavourite.setImageResource(R.drawable.ic_favorite_border_red);
            }else{
                mAddFavourite.setImageResource(R.drawable.ic_favorite_red);
            }
        }
    }
    public void setWord(Word word){
        this.mWord = word;
        notifyDataSetChanged();
    }

    public Word getWord() {
        return mWord;
    }

    public void setOnItemClickListener(WordDetailAdapter.ClickListener clickListener) {
        WordDetailAdapter.clickListener = clickListener;
    }
    public interface ClickListener {
        void onFavouriteClick(View v);
    }
}
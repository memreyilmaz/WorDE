package com.WorDE.android.app.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.WorDE.android.app.R;
import com.WorDE.android.app.database.Word;


public class WordDetailAdapter extends RecyclerView.Adapter<WordDetailAdapter.WordDetailViewHolder> {
    private Word mWord;
    private static ClickListener clickListener;
    public WordDetailAdapter(Word word) {
        mWord = word;
    }

    @NonNull
    @Override
    public WordDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_detail_card_view, parent, false);

        return new WordDetailViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull WordDetailViewHolder holder, int position) {
        Word word = mWord;
        if (word.getWordArtikel() == null){
            holder.mArtikel.setVisibility(View.GONE);
        } else {
            holder.mArtikel.setVisibility(View.VISIBLE);
            holder.mArtikel.setText(word.getWordArtikel());
        }
        holder.mWordName.setText(word.getWordName());
        holder.mExample.setText(word.getWordExample());
        if (!word.getWordFavourite()){
            holder.mAddFavourite.setImageResource(R.drawable.ic_favorite_border);
        }else{
            holder.mAddFavourite.setImageResource(R.drawable.ic_favorite);
        }
    }
    @Override
    public int getItemCount() {
        if (mWord != null){
            return 1;
        }
        else return 0;
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
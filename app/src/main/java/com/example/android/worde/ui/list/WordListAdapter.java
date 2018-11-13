package com.example.android.worde.ui.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.worde.R;
import com.example.android.worde.database.Word;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> implements SectionTitleProvider {
       private List<Word> mWords;
       private static ClickListener clickListener;
    private ImageView mAddFavourite;

    public WordListAdapter() {
    }


    @NonNull
    @Override
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
            return new WordViewHolder(itemView);
    }
    @Override
        public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = mWords.get(position);
        if(word != null) {

            holder.bindTo(word);
        }
    }
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
        }
    public void setWords(List<Word> words){
        this.mWords = words;
        notifyDataSetChanged();
    }
    public Word getWordAtPosition(int position) {
        return mWords.get(position);
    }
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
                mAddFavourite = itemView.findViewById(R.id.add_to_favourites_image_view);
                mAddFavourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onFavouriteClick(view, getAdapterPosition());
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onItemClick(view, getAdapterPosition());
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
    public void setOnItemClickListener(ClickListener clickListener) {
        WordListAdapter.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(View v, int position);
        void onFavouriteClick(View v, int position);
    }
    private String getWordPosition(int position) {
        return String.valueOf(mWords.get(position));
    }
    @Override
    public String getSectionTitle(int position) {
        return getWordPosition(position).substring(0, 1);
    }
}



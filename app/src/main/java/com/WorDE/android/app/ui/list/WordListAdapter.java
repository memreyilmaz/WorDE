package com.WorDE.android.app.ui.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.WorDE.android.app.R;
import com.WorDE.android.app.database.Word;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>
        implements SectionTitleProvider {

    private List<Word> mWords;
    private static ClickListener clickListener;
    public WordListAdapter() {
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

        if (word.getWordArtikel() != null){
            holder.mArtikel.setText(word.getWordArtikel());
            holder.mArtikel.setVisibility(View.VISIBLE);
            holder.mArtikel.setGravity(Gravity.BOTTOM);
            holder.mWordName.setGravity(Gravity.TOP);
        }else {
            holder.mArtikel.setVisibility(View.GONE);
            holder.mWordName.setGravity(Gravity.CENTER_VERTICAL);
        }
        holder.mWordName.setText(word.getWordName());
        if (!word.getWordFavourite()){
            holder.mAddFavourite.setImageResource(R.drawable.ic_favorite_border);
        }else{
            holder.mAddFavourite.setImageResource(R.drawable.ic_favorite);
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
            private ImageView mAddFavourite;

        public WordViewHolder(View itemView) {
                super(itemView);
                mArtikel = itemView.findViewById(R.id.artikel_text_view);
                mWordName = itemView.findViewById(R.id.word_text_view);
                mAddFavourite = itemView.findViewById(R.id.add_to_favourites_image_view);
                mAddFavourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int favouritedWordPosition = getAdapterPosition();
                        clickListener.onFavouriteClick(view, favouritedWordPosition);
                        notifyItemChanged(favouritedWordPosition);
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onItemClick(view, getAdapterPosition());
                    }
                });
            }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        WordListAdapter.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(View v, int position);
        void onFavouriteClick(View v, int position);
    }
    //Helper Method For Side Fast Scroll Implementation
    private String getWordPosition(int position) {
        return String.valueOf(mWords.get(position).getWordName());
    }
    //Method For Side Fast Scroll Bubble Letter Implementation
    @Override
    public String getSectionTitle(int position) {
        return getWordPosition(position).substring(0, 1).toUpperCase();
    }
}
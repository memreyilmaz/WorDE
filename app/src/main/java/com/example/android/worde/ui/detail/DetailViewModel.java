package com.example.android.worde.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;


public class DetailViewModel extends ViewModel {

    private LiveData<Word> mSelectedWord;
    private final int mId;
    private final WordRepository mRepository;

    public DetailViewModel(WordRepository repository, int id) {
        mRepository = repository;
        mId = id;
        mSelectedWord = mRepository.getWordById(mId);
    }
    public LiveData<Word> getWordById() {
        return mSelectedWord;
    }
}
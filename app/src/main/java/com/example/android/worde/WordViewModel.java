package com.example.android.worde;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private LiveData<List<Word>> mAllWords;
    private LiveData<Word> mSelectedWord;
    private int mWordId;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
        mSelectedWord = mRepository.getWordById(mWordId);
    }

    /*public WordViewModel(WordRepository repository) {
        //super(repository);
        mRepository = repository;
    }*/

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public LiveData<Word> getWordById() {
        return mSelectedWord;
    }
}
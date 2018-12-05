package com.example.android.worde.ui.detail;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;

public class DetailViewModel extends ViewModel {
    private final WordRepository mRepository;
    public LiveData<Word> mSelectedWord;
    private MutableLiveData<Integer> wordidlivedata;

    public DetailViewModel(WordRepository repository) {
        wordidlivedata = new MutableLiveData<Integer>();
        mRepository = repository;

        mSelectedWord=Transformations.switchMap(wordidlivedata, new Function<Integer, LiveData<Word>>() {
            @Override
            public LiveData<Word> apply(Integer mId) {
                return mRepository.getWordById(mId);
            }
        });
    }
    public void setCurrentWordId(int wordId){
        wordidlivedata.postValue(wordId);
    }
}
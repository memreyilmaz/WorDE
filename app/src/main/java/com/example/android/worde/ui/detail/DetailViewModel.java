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
    private MutableLiveData<Integer> currentWordIdLiveData;

    public DetailViewModel(WordRepository repository) {

        currentWordIdLiveData = new MutableLiveData<Integer>();
        mRepository = repository;

        mSelectedWord=Transformations.switchMap(currentWordIdLiveData, new Function<Integer, LiveData<Word>>() {
            @Override
            public LiveData<Word> apply(Integer mId) {
                return mRepository.getWordById(mId);
            }
        });
    }
    public void setCurrentWordId(int wordId){
        currentWordIdLiveData.postValue(wordId);
    }
}
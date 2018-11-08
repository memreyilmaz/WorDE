package com.example.android.worde.ui.favourite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;

import java.util.List;

public class LoadFavouritesViewModel extends AndroidViewModel {

    private final LiveData<List<Word>> mFavouriteWords;
    private final WordRepository mRepository;

    public LoadFavouritesViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mFavouriteWords = mRepository.getFavouriteWords();
    }

    public LiveData<List<Word>> getFavouriteWords() {
        return mFavouriteWords;
    }

}
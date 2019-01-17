package com.WorDE.android.app.ui.favourite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.WorDE.android.app.database.Word;
import com.WorDE.android.app.database.WordRepository;

import java.util.List;

public class LoadFavouritesViewModel extends AndroidViewModel {

    private final LiveData<List<Word>> mFavouriteWords;
    private final WordRepository mRepository;

    public LoadFavouritesViewModel(Application application) {
        super(application);
        mRepository = WordRepository.getInstance(getApplication());
        mFavouriteWords = mRepository.getFavouriteWords();
    }
    public LiveData<List<Word>> getFavouriteWords() {
        return mFavouriteWords;
    }
}
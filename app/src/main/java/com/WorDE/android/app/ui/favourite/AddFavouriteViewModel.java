package com.WorDE.android.app.ui.favourite;

import android.arch.lifecycle.ViewModel;

import com.WorDE.android.app.database.WordRepository;

public class AddFavouriteViewModel extends ViewModel {

    private final int mFavourite;
    private final int mId;
    private final WordRepository mRepository;

    public AddFavouriteViewModel(WordRepository repository, int favourite, int id) {
        mRepository = repository;
        mFavourite = favourite;
        mId = id;
        mRepository.setFavouriteStatus(favourite, id);
    }
    public void setFavouriteStatus(int favourite, int id) {
        mRepository.setFavouriteStatus(favourite, id);
    }
}
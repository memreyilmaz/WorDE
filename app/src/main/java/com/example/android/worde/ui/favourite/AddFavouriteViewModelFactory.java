package com.example.android.worde.ui.favourite;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.worde.database.WordRepository;

public class AddFavouriteViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    private final WordRepository mRepository;
    private final int mId;
    private final int mFavourite;


    public AddFavouriteViewModelFactory(WordRepository repository, int favourite, int id) {
        this.mRepository = repository;
        this.mFavourite = favourite;
        this.mId = id;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddFavouriteViewModel(mRepository, mFavourite, mId);
    }
}
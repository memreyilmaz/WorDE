package com.example.android.worde.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.worde.database.WordRepository;



public class DetailViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    private final WordRepository mRepository;
    private final int mId;

    public DetailViewModelFactory(WordRepository repository, int id) {
        this.mRepository = repository;
        this.mId = id;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(mRepository, mId);
    }
}
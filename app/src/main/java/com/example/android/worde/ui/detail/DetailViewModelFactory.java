package com.example.android.worde.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.worde.database.WordRepository;



public class DetailViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    private final WordRepository mRepository;

    public DetailViewModelFactory(WordRepository repository) {
        this.mRepository = repository;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(mRepository);
    }
}
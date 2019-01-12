package com.WorDE.android.app.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.WorDE.android.app.database.WordRepository;



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
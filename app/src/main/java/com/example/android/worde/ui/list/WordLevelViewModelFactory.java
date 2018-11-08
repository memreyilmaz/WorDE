package com.example.android.worde.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.worde.database.WordRepository;


public class WordLevelViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    private final WordRepository mRepository;
    private final String mLevel;

    public WordLevelViewModelFactory(WordRepository repository, String level) {
        this.mRepository = repository;
        this.mLevel = level;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new LevelViewModel(mRepository, mLevel);
    }
}

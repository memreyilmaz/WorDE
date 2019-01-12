package com.WorDE.android.app.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.WorDE.android.app.database.Word;
import com.WorDE.android.app.database.WordRepository;

import java.util.List;


public class LevelViewModel extends ViewModel {
    private final LiveData<List<Word>> mSelectedLevelWords;

    private final String mLevel;
    private final WordRepository mRepository;

    public LevelViewModel(WordRepository repository, String level) {
        mRepository = repository;
        mLevel = level;
        mSelectedLevelWords = mRepository.getWordsByLevels(mLevel);
    }
    public LiveData<List<Word>> getWordsByLevels() {
        return mSelectedLevelWords;
    }
}
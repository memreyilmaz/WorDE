package com.example.android.worde.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.example.android.worde.database.Word;
import com.example.android.worde.database.WordRepository;


public class LevelViewModel extends ViewModel {

    private final LiveData<PagedList<Word>> mSelectedLevelWords;

    private final String mLevel;
    private final WordRepository mRepository;

    public LevelViewModel(WordRepository repository, String level) {
        mRepository = repository;
        mLevel = level;
        mSelectedLevelWords = mRepository.getWordsByLevels(mLevel);
    }

    public LiveData<PagedList<Word>> getWordsByLevels() {
        return mSelectedLevelWords;
    }
}
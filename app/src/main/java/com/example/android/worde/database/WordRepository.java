package com.example.android.worde.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;
    private LiveData<Word> mSelectedWord;

    private int mWordId;

    public WordRepository(Application application) {
        WordDatabase db = WordDatabase.getInstance(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
        mSelectedWord = mWordDao.getWordById(mWordId);
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    LiveData<Word> getWordById(){
        return mSelectedWord;
    }
    public LiveData<List<Word>> getWordsByLevels(String level){
        return mWordDao.getWordsByLevels(level);
    }
}
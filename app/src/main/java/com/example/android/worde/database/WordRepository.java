package com.example.android.worde.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordDatabase db = WordDatabase.getInstance(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords(); //TODO KALDIR BUNU
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    public LiveData<Word> getWordById(int id){
        return mWordDao.getWordById(id);
    }
    public LiveData<PagedList<Word>> getWordsByLevels(String level){
        return mWordDao.getWordsByLevels(level);
    }

   /* public void setFavouriteStatus(int favourite, int id){
       // new updateWordAsyncTask(mWordDao).execute(favourite,id);
        mWordDao.addOrRemoveFavourite(favourite, id);
    }*/

   /* private static class updateWordAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao mAsyncTaskDao;
        //private int favourite;

        updateWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.addOrRemoveFavourite(params[0]);

            return null;
        }
    }*/
}
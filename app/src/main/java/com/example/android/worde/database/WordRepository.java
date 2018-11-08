package com.example.android.worde.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;

    public WordRepository(Application application) {
        WordDatabase db = WordDatabase.getInstance(application);
        mWordDao = db.wordDao();
    }
    public LiveData<Word> getWordById(int id){
        return mWordDao.getWordById(id);
    }
    public LiveData<List<Word>> getWordsByLevels(String level){
        return mWordDao.getWordsByLevels(level);
    }
    public LiveData<List<Word>> getFavouriteWords(){
        return mWordDao.getFavouriteWords();
    }
    public void setFavouriteStatus(int favourite, int id){
         mWordDao.addOrRemoveFavourite(favourite, id);
        // new updateWordAsyncTask(mWordDao).execute(favourite,id);
    }
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
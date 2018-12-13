package com.example.android.worde.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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
        updateWordAsyncTask(favourite, id);
       // new insertAsyncTask(mWordDao).execute(favourite, id);
    }

   /* public LiveData<Word> getFirstWordOfSelectedLevel(String level){
        LiveData<Word> firstWordOfLevel = mWordDao.getFirstWordOnSelectedLevelForTablet(level);
        return firstWordOfLevel;
    }*/

   /* public LiveData<Word> getFirstWordOfDb(){
        LiveData<Word> firstWordOfDb = mWordDao.getFirstWordOnDb();
        return firstWordOfDb;
    }*/

    /*public LiveData<Word> getLastWordOfDb(){
       // new getFirstAsyncTask(mWordDao).execute();
        //new getFirstAsyncTask(mWordDao).execute();
        LiveData<Word> lastWordOfDb = mWordDao.getLastWordOnDb();
        return lastWordOfDb;
    }*/

   private void updateWordAsyncTask(int favourite, int id) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mWordDao.addOrRemoveFavourite(favourite, id);
                return null;
            }
        }.execute();
    }

    /*private static class insertAsyncTask extends AsyncTask<Integer, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... ıntegers) {
            mAsyncTaskDao.addOrRemoveFavourite(ıntegers[0], ıntegers[1]);
            return null;
        }
    }*/

        /*private class getFirstAsyncTask extends AsyncTask<Void, Void, Word> {

        private WordDao mAsyncTaskDao;
        Word lastWord;


            getFirstAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Word doInBackground(Void... voids) {
            mAsyncTaskDao.getFirstWordOnDb();
            return null;
        }

            @Override
            protected void onPostExecute(Word word) {
                //super.onPostExecute(word);
                word = lastWordOfDb;
            }

        }*/
}
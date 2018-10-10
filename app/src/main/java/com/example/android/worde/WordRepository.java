package com.example.android.worde;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
   // private static volatile WordRepository INSTANCE = null;
    private WordDao mWordDao;
    //private final ExecutorService mIoExecutor;
    private LiveData<List<Word>> mAllWords;

    /*public static WordRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (WordRepository.class) {
                if (INSTANCE == null) {
                    WordDatabase database = WordDatabase.getInstance(application);
                    INSTANCE = new WordRepository(database.wordDao(),
                            Executors.newSingleThreadExecutor());

                }
            }
        }
        return INSTANCE;
    }

    public WordRepository(WordDao dao, ExecutorService executor) {
        mIoExecutor = executor;
        mWordDao = dao;
    }*/




    WordRepository(Application application) {

        WordDatabase db = WordDatabase.getInstance(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Word>> getAllWords() {
        //mIoExecutor.execute(() -> .getAllWords());
       // return mIoExecutor.submit(mWordDao::getAllWords).get();
        //return mAllWords;
        //getWordsAsyncTask task = new getWordsAsyncTask(mWordDao);
        //mAllWords = task.execute().get();

       // mAllWords = mWordDao.getAllWords();
        return mAllWords;
        /*try {
            return mIoExecutor.submit(mWordDao::getAllWords).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    /*public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }*/

   /* private static class getWordsAsyncTask extends AsyncTask<Word, Void, LiveData<List<Word>>> {

        private WordDao mAsyncTaskDao;

        getWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Word>> doInBackground(final Word... params) {
            return mAsyncTaskDao.getAllWords();
        //    return null;
        }

    }*/
}
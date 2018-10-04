package com.example.android.worde;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface WordDao {


    @Query("SELECT * from wordlist ORDER BY name ASC")
    LiveData<List<Word>> getWordsInAlphabeticalOrder();

    @Query("SELECT * from wordlist ORDER BY level")
    LiveData<List<Word>> getWordsByLevels();

    @Query("SELECT * from wordlist ORDER BY favourite ASC")
    LiveData<List<Word>> getFavouritedWords();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);


}

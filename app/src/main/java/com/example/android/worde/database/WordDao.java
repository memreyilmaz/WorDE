package com.example.android.worde.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface WordDao {


    @Query("SELECT * FROM wordlist ORDER BY name")
    LiveData<List<Word>> getAllWords();

    @Query("SELECT * FROM wordlist WHERE level = :level")
    LiveData<List<Word>> getWordsByLevels(String level);

    @Query("SELECT * FROM wordlist ORDER BY favourite ASC")
    LiveData<List<Word>> getFavouritedWords();

    @Query("SELECT * FROM wordlist WHERE _id = :id")
    LiveData<Word> getWordById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Query("UPDATE wordlist SET favourite = :favourite WHERE _id = :id")
    public void addOrRemoveFavourite(int favourite, int id);

}

package com.example.android.worde.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;



@Dao
public interface WordDao {


    @Query("SELECT * FROM wordlist ORDER BY _id ASC")
    LiveData<List<Word>> getAllWords();

    //@Query("SELECT * FROM wordlist LIMIT 1")
    //Word[] getRandomWordForWidget();

    @Query("SELECT * FROM wordlist WHERE level = :level")
    LiveData<List<Word>> getWordsByLevels(String level);

    @Query("SELECT * FROM wordlist WHERE favourite = 1 ORDER BY _id ASC")
    LiveData<List<Word>> getFavouriteWords();

    /*sample get favourites func

    @Query("SELECT * FROM match WHERE liked = 1 ORDER BY match DESC LIMIT :limit")
    fun getMatches(limit: Int = 6, liked: Boolean = true): Flowable<List<Match>>*/

    @Query("SELECT * FROM wordlist WHERE _id = :id")
    LiveData<Word> getWordById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Query("UPDATE wordlist SET favourite = :favourite WHERE _id = :id")
    void addOrRemoveFavourite(int favourite, int id);

}

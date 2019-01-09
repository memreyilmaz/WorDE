package com.example.android.worde.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) for a word.
 * Each method performs a database operation, such as updating a word or running DB queries.
 */

@Dao
public interface WordDao {

    @Query("SELECT * FROM wordlist ORDER BY _id ASC")
    LiveData<List<Word>> getAllWords();

    //Query for a random word to show in the widget
    @Query("SELECT * FROM wordlist ORDER BY RANDOM() LIMIT 1")
    Word getRandomWordForWidget();

    //Query for selected level words to show in WordListFragment
    @Query("SELECT * FROM wordlist WHERE level = :level ORDER BY _id ASC")
    LiveData<List<Word>> getWordsByLevels(String level);

    /**
     *  Query for selected levels first word to show in WordListFragment For Tablet Two Pane Layout
     *  on First Launch and to use in loadPreviousWord method in WordDetailFragment
     */
    @Query("SELECT * FROM wordlist WHERE level = :level ORDER BY _id ASC LIMIT 1")
    Word getFirstWordOnSelectedLevel(String level);

    /**
     * Query for selected levels last word to use in loadNextWord method in WordDetailFragment
     */
    @Query("SELECT * FROM wordlist WHERE level = :level ORDER BY _id DESC LIMIT 1")
    Word getLastWordOnSelectedLevel(String level);

    @Query("SELECT * FROM wordlist ORDER BY _id DESC LIMIT 1")
    Word getLastWordOnDb();

    @Query("SELECT * FROM wordlist ORDER BY _id ASC LIMIT 1")
    Word getFirstWordOnDb();

    //Query for first favourite word to show in WordDetailFragment in two panelayout
    @Query("SELECT * FROM wordlist WHERE favourite = 1 ORDER BY _id ASC LIMIT 1")
    Word getFirstFavouriteWordOnDb();

    //Query for favourite words to show in WordListFragment
    @Query("SELECT * FROM wordlist WHERE favourite = 1 ORDER BY _id ASC")
    LiveData<List<Word>> getFavouriteWords();

    //Query for selected word to show in WordDetailFragment
    @Query("SELECT * FROM wordlist WHERE _id = :id")
    LiveData<Word> getWordById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    //Query for selected word to update its favourite status
    @Query("UPDATE wordlist SET favourite = :favourite WHERE _id = :id")
    void addOrRemoveFavourite(int favourite, int id);

}

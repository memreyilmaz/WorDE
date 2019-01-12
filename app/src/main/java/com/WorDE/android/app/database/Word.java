package com.WorDE.android.app.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.WorDE.android.app.Config.COL_ARTIKEL;
import static com.WorDE.android.app.Config.COL_EXAMPLE;
import static com.WorDE.android.app.Config.COL_FAVOURITE;
import static com.WorDE.android.app.Config.COL_ID;
import static com.WorDE.android.app.Config.COL_LEVEL;
import static com.WorDE.android.app.Config.COL_NAME;
import static com.WorDE.android.app.Config.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Word {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = COL_ID)
    private Integer mWordId;
    @NonNull
    @ColumnInfo(name = COL_LEVEL)
    private String mWordLevel;
    @ColumnInfo(name = COL_ARTIKEL)
    private String mWordArtikel;
    @NonNull
    @ColumnInfo(name = COL_NAME)
    private String mWordName;
    @NonNull
    @ColumnInfo(name = COL_EXAMPLE)
    private String mWordExample;
    @NonNull
    @ColumnInfo (name = COL_FAVOURITE)
    private boolean mWordFavourite;

    public Word(@NonNull String wordLevel, String wordArtikel, @NonNull String wordName, @NonNull String wordExample, @NonNull boolean wordFavourite) {
        this.mWordLevel = wordLevel;
        this.mWordArtikel = wordArtikel;
        this.mWordName = wordName;
        this.mWordExample = wordExample;
        this.mWordFavourite = wordFavourite;
    }
    public Integer getWordId() {
        return mWordId;
    }
    public void setWordId(Integer wordId) {
        this.mWordId = wordId;
    }
    @NonNull
    public String getWordLevel() {
        return mWordLevel;
    }
    public String getWordArtikel() {
        return mWordArtikel;
    }
    @NonNull
    public String getWordName() {
        return mWordName;
    }
    @NonNull
    public String getWordExample() {
        return mWordExample;
    }
    @NonNull
    public boolean getWordFavourite() {
        return mWordFavourite;
    }
}

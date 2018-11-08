package com.example.android.worde.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

@Database(entities = {Word.class}, version = 2)
public abstract class WordDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static volatile WordDatabase INSTANCE = null;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {

            }
        };
    @NonNull
    public static synchronized WordDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                             WordDatabase.class, "word_database")
                             .addMigrations(MIGRATION_1_2)
                            .allowMainThreadQueries() //TODO remove after moving add to favourites func to asynctask
                             .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                             .build();
                }
            }
        }
        return INSTANCE;
    }
}

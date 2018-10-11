package com.example.android.worde;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 1)
public abstract class WordDatabase extends RoomDatabase {

    public abstract WordDao wordDao();
    private static final Executor executor = Executors.newSingleThreadExecutor();

    private static volatile WordDatabase INSTANCE = null;

    @NonNull
    public static synchronized WordDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordDatabase.class, "word_database")
                            //.allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                   // super.onCreate(db);
                                    //executor.execute();{
                                     //   fillWithDemoData(context.getApplicationContext());
                                        ;
                                    //});
                                   // fillWithDemoData(context.getApplicationContext());
                                    executor.execute(() -> {
                                        fillWithDemoData(context.getApplicationContext());
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    @WorkerThread
    private static void fillWithDemoData(Context context) {
        WordDao dao = getInstance(context).wordDao();
        JSONArray word = loadJsonArray(context);
        try {
            for (int i = 0; i < word.length(); i++) {
                JSONObject item = word.getJSONObject(i);
                dao.insert(new Word(
                        item.getString("level"),
                        item.getString("artikel"),
                        item.getString("name"),
                        item.getString("example"),
                        item.getInt("favourite")));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    private static JSONArray loadJsonArray(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.word);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("words");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }

        return null;
    }

}

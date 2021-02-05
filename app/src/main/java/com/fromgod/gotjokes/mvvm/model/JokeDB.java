package com.fromgod.gotjokes.mvvm.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Joke.class}, exportSchema = false, version = 2)
public abstract class JokeDB extends RoomDatabase {
    private static final String TAG = "Database";

    private static JokeDB instance;
    public abstract JokeDAO jokeDAO();

    public static synchronized JokeDB getInstance(Context context) {
        if(instance == null) {
            instance = createJokeDB(context);
        }
        return instance;
    }

    private static JokeDB createJokeDB(Context context) {
        RoomDatabase.Builder<JokeDB> builder = Room.databaseBuilder(context.getApplicationContext(), JokeDB.class, "joke_database");
        return builder.fallbackToDestructiveMigration().build();
    }

}

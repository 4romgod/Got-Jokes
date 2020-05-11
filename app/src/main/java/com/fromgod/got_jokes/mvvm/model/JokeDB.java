package com.fromgod.got_jokes.mvvm.model;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Joke.class}, exportSchema = false, version = 2)
public abstract class JokeDB extends RoomDatabase {
    private static final String TAG = "Database";

    private static JokeDB INSTANCE;

    public abstract JokeDAO jokeDAO();


    public static synchronized JokeDB getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = create(context);
        }

        return INSTANCE;
    }

    private static JokeDB create(Context context){
        RoomDatabase.Builder<JokeDB> builder =
                Room.databaseBuilder(context.getApplicationContext(),
                        JokeDB.class, "joke_database");

        return builder.fallbackToDestructiveMigration().build();
    }


}

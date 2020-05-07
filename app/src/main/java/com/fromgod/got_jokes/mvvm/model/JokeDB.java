package com.fromgod.got_jokes.mvvm.model;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Joke.class}, exportSchema = false, version = 1)
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

        return builder.fallbackToDestructiveMigration().addCallback(roomcallback).build();
    }


    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "onCreate: calling populate...");
            new PopulateDbAsyncTask(INSTANCE).execute();
        }

    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private static final String TAG = "Populate";
        
        private JokeDAO jokeDAO;

        private PopulateDbAsyncTask(JokeDB db){
            jokeDAO = db.jokeDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: Populating database");
            jokeDAO.insert(new Joke("Programming", "single", "some funny things that i feel like sharing with the world", "", ""));
            jokeDAO.insert(new Joke("Dark","twopart",  "some funny things that i feel like sharing with the world", "", ""));
            jokeDAO.insert(new Joke("Miscellaneous","single",  "some funny things that i feel like sharing with the world", "", ""));

            return null;
        }
    }

}

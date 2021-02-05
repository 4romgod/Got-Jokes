package com.fromgod.gotjokes.mvvm.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JokeDAO {

    @Insert
    void insert(Joke joke);

    @Query("SELECT * FROM jokes_table")
    LiveData<List<Joke>> getAllJokes();

    @Query("SELECT * FROM jokes_table WHERE id= :id")
    LiveData<Joke> getJoke(int id);

    @Update
    void update(Joke joke);

    @Delete
    void delete(Joke joke);

    @Query("Delete from jokes_table")
    void deleteAllJokes();

    @Query("SELECT COUNT() FROM jokes_table WHERE id = :id")
    LiveData<Integer> getCount(int id);

}

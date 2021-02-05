package com.fromgod.gotjokes.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fromgod.gotjokes.mvvm.model.Joke;
import com.fromgod.gotjokes.mvvm.model.JokeRepository;

import java.util.List;

public class JokeViewModel extends AndroidViewModel {

    private JokeRepository jokeRepository;
    private LiveData<List<Joke>> jokeListLiveData;

    public JokeViewModel(@NonNull Application application) {
        super(application);
        jokeRepository = new JokeRepository(application);
        jokeListLiveData = jokeRepository.getAllJokes();
    }

    public LiveData<List<Joke>> getAllJokes(){
        return jokeListLiveData;
    }

    public LiveData<Joke> getJoke(int id){
        return jokeRepository.getJoke(id);
    }

    public LiveData<Integer> getCount(int id){
        return jokeRepository.getCount(id);
    }

    public void insert(Joke joke){
        jokeRepository.insert(joke);
    }

    public void update(Joke joke){
        jokeRepository.update(joke);
    }

    public void delete(Joke joke){
        jokeRepository.delete(joke);
    }

    public void deleteAllItems(){
        jokeRepository.deleteAllItems();
    }

}

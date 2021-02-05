package com.fromgod.gotjokes.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fromgod.gotjokes.mvvm.model.Joke;
import com.fromgod.gotjokes.mvvm.model.JokeRepository;

import java.util.List;

public class JokeViewModel extends AndroidViewModel {

    private JokeRepository repository;
    private LiveData<List<Joke>> jokes;


    public JokeViewModel(@NonNull Application application) {
        super(application);
        repository = new JokeRepository(application);
        jokes = repository.getAllJokes();
    }       //end constructor()


    public LiveData<List<Joke>> getAllJokes(){
        return jokes;
    }       //end getAllJokes()

    public LiveData<Joke> getJoke(int id){
        return repository.getJoke(id);
    }

    public LiveData<Integer> getCount(int id){
        return repository.getCount(id);
    }


    public void insert(Joke joke){
        repository.insert(joke);
    }       //end insert()

    public void update(Joke joke){
        repository.update(joke);
    }

    public void delete(Joke joke){
        repository.delete(joke);
    }

    public void deleteAllItems(){
        repository.deleteAllItems();
    }


}

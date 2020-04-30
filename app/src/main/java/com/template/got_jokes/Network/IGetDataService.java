package com.template.got_jokes.Network;

import com.template.got_jokes.mvvm.model.Joke;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IGetDataService {

    @GET("programming?type=single")
    Call<List<Joke>> getJokes();

    @GET("programming?type=single")
    Call<Joke> getAJoke();

}       //end interface

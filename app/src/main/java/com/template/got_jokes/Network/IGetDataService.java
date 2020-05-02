package com.template.got_jokes.Network;

import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.mvvm.model.Joke1P;
import com.template.got_jokes.mvvm.model.Joke2P;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGetDataService {

    @GET("{category}?type=single")
    Call<Joke1P> getJoke1P(
            @Path("category") String category,
            @Query("contains") String contain
    );

    @GET("{category}?type=twopart")
    Call<Joke2P> getJoke2P(
            @Path("category") String category,
            @Query("contains") String contain
    );

}       //end interface

package com.template.got_jokes.Network;

import com.template.got_jokes.mvvm.model.Joke;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGetDataService {

    @GET("{category}")
    Call<Joke> getJoke(
            @Path("category") String category,
            @Query("contains") String contain
    );

    /*@GET("{category}?type=single")
    Call<Joke1P> getJoke1P(
            @Path("category") String category,
            @Query("contains") String contain
    );*/


}       //end interface

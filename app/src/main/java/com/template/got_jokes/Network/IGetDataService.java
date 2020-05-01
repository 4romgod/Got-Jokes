package com.template.got_jokes.Network;

import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.mvvm.model.Joke2P;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGetDataService {

    @GET("{category}")
    Call<Joke> getJoke(
            @Path("category") String category,
            @Query("type") String type
    );

    /*@GET("{category}?type=twopart")
    Call<Joke2P> getJoke2P(@Path("category") String category);*/

    /*@GET("{category}")
    Call<Joke> getJoke(@Path("category") String cat);*/

   /* @GET("{category}")
    Call<Joke> getJoke(@Path("category") String cat, @Query("type") String type);*/

/*    @GET("any")
    Call<Joke> getJoke(@Query("contains") String contains);*/

}       //end interface

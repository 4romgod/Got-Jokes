package com.template.got_jokes.Network;

import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.mvvm.model.Joke1P;
import com.template.got_jokes.mvvm.model.Joke2P;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGetDataService {

    @GET("{category}?type=single")
    Call<Joke1P> getJoke1P(
            @Path("category") String category
    );

    @GET("{category}?type=twopart")
    Call<Joke2P> getJoke2P(
            @Path("category") String category
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

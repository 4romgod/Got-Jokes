package com.fromgod.gotjokes.Network;

import com.fromgod.gotjokes.mvvm.model.Joke;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGetDataService {

    @GET("{category}")
    Call<Joke> getJoke(@Path("category") String category, @Query("contains") String contain);

}

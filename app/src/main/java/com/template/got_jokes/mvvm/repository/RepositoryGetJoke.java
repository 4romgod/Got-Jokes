package com.template.got_jokes.mvvm.repository;

import android.widget.Toast;

import com.template.got_jokes.Network.IGetDataService;
import com.template.got_jokes.Network.RetrofitClientInstance;
import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.mvvm.model.Joke1P;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*public class RepositoryGetJoke {

    IGetDataService service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);

    public void getJoke(){
        Call<Joke1P> call = service.getJoke1P("Dark");
        call.enqueue(new Callback<Joke1P>() {
            @Override
            public void onResponse(Call<Joke1P> call, Response<Joke1P> response) {
                progressDialog.dismiss();

                Joke joke = response.body();
                tvCategory.setText(joke.getCategory());
                tvJoke.setText(joke.getJoke());
            }

            @Override
            public void onFailure(Call<Joke1P> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something got fucked up", Toast.LENGTH_SHORT).show();
            }
        });
    }

}*/

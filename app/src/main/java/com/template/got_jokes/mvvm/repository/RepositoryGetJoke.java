package com.template.got_jokes.mvvm.repository;

import android.widget.Toast;

import com.template.got_jokes.Network.IGetDataService;
import com.template.got_jokes.Network.RetrofitClientInstance;
import com.template.got_jokes.mvvm.model.Joke;

import retrofit2.Call;
import retrofit2.Callback;

/*public class RepositoryGetJoke {

    IGetDataService service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);

    Call<Joke> call = service.getJoke();
        call.enqueue(new Callback<Joke>() {
        @Override
        public void onResponse(Call<Joke> call, Response<Joke> response) {
            progressDialog.dismiss();

            Joke joke = response.body();
            tvCategory.setText(joke.getCategory());
            tvJoke.setText(joke.getJoke());
        }

        @Override
        public void onFailure(Call<Joke> call, Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Something got fucked up", Toast.LENGTH_SHORT).show();
        }
    });
}*/

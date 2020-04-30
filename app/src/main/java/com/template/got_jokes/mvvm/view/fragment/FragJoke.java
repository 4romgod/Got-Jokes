package com.template.got_jokes.mvvm.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.Network.IGetDataService;
import com.template.got_jokes.Network.RetrofitClientInstance;
import com.template.got_jokes.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragJoke extends Fragment {

    //VIEWS
    View layoutMain;
    ProgressDialog progressDialog;

    TextView tvCategory, tvJoke;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }       //end onCreate()

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutMain == null){
            layoutMain = inflater.inflate(R.layout.frag_jokes, null);
        }

        tvCategory = layoutMain.findViewById(R.id.text_category);
        tvJoke = layoutMain.findViewById(R.id.text_joke);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        IGetDataService service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);

        Call<Joke> call = service.getAJoke();
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

        return layoutMain;
    }       //end onCreateView()


}       //end class

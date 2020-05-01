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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.Network.IGetDataService;
import com.template.got_jokes.Network.RetrofitClientInstance;
import com.template.got_jokes.R;
import com.template.got_jokes.mvvm.model.Joke2P;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragLaugh extends Fragment {

    //VIEWS
    View layoutMain;
    ProgressDialog progressDialog;
    TextView tvCategory, tvJoke, tvJoke2;
    FloatingActionButton fabNext;

    IGetDataService service;

    String jokeType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }       //end onCreate()

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutMain == null){
            layoutMain = inflater.inflate(R.layout.frag_laugh, null);
        }
        initViews();

        jokeType = getArguments().getString("JOKE_CAT");

        service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);
        getJoke2P();

        return layoutMain;
    }       //end onCreateView()


    public void initViews(){
        tvCategory = layoutMain.findViewById(R.id.text_category);
        tvJoke = layoutMain.findViewById(R.id.text_joke);
        tvJoke2 = layoutMain.findViewById(R.id.text_joke2);
        fabNext = layoutMain.findViewById(R.id.fab_next);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJoke2P();
            }
        });

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }


    public void getJoke(){
        Call<Joke> call = service.getJoke(jokeType);
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

    }

    public void getJoke2P(){
        Call<Joke2P> call = service.getJoke2P(jokeType);
        call.enqueue(new Callback<Joke2P>() {
            @Override
            public void onResponse(Call<Joke2P> call, Response<Joke2P> response) {
                progressDialog.dismiss();

                Joke2P joke2P = response.body();
                tvCategory.setText(joke2P.getCategory());
                tvJoke.setText(joke2P.getSetup());
                tvJoke2.setText(joke2P.getDelivery());
            }


            @Override
            public void onFailure(Call<Joke2P> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something got fucked up", Toast.LENGTH_SHORT).show();
            }
        });

    }

}       //end class

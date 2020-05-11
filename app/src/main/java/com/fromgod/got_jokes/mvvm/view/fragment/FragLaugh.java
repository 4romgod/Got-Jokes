package com.fromgod.got_jokes.mvvm.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fromgod.got_jokes.mvvm.viewmodel.JokeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.fromgod.got_jokes.Network.IGetDataService;
import com.fromgod.got_jokes.Network.RetrofitClientInstance;
import com.fromgod.got_jokes.R;
import com.fromgod.got_jokes.mvvm.model.Joke;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragLaugh extends Fragment {
    private static final String TAG = "FragLaugh";

    //VIEWS
    View layoutMain;
    ProgressBar progressBar;
    TextView textCategory, textSetup, textDelivery;
    FloatingActionButton fabNext, fabPrev, fabSave;

    // PATH AND QUERY STRINGS
    String jokeCat = "";
    String jokeContains = "";

    JokeViewModel viewModel;

    Joke joke;

    IGetDataService service;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }       //end onCreate()


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutMain == null) {
            layoutMain = inflater.inflate(R.layout.frag_laugh, null);
        }
        initViews();

        viewModel = ViewModelProviders.of(this).get(JokeViewModel.class);

        service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);

        jokeCat = getArguments().getString("JOKE_CAT");
        jokeContains = getArguments().getString("JOKE_CONTAINS");

        return layoutMain;
    }       //end onCreateView()

    @Override
    public void onStart() {
        super.onStart();

        getJoke();

        nextBtn();
        saveBtn();
    }       //end onStart()


    public void initViews() {
        progressBar = layoutMain.findViewById(R.id.progressBar);

        textCategory =  layoutMain.findViewById(R.id.text_category);
        textSetup =  layoutMain.findViewById(R.id.text_setup);
        textDelivery =  layoutMain.findViewById(R.id.text_delivery);

        fabNext = layoutMain.findViewById(R.id.fab_next);
        fabPrev = layoutMain.findViewById(R.id.fab_prev);
        fabSave = layoutMain.findViewById(R.id.fab_save);
    }

    public void nextBtn() {
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getJoke();
            }

        });
    }

    public void saveBtn(){
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Saving...", Toast.LENGTH_SHORT).show();
                viewModel.insert(joke);
            }
        });
    }


    public void getJoke() {
        Call<Joke> call = service.getJoke(jokeCat, jokeContains);
        progressBar.setVisibility(View.VISIBLE);

        Log.d(TAG, "getJoke: about to get a joke...");
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                progressBar.setVisibility(View.GONE);

                joke = response.body();

                if(joke != null) {
                    Log.d(TAG, "onResponse: Joke: " + joke.toString());
                    display(joke);
                }

            }   //end onRequest()

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                textCategory.setText("Something went wrong...");
                textSetup.setText("");
                textDelivery.setText("");

                Log.d(TAG, "onFailure: Couldn't get the joke");
            }
        });

    }           //end getJoke()


    public void display(Joke joke){

        if (joke.getError() == true) {
            textCategory.setText("No matching joke found...");
            textSetup.setText("");
            textDelivery.setText("");
        }
        else {
            if (joke.getType().equalsIgnoreCase(getString(R.string.single))) {
                textCategory.setText(joke.getCategory());
                textSetup.setText(joke.getJoke());
            }
            else if (joke.getType().equalsIgnoreCase(getString(R.string.twopart))) {
                textCategory.setText(joke.getCategory());
                textSetup.setText(joke.getSetup());
                textDelivery.setText(joke.getDelivery());
            }
        }

    }       //end display()



}       //end class

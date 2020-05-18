package com.fromgod.got_jokes.mvvm.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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


public class FragLaugh extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragLaugh";

    //VIEWS
    View layoutMain;
    ProgressDialog progressDialog;
    TextView textCategory, textSetup, textDelivery;
    FloatingActionButton fabNext, fabPrev, fabSave;

    JokeViewModel viewModel;

    IGetDataService service;

    // PATH AND QUERY STRINGS
    String jokeCat = "";
    String jokeContains = "";

    Joke joke;


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

        viewModel = ViewModelProviders.of(getActivity()).get(JokeViewModel.class);

        service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);

        jokeCat = getArguments().getString("JOKE_CAT");
        jokeContains = getArguments().getString("JOKE_CONTAINS");

        return layoutMain;
    }       //end onCreateView()


    @Override
    public void onStart() {
        super.onStart();

        getJoke();

    }       //end onStart()


    public void initViews() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");

        textCategory =  layoutMain.findViewById(R.id.text_category);
        textSetup =  layoutMain.findViewById(R.id.text_setup);
        textDelivery =  layoutMain.findViewById(R.id.text_delivery);

        fabPrev = layoutMain.findViewById(R.id.fab_prev);
        fabNext = layoutMain.findViewById(R.id.fab_next);
        fabNext.setOnClickListener(this);

        fabSave = layoutMain.findViewById(R.id.fab_save);
        fabSave.setOnClickListener(this);
    }       //end initViews()


    public void getJoke() {
        Call<Joke> call = service.getJoke(jokeCat, jokeContains);
        progressDialog.show();

        Log.d(TAG, "getJoke: about to get a joke...");
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                progressDialog.dismiss();

                joke = response.body();

                if(joke != null) {
                    Log.d(TAG, "onResponse: Joke: " + joke.toString());
                    display(joke);
                }

            }   //end onRequest()

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                progressDialog.dismiss();

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


    @Override
    public void onClick(View v) {

        // clicked the save button
        if(v.getId() == R.id.fab_save){
            progressDialog.show();

            if( joke!=null ){
                saveJoke(joke);
            }       //end if{}
            progressDialog.dismiss();

        }

        // clicked the next button
        else if(v.getId() == R.id.fab_next){
            getJoke();
        }

    }       //end onClick()

    public void saveJoke(final Joke joke){
        viewModel.getCount(joke.getId()).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                // 1. check if joke is already in the database
                if(integer <= 0){
                    Log.d(TAG, "onClick: Joke is unique... count: " + integer);

                    // 2.1 if not, then insert
                    viewModel.insert(joke);
                    Toast.makeText(getActivity(), "Saving...", Toast.LENGTH_SHORT).show();
                }else{

                    // 2.2 else, do not insert
                    Log.d(TAG, "onChanged: Joke is not unique... count: "+integer);
                    Toast.makeText(getActivity(), "Already saved...", Toast.LENGTH_SHORT).show();
                }

            }       //end onChanged()

        });
    }


}       //end class

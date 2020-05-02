package com.template.got_jokes.mvvm.view.fragment;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.template.got_jokes.Network.IGetDataService;
import com.template.got_jokes.Network.RetrofitClientInstance;
import com.template.got_jokes.R;
import com.template.got_jokes.utils.Util;
import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.mvvm.model.Joke1P;
import com.template.got_jokes.mvvm.model.Joke2P;


import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragLaugh extends Fragment {
    private static final String TAG = "FragLaugh";

    //VIEWS
    View layoutMain;
    ProgressDialog progressDialog;
    TextView tvCategory, tvJoke, tvJoke2;
    FloatingActionButton fabNext, fabPrev, fabSave;

    IGetDataService service;

    String jokeCat = "";
    String jokeContains = "";


    LinkedList listJokes = new LinkedList();
    int index = 0;

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
        service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);

        jokeCat = getArguments().getString("JOKE_CAT");
        jokeContains = getArguments().getString("JOKE_CONTAINS");

        return layoutMain;
    }       //end onCreateView()

    @Override
    public void onStart() {
        super.onStart();

        getRandJoke();

        nextBtn();
        prevBtn();
        saveBtn();

    }       //end onStart()


    public void initViews() {
        tvCategory = layoutMain.findViewById(R.id.text_category);
        tvJoke = layoutMain.findViewById(R.id.text_joke);
        tvJoke2 = layoutMain.findViewById(R.id.text_joke2);

        fabNext = layoutMain.findViewById(R.id.fab_next);
        fabPrev = layoutMain.findViewById(R.id.fab_prev);
        fabSave = layoutMain.findViewById(R.id.fab_save);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }


    public void prevBtn() {
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index <= 0) {
                    Toast.makeText(getActivity(), "Cant go back", Toast.LENGTH_SHORT).show();
                } else {
                    index = index - 1;
                    Joke joke = (Joke) listJokes.get(index);
                    if(joke.getType().equalsIgnoreCase(getString(R.string.single))){
                        display1P((Joke1P) listJokes.get(index));
                    }
                    else{
                        display2P((Joke2P) listJokes.get(index));
                    }
                }

                Log.d(TAG, "onClick prev: size: " + listJokes.size());
                Log.d(TAG, "onClick prev: index: " + index);
            }
        });
    }

    public void nextBtn() {
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index >= listJokes.size() - 1) {
                    index = listJokes.size() - 1;
                    getRandJoke();
                }
                else {
                    index++;
                    Joke joke = (Joke) listJokes.get(index);
                    if(joke.getType().equalsIgnoreCase(getString(R.string.single))){
                        display1P((Joke1P) listJokes.get(index));
                    }
                    else{
                        display2P((Joke2P) listJokes.get(index));
                    }
                }

                Log.d(TAG, "onClick next: size: " + listJokes.size());
                Log.d(TAG, "onClick next: index: " + index);
            }
        });
    }

    public void saveBtn(){
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Saving the Joke", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getJoke2P() {
        Call<Joke2P> call = service.getJoke2P(jokeCat, jokeContains);
        call.enqueue(new Callback<Joke2P>() {
            @Override
            public void onResponse(Call<Joke2P> call, Response<Joke2P> response) {
                progressDialog.dismiss();

                Joke2P joke = (Joke2P) response.body();
                Log.d(TAG, "onResponse: Joke: " + joke.toString());
                display2P(joke);

                listJokes.addLast(joke);
                index = listJokes.size() - 1;
            }

            @Override
            public void onFailure(Call<Joke2P> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something got fucked up", Toast.LENGTH_SHORT).show();
            }
        });
    }           //end getJoke1P()

    public void getJoke1P() {
        Call<Joke1P> call = service.getJoke1P(jokeCat, jokeContains);
        call.enqueue(new Callback<Joke1P>() {
            @Override
            public void onResponse(Call<Joke1P> call, Response<Joke1P> response) {
                progressDialog.dismiss();

                Joke1P joke = (Joke1P) response.body();
                Log.d(TAG, "onResponse: Joke: " + joke.toString());
                display1P(joke);

                listJokes.addLast(joke);
                index = listJokes.size() - 1;
            }

            @Override
            public void onFailure(Call<Joke1P> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something got fucked up", Toast.LENGTH_SHORT).show();
            }
        });
    }           //end getJoke1P()

    public void getRandJoke(){
        progressDialog.show();

        if(Util.getRandType(getContext()).equals(getString(R.string.twopart))){
            getJoke2P();
        }
        else if (Util.getRandType(getContext()).equals(getString(R.string.single))){
            getJoke1P();
        }
    }           //end getRandJoke()


    public void display2P(Joke2P joke2P) {
        tvCategory.setText(joke2P.getCategory());
        tvJoke.setText(joke2P.getSetup());
        tvJoke2.setText(joke2P.getDelivery());

        Log.d(TAG, "display: joke: " + joke2P.toString());
    }       //end display2P()*/
    public void display1P(Joke1P joke) {
        tvCategory.setText(joke.getCategory());
        tvJoke.setText(joke.getJoke());

        Log.d(TAG, "display: joke: " + joke.toString());
    }       //end display2P()*/


}       //end class

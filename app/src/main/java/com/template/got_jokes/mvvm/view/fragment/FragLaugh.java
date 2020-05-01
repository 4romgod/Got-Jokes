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
import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.mvvm.model.Joke1P;
import com.template.got_jokes.mvvm.model.Joke2P;

import java.util.LinkedList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragLaugh extends Fragment {
    private static final String TAG = "FragLaugh";

    //VIEWS
    View layoutMain;
    ProgressDialog progressDialog;
    TextView tvCategory, tvJoke, tvJoke2;
    FloatingActionButton fabNext, fabPrev;

    IGetDataService service;

    String jokeCat;

    LinkedList listJokes = new LinkedList();
    int index = 0;

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
        service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);

        jokeCat = getArguments().getString("JOKE_CAT");

        return layoutMain;
    }       //end onCreateView()

    @Override
    public void onStart() {
        super.onStart();

        getJoke();

        nextBtn();
        prevBtn();

    }       //end onStart()


    public void initViews(){
        tvCategory = layoutMain.findViewById(R.id.text_category);
        tvJoke = layoutMain.findViewById(R.id.text_joke);
        tvJoke2 = layoutMain.findViewById(R.id.text_joke2);

        fabNext = layoutMain.findViewById(R.id.fab_next);

        fabPrev = layoutMain.findViewById(R.id.fab_prev);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }


    public void prevBtn(){
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(index <= 0){
                    Toast.makeText(getActivity(), "Cant go back", Toast.LENGTH_SHORT).show();
                }
                else{
                    index = index-1;
                    Joke joke = (Joke) listJokes.get(index);
                    display(joke);
                }

                Log.d(TAG, "onClick prev: size: "+listJokes.size());
                Log.d(TAG, "onClick prev: index: "+index);
            }
        });
    }


    public void nextBtn(){
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index >= listJokes.size()-1){
                    progressDialog.show();
                    getJoke();
                    index = listJokes.size()-1;
                }
                else {
                    index++;
                    Joke joke = (Joke) listJokes.get(index);
                    display(joke);
                }

                Log.d(TAG, "onClick next: size: "+listJokes.size());
                Log.d(TAG, "onClick next: index: "+index);
            }
        });
    }


    public void getJoke(){
        String[] types = {getString(R.string.single), getString(R.string.twopart)};
        int indexType = (new Random()).nextInt(2);
        String type = types[indexType];

        Call<Joke> call = service.getJoke(jokeCat, type);
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                progressDialog.dismiss();

                Joke joke = response.body();
                Log.d(TAG, "onResponse: Joke: "+joke.toString());
                display(joke);

                listJokes.addLast(joke);
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something got fucked up", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void display(Joke joke){

        if(joke.getType().equals(getString(R.string.single))){
            //Joke1P joke1P = (Joke1P) joke;
            tvCategory.setText(joke1P.getCategory());
            tvJoke.setText(joke1P.getJoke());
            Log.d(TAG, "display: joke: "+joke1P.toString());
        }
        else if (joke.getType().equals(getString(R.string.twopart))){
            //Joke2P joke2P = (Joke2P) joke;
            tvCategory.setText(joke2P.getCategory());
            tvJoke.setText(joke2P.getSetup());
            tvJoke2.setText(joke2P.getDelivery());
            Log.d(TAG, "display: joke: "+joke2P.toString());
        }
        else {
            Log.d(TAG, "display: joke: NO JOKE TO DISPLAY");
        }



    }       //end display()



}       //end class

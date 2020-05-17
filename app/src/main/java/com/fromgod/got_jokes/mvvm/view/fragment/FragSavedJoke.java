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
import androidx.lifecycle.ViewModelProviders;

import com.fromgod.got_jokes.Network.IGetDataService;
import com.fromgod.got_jokes.Network.RetrofitClientInstance;
import com.fromgod.got_jokes.R;
import com.fromgod.got_jokes.mvvm.model.Joke;
import com.fromgod.got_jokes.mvvm.viewmodel.JokeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragSavedJoke extends Fragment {
    private static final String TAG = "FragLaugh";

    //VIEWS
    View layoutMain;
    TextView textCategory, textSetup, textDelivery;


    Joke joke;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }       //end onCreate()


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutMain == null) {
            layoutMain = inflater.inflate(R.layout.frag_saved_joke, null);
        }
        initViews();

        return layoutMain;
    }       //end onCreateView()


    @Override
    public void onStart() {
        super.onStart();

    }       //end onStart()


    public void initViews() {

        textCategory =  layoutMain.findViewById(R.id.text_category);
        textSetup =  layoutMain.findViewById(R.id.text_setup);
        textDelivery =  layoutMain.findViewById(R.id.text_delivery);

    }       //end initViews()



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

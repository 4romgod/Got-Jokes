package com.fromgod.got_jokes.mvvm.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fromgod.got_jokes.R;
import com.fromgod.got_jokes.mvvm.model.Joke;
import com.fromgod.got_jokes.mvvm.viewmodel.JokeViewModel;

import java.util.List;


public class FragSaved extends Fragment {
    private static final String TAG = "FragSaved";

    //VIEWS
    View layoutMain;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    private JokeViewModel viewModel;
    private LiveData<List<Joke>> liveJokes;

    AdapterRecycler  adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }       //end onCreate()

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutMain == null){
            layoutMain = inflater.inflate(R.layout.frag_saved, null);
        }
        initViews();

        adapter = new AdapterRecycler();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(JokeViewModel.class);
        liveJokes = viewModel.getAllJokes();
        Log.d(TAG, "onCreateView: getting all jokes...");
        liveJokes.observe(getActivity(), new Observer<List<Joke>>() {
            @Override
            public void onChanged(List<Joke> jokes) {
                Log.d(TAG, "onChanged: size: " + jokes.size());
                progressBar.setVisibility(View.GONE);

                adapter.setJokes(jokes);

                //Log.d(TAG, "onChanged: joke: " + jokes.get(0).toString());

            }
        });

        return layoutMain;
    }       //end onCreateView()


    public void initViews(){
        recyclerView = layoutMain.findViewById(R.id.layout_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        progressBar = layoutMain.findViewById(R.id.progressBar);
    }       //end initViews()



}       //end class

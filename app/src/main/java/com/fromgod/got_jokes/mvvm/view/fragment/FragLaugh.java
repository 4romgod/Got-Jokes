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
import androidx.viewpager.widget.ViewPager;

import com.fromgod.got_jokes.ViewPagerAdapter;
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
    ViewPager viewPager;
    ProgressBar progressBar;
    TextView tvCategory, tvJoke, tvJoke2;
    FloatingActionButton fabNext, fabPrev, fabSave;

    ViewPagerAdapter myAdapter;


    // PATH AND QUERY STRINGS
    String jokeCat = "";
    String jokeContains = "";

    IGetDataService service;

    JokeViewModel viewModel;

    // TEMPORARILY HOLDS JOKES TO ENABLE NEXT,PREV BUTTONS
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

        viewModel = ViewModelProviders.of(this).get(JokeViewModel.class);


        jokeCat = getArguments().getString("JOKE_CAT");
        jokeContains = getArguments().getString("JOKE_CONTAINS");

        return layoutMain;
    }       //end onCreateView()

    @Override
    public void onStart() {
        super.onStart();

        getJoke();

        nextBtn();
        prevBtn();
        saveBtn();
    }       //end onStart()


    public void initViews() {
        progressBar = (ProgressBar) layoutMain.findViewById(R.id.progressBar);

        viewPager = (ViewPager) layoutMain.findViewById(R.id.layout_view_pager);
        myAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(myAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }

        });

        fabNext = layoutMain.findViewById(R.id.fab_next);
        fabPrev = layoutMain.findViewById(R.id.fab_prev);
        fabSave = layoutMain.findViewById(R.id.fab_save);

    }


    public void prevBtn() {
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index <= 0) {
                    Toast.makeText(getActivity(), "Cant go back", Toast.LENGTH_SHORT).show();
                } else {
                    index = index - 1;
                    viewPager.setCurrentItem(index);
                }

                Log.d(TAG, "onClick prev: size: " + myAdapter.getCount());
                Log.d(TAG, "onClick prev: index: " + index);
            }
        });
    }

    public void nextBtn() {
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index >= myAdapter.getCount() - 1) {
                    getJoke();
                }
                else {
                    index++;
                    viewPager.setCurrentItem(index);
                }

                Log.d(TAG, "onClick next: size: " + myAdapter.getCount());
                Log.d(TAG, "onClick next: index: " + index);
            }
        });
    }

    public void saveBtn(){
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming Soon...", Toast.LENGTH_SHORT).show();

                int position = viewPager.getCurrentItem();
                Joke joke = myAdapter.getListJokes().get(position);
                viewModel.insert(joke);
                Log.d(TAG, "onClick: joke: "+joke.toString());
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

                Joke joke = response.body();
                Log.d(TAG, "onResponse: Joke: " + joke.toString());

                if(joke != null){
                    myAdapter.addJoke(joke);
                    viewPager.setCurrentItem(myAdapter.getCount()-1);
                }

            }   //end onRequest()

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: Couldnt get the joke");
            }
        });

    }           //end getJoke()

}       //end class

package com.fromgod.gotjokes.mvvm.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fromgod.gotjokes.Network.IGetDataService;
import com.fromgod.gotjokes.Network.RetrofitClientInstance;
import com.fromgod.gotjokes.mvvm.model.Joke;
import com.fromgod.gotjokes.mvvm.view.activity.MainActivity;
import com.fromgod.gotjokes.mvvm.viewmodel.JokeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.fromgod.gotjokes.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragHome extends Fragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "FragJokes";

    //VIEWS
    View layoutMain;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    TextView textCategory, textSetup, textDelivery;
    FloatingActionButton fabNext, fabPrev, fabSave;

    ActionBar actionBar;
    DrawerLayout mDrawerLayout;     //container that allows drawer to be pulled out from either end
    ActionBarDrawerToggle mToggle;      //tie functionality of drawer layout to the toolbar
    NavigationView navView;

    JokeViewModel viewModel;

    IGetDataService service;

    // PATH AND QUERY STRINGS
    String catJoke = "";
    String keyWord = "";

    Joke joke;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);        // enables toggle btn, and options menu
    }       //end onCreate()

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutMain == null) {
            layoutMain = inflater.inflate(R.layout.frag_home, null);
        }
        initViews();
        setupToolbar();

        catJoke = getString(R.string.any);

        viewModel = ViewModelProviders.of(getActivity()).get(JokeViewModel.class);
        service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);

        return layoutMain;
    }       //end onCreateView()


    @Override
    public void onStart() {
        super.onStart();

        if (MainActivity.fragActive == MainActivity.fragHome) {
            getJoke();
        }

    }       //end onStart()


    public void initViews() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Home...");

        textCategory = layoutMain.findViewById(R.id.text_category);
        textSetup = layoutMain.findViewById(R.id.text_setup);
        textDelivery = layoutMain.findViewById(R.id.text_delivery);

        fabPrev = layoutMain.findViewById(R.id.fab_prev);
        fabNext = layoutMain.findViewById(R.id.fab_next);
        fabNext.setOnClickListener(this);

        fabSave = layoutMain.findViewById(R.id.fab_save);
        fabSave.setOnClickListener(this);

    }   //end initView()


    public void setupToolbar() {
        toolbar = layoutMain.findViewById(R.id.layout_toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Got Jokes...");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerLayout = layoutMain.findViewById(R.id.layout_drawer);
        mToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        navView = layoutMain.findViewById(R.id.view_nav_drawer);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();        //synchronize the indicator with state of linked DrawerLayout

        navView.setNavigationItemSelectedListener(this);
    }       //end setupToolbar()


    public void getJoke() {
        Call<Joke> call = service.getJoke(catJoke, keyWord);
        progressDialog.show();

        Log.d(TAG, "getJoke: about to get a joke...");
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                progressDialog.dismiss();

                joke = response.body();

                if (joke != null) {
                    Log.d(TAG, "onResponse: Joke: " + joke.toString());
                    display(joke);
                }

            }   //end onRequest()

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                progressDialog.dismiss();

                textCategory.setText("Something went wrong... Press Next to Refresh");
                textSetup.setText("");
                textDelivery.setText("");

                Log.d(TAG, "onFailure: Couldn't get the joke");
            }
        });

    }           //end getJoke()


    public void display(Joke joke) {
        textCategory.setText("");
        textSetup.setText("");
        textDelivery.setText("");

        if (joke.getError() == true) {
            textCategory.setText("No matching joke found...");
            textSetup.setText("");
            textDelivery.setText("");
        } else {
            if (joke.getType().equalsIgnoreCase(getString(R.string.single))) {
                textCategory.setText(joke.getCategory());
                textSetup.setText(joke.getJoke());
            } else if (joke.getType().equalsIgnoreCase(getString(R.string.twopart))) {
                textCategory.setText(joke.getCategory());
                textSetup.setText(joke.getSetup());
                textDelivery.setText(joke.getDelivery());
            }
        }

    }       //end display()


    @Override
    public void onClick(View v) {
        // clicked the save button
        if (v.getId() == R.id.fab_save) {
            progressDialog.show();

            if (joke != null) {
                saveJoke(joke);
            }

            progressDialog.dismiss();
        }

        // clicked the next button
        else if (v.getId() == R.id.fab_next) {
            getJoke();
        }

    }       //end onClick()


    public void saveJoke(final Joke joke) {
        viewModel.getCount(joke.getId()).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                // 1. check if joke is already in the database
                if (integer <= 0) {
                    Log.d(TAG, "onClick: Joke is unique... count: " + integer);

                    // 2.1 if not, then insert
                    viewModel.insert(joke);
                } else {
                    // 2.2 else, do not insert
                    Log.d(TAG, "onChanged: Joke is not unique... count: " + integer);
                }

            }       //end onChanged()

        });
    }           //end saveJoke()


    //makes the toggle menu icon clickable
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.home:
                catJoke = getString(R.string.any);
                break;

            case R.id.programming:
                catJoke = getString(R.string.programing);
                break;

            case R.id.dark:
                catJoke = getString(R.string.dark);
                break;

            case R.id.miscellaneous:
                catJoke = getString(R.string.miscellaneous);
                break;
        }       //end switch{}

        getJoke();

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

/*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyWord = query;

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        }));

        super.onCreateOptionsMenu(menu, inflater);
    }
*/

}       //end class

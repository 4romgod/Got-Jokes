package com.template.got_jokes.mvvm.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.template.got_jokes.R;
import com.template.got_jokes.UI;

public class FragHome extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "FragJokes";

    //VIEWS
    View layoutMain;
    Toolbar toolbar;

    ActionBar actionBar;
    DrawerLayout mDrawerLayout;     //container that allows drawer to be pulled out from either end
    ActionBarDrawerToggle mToggle;      //tie functionality of drawer layout to the toolbar
    NavigationView navView;

    FragmentManager fragmentManager;

    String catJoke = "";
    String contains = "";


    public Fragment newFragment(String cat, String contains){
        Fragment fragment = new FragLaugh();
        Bundle args = new Bundle();
        args.putString("JOKE_CAT", cat);
        args.putString("JOKE_CONTAINS", contains);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);        // enables toggle btn, and options menu
    }       //end onCreate()

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutMain == null){
            layoutMain = inflater.inflate(R.layout.frag_home, null);
        }
        initViews();
        setupToolbar();

        fragmentManager = getActivity().getSupportFragmentManager();

        catJoke = getString(R.string.any);
        UI.replaceFragment(fragmentManager, newFragment(catJoke, contains), R.id.layout_frame_jokes);

        return layoutMain;
    }       //end onCreateView()


    public void initViews(){
        toolbar = layoutMain.findViewById(R.id.layout_toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        mDrawerLayout = layoutMain.findViewById(R.id.layout_drawer);
        mToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        navView = layoutMain.findViewById(R.id.view_nav_drawer);
    }   //end initView()


    public void setupToolbar(){
        Log.d(TAG, "setupToolbar(): get toolbar view, create DrawerToggle, drawer listener, navViewLister");

        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();        //synchronize the indicator with state of linked DrawerLayout

        navView.setNavigationItemSelectedListener(this);
    }       //end setupToolbar()


    //makes the toggle menu icon clickable
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            //mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.home:
                catJoke = getString(R.string.any);
                Toast.makeText(getContext(), catJoke, Toast.LENGTH_SHORT).show();
                break;

            case R.id.programming:
                catJoke = getString(R.string.programing);
                Toast.makeText(getContext(), catJoke, Toast.LENGTH_SHORT).show();
                break;

            case R.id.dark:
                catJoke = getString(R.string.dark);
                Toast.makeText(getContext(), catJoke, Toast.LENGTH_SHORT).show();
                break;

            case R.id.miscellaneous:
                catJoke = getString(R.string.miscellaneous);
                Toast.makeText(getContext(), catJoke, Toast.LENGTH_SHORT).show();
                break;
        }       //end switch{}

        mDrawerLayout.closeDrawer(GravityCompat.START);
        UI.replaceFragment(fragmentManager, newFragment(catJoke, contains), R.id.layout_frame_jokes);

        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contains = query;
                UI.replaceFragment(fragmentManager, newFragment(catJoke, contains), R.id.layout_frame_jokes);
                Toast.makeText(getContext(), "Searching", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        }));



        super.onCreateOptionsMenu(menu, inflater);
    }


}       //end class

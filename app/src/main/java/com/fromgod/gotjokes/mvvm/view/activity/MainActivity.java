package com.fromgod.gotjokes.mvvm.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.fromgod.gotjokes.mvvm.view.fragment.FragAbout;
import com.fromgod.gotjokes.mvvm.view.fragment.FragSavedJoke;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.fromgod.gotjokes.mvvm.view.fragment.FragHome;
import com.fromgod.gotjokes.mvvm.view.fragment.FragSaved;
import com.fromgod.gotjokes.R;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public BottomNavigationView viewBottomNav;

    public FragmentManager fm = this.getSupportFragmentManager();
    public static final Fragment fragHome = new FragHome();
    public static final Fragment fragAbout = new FragAbout();
    public static final Fragment fragSaved = new FragSaved();
    public static Fragment fragActive = fragHome;

    Stack<String> stack = new Stack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        enableNavBottom();
    }

    @Override
    protected void onStart() {
        super.onStart();

        fm.beginTransaction().add(R.id.layout_frame_main, fragSaved, "saved").hide(fragSaved).commit();
        fm.beginTransaction().add(R.id.layout_frame_main, fragAbout, "post").hide(fragAbout).commit();
        fm.beginTransaction().add(R.id.layout_frame_main, fragHome, "home").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fm.beginTransaction().show(fragActive).commit();

        int selectedId = 0;
        if(fragActive instanceof FragHome){
            Log.d(TAG, "onResume: Home Fragment");
            selectedId = R.id.home;
        }
        else if(fragActive instanceof FragSaved){
            Log.d(TAG, "onResume: Saved Fragment");
            selectedId = R.id.saved;
        }
        else if(fragActive instanceof FragAbout){
            Log.d(TAG, "onResume: About Fragment");
            selectedId = R.id.about;
        }

        viewBottomNav.setSelectedItemId(selectedId);
    }

    public void initViews() {
        viewBottomNav = (BottomNavigationView) findViewById(R.id.view_nav_bottom);
    }

    public void enableNavBottom() {
        viewBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home: {
                        stack.push("home");
                        fm.beginTransaction().hide(fragActive).show(fragHome).commit();
                        fragActive = fragHome;
                        break;
                    }
                    case R.id.saved: {
                        stack.push("saved");
                        fm.beginTransaction().hide(fragActive).show(fragSaved).commit();
                        fragActive = fragSaved;
                        break;
                    }
                    case R.id.about: {
                        stack.push("About");
                        fm.beginTransaction().hide(fragActive).show(fragAbout).commit();
                        fragActive = fragAbout;
                        break;
                    }
                    default: {
                        Toast.makeText(MainActivity.this, "Invalid Item", Toast.LENGTH_SHORT).show();
                    }
                }

                if(fragActive instanceof FragSavedJoke) {
                    fm.beginTransaction().remove(fragActive).commit();
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(fragActive == fragHome || fragActive == fragAbout || fragActive == fragSaved) {
            finish();
        }
        else if(fragActive instanceof FragSavedJoke) {
            fm.beginTransaction().hide(MainActivity.fragActive).show(fragSaved).commit();
            fm.beginTransaction().remove(fragActive).commit();
            fragActive = fragSaved;
        }
        else {
            super.onBackPressed();
        }
    }

}

package com.fromgod.got_jokes.mvvm.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.fromgod.got_jokes.mvvm.view.fragment.FragHome;
import com.fromgod.got_jokes.mvvm.view.fragment.FragProfile;
import com.fromgod.got_jokes.mvvm.view.fragment.FragSaved;
import com.fromgod.got_jokes.R;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    //VIEWS
    BottomNavigationView viewBottomNav;

    final Fragment fragHome = new FragHome();
    final Fragment fragPost = new FragProfile();
    final Fragment fragSaved = new FragSaved();
    Fragment fragActive = fragHome;

    FragmentManager fm = this.getSupportFragmentManager();

    Stack<String> stack = new Stack();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        fm.beginTransaction().add(R.id.layout_frame_main, fragSaved, "saved").hide(fragSaved).commit();
        fm.beginTransaction().add(R.id.layout_frame_main, fragPost, "post").hide(fragPost).commit();
        fm.beginTransaction().add(R.id.layout_frame_main, fragHome, "home").commit();
        Log.d(TAG, "onCreate: "+fm.getBackStackEntryCount());

        enableNavBottom();
    }       //end onCreate()


    public void initViews() {
        viewBottomNav = (BottomNavigationView) findViewById(R.id.view_nav_bottom);
    }       //end initViews()


    public void enableNavBottom() {
        viewBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.home: {
                        stack.push("home");
                        Toast.makeText(MainActivity.this, "Jokes", Toast.LENGTH_SHORT).show();

                        fm.beginTransaction().hide(fragActive).show(fragHome).commit();
                        fragActive = fragHome;
                        break;
                    }
                    case R.id.post: {
                        stack.push("post");
                        Toast.makeText(MainActivity.this, "Post", Toast.LENGTH_SHORT).show();

                        fm.beginTransaction().hide(fragActive).show(fragPost).commit();
                        fragActive = fragPost;
                        break;
                    }
                    case R.id.saved: {
                        stack.push("saved");
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                        fm.beginTransaction().hide(fragActive).show(fragSaved).commit();
                        fragActive = fragSaved;
                        break;
                    }
                    default: {
                        Toast.makeText(MainActivity.this, "Invalid Item", Toast.LENGTH_SHORT).show();
                    }

                }       //end switch()

                Log.d(TAG, "onNavigationItemSelected: "+fm.getBackStackEntryCount());
                return true;
            }
        });

    }       //end enableNavBottom()


   @Override
    public void onBackPressed() {
       Log.d(TAG, "onBackPressed: " +fm.getBackStackEntryCount());
       if(fm.getBackStackEntryCount()==1){
           finish();
       }

        super.onBackPressed();
    }

}       //end class

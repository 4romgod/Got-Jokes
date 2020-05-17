package com.fromgod.got_jokes.mvvm.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.fromgod.got_jokes.utils.UI;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    //VIEWS
    BottomNavigationView viewBottomNav;

    FragmentManager fragmentManager = this.getSupportFragmentManager();

    Stack stack = new Stack();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        UI.replaceFragment(fragmentManager, new FragHome(), R.id.layout_frame_main);
        stack.push(0);

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
                        stack.push(0);
                        Toast.makeText(MainActivity.this, "Jokes", Toast.LENGTH_SHORT).show();
                        UI.replaceFragment(fragmentManager, new FragHome(), R.id.layout_frame_main);
                        break;
                    }
                    case R.id.post: {
                        stack.push(1);
                        Toast.makeText(MainActivity.this, "Post", Toast.LENGTH_SHORT).show();
                        UI.replaceFragment(fragmentManager, new FragProfile(), R.id.layout_frame_main);
                        break;
                    }
                    case R.id.saved: {
                        stack.push(2);
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        UI.replaceFragment(fragmentManager, new FragSaved(), R.id.layout_frame_main);
                        break;
                    }
                    default: {
                        Toast.makeText(MainActivity.this, "Invalid Item", Toast.LENGTH_SHORT).show();
                    }

                }       //end switch()

                return true;
            }
        });

    }       //end enableNavBottom()


    @Override
    public void onBackPressed() {
        if (stack.size() == 1) {
            finish();
        }
        else {
            stack.pop();
            int index = (int) stack.peek();
            viewBottomNav.getMenu().getItem(index).setChecked(true);
        }

        Log.d(TAG, "onBackPressed: size: "+stack.size());
        super.onBackPressed();
    }


}       //end class

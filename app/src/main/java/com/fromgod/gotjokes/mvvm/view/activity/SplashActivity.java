package com.fromgod.gotjokes.mvvm.view.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.fromgod.gotjokes.R;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: Splash Activity started");

        //blink();

        Thread thread = new Thread(){
            @Override
            public void run() {
                Log.d(TAG, "run: method to run the thread");
                try {
                    sleep(0);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                finally {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();
    }

    public void blink(){
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.screen);
        ObjectAnimator animator = ObjectAnimator.ofInt(layout, "backgroundColor",
                Color.rgb(82, 143, 118),
                Color.rgb(17, 128, 14),
                Color.rgb(82, 143, 118)
        );

        animator.setDuration(1000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}

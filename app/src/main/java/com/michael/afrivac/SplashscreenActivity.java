package com.michael.afrivac;

import com.michael.afrivac.sharePref.SharePref;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class SplashscreenActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME_OUT = 3000 ;



    SharePref sharePref;


    // After completion of 2000 ms, the next activity will get started.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splashscreen activity can cover the entire screen.

        setContentView(R.layout.activity_splashscreen);
        //this will bind your SplashscreenActivity.class file with activity_splash.
        sharePref = SharePref.getINSTANCE(getApplicationContext());


        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        Animation fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);

        View fadeLayoutImage = findViewById(R.id.splash_layout);
        TextView fromBottomText = findViewById(R.id.afrivac_text_view);
        ImageView logo = findViewById(R.id.logo);

        fadeLayoutImage.startAnimation(fadeIn);
        fromBottomText.startAnimation(fromBottom);
        logo.startAnimation(fromTop);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Display OnBoarding Screen for First Time user
                if (sharePref.getFirstTime()) {
                    startActivity(new Intent(getApplicationContext(), OnboardscreenActivity.class));
                } else {
                    //Display LoginActivity Screen for old user
                    Intent i = new Intent(SplashscreenActivity.this, LoginActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the MainActivity.
                }
                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
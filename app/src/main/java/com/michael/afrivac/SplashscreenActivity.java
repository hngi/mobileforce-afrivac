package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.michael.afrivac.sharePref.SharePref;

public class SplashscreenActivity extends AppCompatActivity {

    SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharePref = SharePref.getINSTANCE(getApplicationContext());


        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade__in);
        ImageView fadeInImage = findViewById(R.id.logo);
        fadeInImage.startAnimation(fadeIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharePref.getIsUserLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Intent intent = new Intent(getApplicationContext(), OnboardscreenActivity.class);

                    startActivity(intent);
                }

                finish();
            }
            //the delay time is 3s
        }, 5000);

    }

}
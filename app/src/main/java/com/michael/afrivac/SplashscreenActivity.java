package com.michael.afrivac;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.michael.afrivac.sharePref.SharePref;

public class SplashscreenActivity extends AppCompatActivity {

    SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharePref = SharePref.getINSTANCE(getApplicationContext());

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
            //the delay time is 2s
        }, 2000);
    }

}
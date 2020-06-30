package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class SplashscreenActivity extends AppCompatActivity {
    SharedPreferences onBoardPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardPreference = getSharedPreferences("onBoardScreen",MODE_PRIVATE);
                //SharePreference which works for firstTime user to display the onBoarding Screen
                boolean isFirstTime = onBoardPreference.getBoolean("firstTime",true);
                if(isFirstTime){
                    SharedPreferences.Editor editor = onBoardPreference.edit();
                    //SharePreference which works for non firstTime user to display the LoginActivity
                    editor.putBoolean("firstTime",false);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), OnboardscreenActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                finish();
            }
            //the delay time is 3s
        }, 2000);

    }

}
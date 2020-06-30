package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.michael.afrivac.sharePref.SharePref;

public class SplashscreenActivity extends AppCompatActivity {
    SharedPreferences OnBoardPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Display OnBoardScreen for firsTime User
                OnBoardPreference = getSharedPreferences("onBoardScreen",MODE_PRIVATE);
                boolean isFirstTime = OnBoardPreference.getBoolean("firstTime",true);
                if(isFirstTime){
                    SharedPreferences.Editor editor = OnBoardPreference.edit();
                    editor.putBoolean("firstTime",false);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), OnboardscreenActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //Display LoginActivity for non firsTime User
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
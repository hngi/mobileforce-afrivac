package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.michael.afrivac.sharePref.SharePref;

public class SplashscreenActivity extends AppCompatActivity {

    ShimmerFrameLayout container;
    SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        container.startShimmerAnimation();

        sharePref = SharePref.getINSTANCE(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharePref.getIsUserLoggedIn()) {
                    container.stopShimmerAnimation();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    container.stopShimmerAnimation();
                    Intent intent = new Intent(getApplicationContext(), OnboardscreenActivity.class);



                    startActivity(intent);
                }

                finish();
            }
            //the delay time is 3s
        }, 2000);

    }

}
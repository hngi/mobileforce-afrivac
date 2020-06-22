package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.michael.afrivac.Util.Helper;

public class OnboardscreenActivity extends AppCompatActivity {

    ImageView dash_1, dash_2, rightArrow, leftArrow;
    TextView number;
    ConstraintLayout layout;
    Button button;

    Helper helper;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        helper  = new Helper();

        dash_1 = findViewById(R.id.dash_1);
        dash_2 = findViewById(R.id.dash_2);
        rightArrow = findViewById(R.id.rightArrow);
        leftArrow = findViewById(R.id.leftArrow);
        number = findViewById(R.id.number);
        layout = findViewById(R.id.layout);
        button = findViewById(R.id.button);

        long delay = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    rightArrow.performClick();
            }
        }, delay );

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundResource(R.drawable.onboarding_2);
                number.setText("02");
                rightArrow.setImageResource(R.drawable.front_after);
                leftArrow.setImageResource(R.drawable.back_after);
                dash_1.setImageResource(R.drawable.dash_2);
                dash_2.setImageResource(R.drawable.dash);
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundResource(R.drawable.onboarding_1);
                number.setText("01");
                rightArrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                leftArrow.setImageResource(R.drawable.ic_baseline_arrow_back_ios_24);
                dash_1.setImageResource(R.drawable.dash);
                dash_2.setImageResource(R.drawable.dash_2);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                button.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        helper.gotoLoginAcitivity(getApplicationContext());
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        });
    }

}
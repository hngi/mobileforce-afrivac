package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OnboardscreenActivity extends AppCompatActivity {

    ImageView dash_1, dash_2, rightArrow, leftArrow;
    TextView number;
    ConstraintLayout layout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

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
                number.setText(R.string.two);
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
                number.setText(R.string.one);
                rightArrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                leftArrow.setImageResource(R.drawable.ic_baseline_arrow_back_ios_24);
                dash_1.setImageResource(R.drawable.dash);
                dash_2.setImageResource(R.drawable.dash_2);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
                startActivity(intent);
            }
        });
    }

}
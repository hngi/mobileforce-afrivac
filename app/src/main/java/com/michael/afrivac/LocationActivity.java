package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

public class LocationActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout viewPagerLayout;

    private LocationSliderAdapter mSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_location);

        mViewPager = findViewById(R.id.slideViewPager);
        viewPagerLayout = findViewById(R.id.sliderLayout);

        mSliderAdapter = new LocationSliderAdapter(this);
        mViewPager.setAdapter(mSliderAdapter);
    }
}

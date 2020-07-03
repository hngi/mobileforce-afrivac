package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class FindHotelDetails extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hotel_details);

        mToolbar = findViewById(R.id.myToolbar);
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Overview"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Reviews"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = findViewById(R.id.slideViewPager);
        FindHotelSliderAdapter hotelSliderAdapter = new FindHotelSliderAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(hotelSliderAdapter);
        mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setupWithViewPager(mViewPager, false);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    }

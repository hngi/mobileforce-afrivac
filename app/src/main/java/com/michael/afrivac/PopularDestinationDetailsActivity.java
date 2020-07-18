package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PopularDestinationDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView image, fav;
    private TextView name, ratingNumber, overviewDesc;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_destination_details);

        image = findViewById(R.id.destination_detail_image);
        name = findViewById(R.id.popularDestinationName);
        ratingNumber = findViewById(R.id.ratingValue);
        ratingBar = findViewById(R.id.MyRating);
        fav = findViewById(R.id.fav);
        overviewDesc = findViewById(R.id.txtOverview);

        Intent intent = getIntent();
        String placeName = intent.getStringExtra("name");
        String country = intent.getStringExtra("country");
        String desImage = intent.getStringExtra("image");
        double ratingsNumber = intent.getDoubleExtra("ratingNumber", 0.0);
        int reviewNumber = intent.getIntExtra("reviewNumber", 0);
        boolean favorite = intent.getBooleanExtra("favorite", false);
        int position = intent.getIntExtra("position", 0);
        String description = intent.getStringExtra("summary");

        String nameCountry = placeName + " (" + country + ")";
        name.setText(nameCountry);
        String rateNums = ratingsNumber + " (" + reviewNumber + ")";
        ratingNumber.setText(rateNums);
        Picasso.get().load(desImage).into(image);


        SharedPreferences sharedPreferencesOverview = getSharedPreferences("POSITION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorOverview = sharedPreferencesOverview.edit();
        editorOverview.putInt("position", position);
        editorOverview.commit();

        SharedPreferences sharedPreferencesReview = getSharedPreferences("POSITION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorReview = sharedPreferencesReview.edit();
        editorReview.putInt("position", position);
        editorReview.commit();

        mToolbar = findViewById(R.id.myToolbar);
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Overview"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Reviews"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = findViewById(R.id.slideViewPager);
        PopularDestinationSliderAdapter destinationSliderAdapter = new PopularDestinationSliderAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(destinationSliderAdapter);
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
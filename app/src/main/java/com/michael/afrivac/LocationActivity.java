<<<<<<< HEAD
package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class LocationActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private LocationSliderAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_location);

        mToolbar = findViewById(R.id.myToolbar);
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Overview"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Reviews"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = findViewById(R.id.slideViewPager);
        LocationSliderAdapter locationSliderAdapter = new LocationSliderAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(locationSliderAdapter);
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
=======
package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout viewDotsLayout;
    private TextView dotText;

    private TextView[] mDots;

    private LocationSliderAdapter mSliderAdapter;
    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_location);

        dotText = findViewById(R.id.dotsText);

        mViewPager = findViewById(R.id.slideViewPager);
        viewDotsLayout = findViewById(R.id.dots);

        mSliderAdapter = new LocationSliderAdapter(this);
        mViewPager.setAdapter(mSliderAdapter);

        addDotsIndicator(0);
        mViewPager.addOnPageChangeListener(viewListener);
    }

    public  void addDotsIndicator(int position){
        mDots = new TextView[2];
        viewDotsLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(40);
            mDots[i].setTextColor(getResources().getColor(R.color.colorAccent));

            viewDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
>>>>>>> e8f5b31d3c177a670b0a95a96b47aa90b7f06a79

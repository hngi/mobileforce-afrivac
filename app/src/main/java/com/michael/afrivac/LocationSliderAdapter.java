package com.michael.afrivac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class LocationSliderAdapter  extends FragmentStatePagerAdapter {

    int noOfTabs;

    public LocationSliderAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.noOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                LocationActivityOverviewFragment tab1 = new LocationActivityOverviewFragment();
                return tab1;
            case 1:
                LocationActivityReviewsFragment tab2 = new LocationActivityReviewsFragment();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Overview";
            case 1:
                return "Reviews";
            default:
                return null;
        }
    }

}


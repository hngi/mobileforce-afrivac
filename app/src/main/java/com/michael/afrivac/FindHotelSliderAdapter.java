package com.michael.afrivac;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FindHotelSliderAdapter extends FragmentStatePagerAdapter {


    int noOfTabs;

    public FindHotelSliderAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.noOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                FindHotelDetailsOverviewFragment tab1 = new FindHotelDetailsOverviewFragment();
                return tab1;
            case 1:
                PopularDestinationDetailsReviewFragment tab2 = new PopularDestinationDetailsReviewFragment();
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

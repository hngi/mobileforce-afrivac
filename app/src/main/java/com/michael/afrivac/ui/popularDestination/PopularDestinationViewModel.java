package com.michael.afrivac.ui.popularDestination;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PopularDestinationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PopularDestinationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Popular destination fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
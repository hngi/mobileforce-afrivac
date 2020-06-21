package com.michael.afrivac.ui.findHotel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindHotelViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FindHotelViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is find hotel fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
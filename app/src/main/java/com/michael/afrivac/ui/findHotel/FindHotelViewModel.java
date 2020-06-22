package com.michael.afrivac.ui.findHotel;

import android.app.Application;

import com.michael.afrivac.R;
import com.michael.afrivac.model.FindHotel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.MutableLiveData;


public class FindHotelViewModel extends AndroidViewModel {

    public List<FindHotel> mainFindHotels;
    public List<FindHotel> queryFindHotels;

    private MutableLiveData<State> state = new MutableLiveData<>();

    public FindHotelViewModel(@NonNull Application application) {
        super(application);
        this.mainFindHotels = new ArrayList<>();
        this.mainFindHotels= generateFindHotel();
        state.setValue(State.NORMAL);
    }

    public enum State {
        NORMAL,
        INTERNAL_SEARCH
    }

    public MutableLiveData<State> getState(){
        return state;
    }

    private List<FindHotel> generateFindHotel() {

        List<FindHotel> findHotel = new ArrayList<>();
        for (int i = 0; i < 25; i++) {

            findHotel.add(new FindHotel(
                    R.drawable.hotel4,
                    "Mountian Hotel",
                    "GRA, Buea",
                    R.drawable.ic_baseline_airline_seat_individual_suite_24,
                    R.drawable.ic_baseline_wifi_24,
                    R.drawable.p,
                    R.drawable.wiegts,
                    R.drawable.pool,
                    false,
                    4.4,
                    5000
            ));
        };

        return findHotel;
    }
}
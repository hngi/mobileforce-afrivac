package com.michael.afrivac.ui.popular_destination;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.michael.afrivac.model.PopularPlaces;

import java.util.ArrayList;
import java.util.List;

public class PopularDestinationViewModel extends AndroidViewModel {

    public List<PopularPlaces> initialPopularPlaces;
    public List<PopularPlaces> queryPopularPlaces;

    private MutableLiveData<State> state = new MutableLiveData<>();

    public PopularDestinationViewModel(@NonNull Application application) {
        super(application);
        this.initialPopularPlaces = new ArrayList<>();
        this.initialPopularPlaces = generatePopularPlaces();
        state.setValue(State.NORMAL);
    }



    public enum State {
        NORMAL,
        INTERNAL_SEARCH
    }

    public MutableLiveData<State> getState() {
        return state;
    }

    private List<PopularPlaces> generatePopularPlaces() {

        // Generate dummy data
        List<PopularPlaces> popularPlaces = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            popularPlaces.add(new PopularPlaces(
                    "Nigeria",
                    "Agege",
                    "The desert heat, the noisy streets and the sheer size of Cairo will leave even the most adaptable traveler with a serious case of culture shock" + "\nIt is a place of life or death",
                    "https://raw.githubusercontent.com/hngi/mobileforce-afrivac/develop/app/src/main/res/drawable/cairo.jpg",
                    false,
                    3.4,
                    2000
            ));
        };

        return popularPlaces;
    }
}
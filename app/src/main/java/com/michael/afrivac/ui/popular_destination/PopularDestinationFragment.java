package com.michael.afrivac.ui.popular_destination;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.model.DestinationSuggestion;
import com.michael.afrivac.model.PopularPlaces;

import java.util.ArrayList;
import java.util.List;

public class PopularDestinationFragment extends Fragment {
    RecyclerView popularPlacesRV;
    private AutoCompleteTextView searchTV;
    ImageButton menuButton, avatarButton;

    private PopularDestinationViewModel popularDestinationViewModel;
    private PopularDestinationRVAdapter popularDestinationRVAdapter;

    public static PopularDestinationFragment newInstance() {
        return new PopularDestinationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).getSupportActionBar().hide();

        popularDestinationViewModel = ViewModelProviders.of(this).get(PopularDestinationViewModel.class);
        View view = inflater.inflate(R.layout.fragment_popular_destination, container, false);

        popularPlacesRV = view.findViewById(R.id.popularDestinationRV);
        menuButton = view.findViewById(R.id.menuButton);
        avatarButton = view.findViewById(R.id.avatarButton);
        searchTV = view.findViewById(R.id.searchTV);

        popularDestinationRVAdapter = new PopularDestinationRVAdapter(getContext(), new PopularDestinationRVAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedPosition) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        popularPlacesRV.setAdapter(popularDestinationRVAdapter);

        setUpSearch();

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((DrawerLayout) requireActivity().findViewById(R.id.drawer_layout)).openDrawer(GravityCompat.START);
                } catch (Exception ignored) { }
            }
        });

        Glide.with(requireActivity())
                .load(R.drawable.girl2)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .circleCrop()
                .into(avatarButton);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Show profile
            }
        });


        popularDestinationViewModel.getState().observe(getViewLifecycleOwner(), new Observer<PopularDestinationViewModel.State>() {
            @Override
            public void onChanged(PopularDestinationViewModel.State state) {
                switch (state) {
                    case NORMAL:
                        popularDestinationRVAdapter.setDestinations(popularDestinationViewModel.initialPopularPlaces);
                        extractSuggestions();
                        break;
                    case INTERNAL_SEARCH:
                        popularDestinationRVAdapter.setDestinations(popularDestinationViewModel.queryPopularPlaces);
                        break;
                }
            }
        });
        return view;
    }

    private void setUpSearch() {
        searchTV.setThreshold(1);
        searchTV.setDropDownBackgroundDrawable(getActivity().getDrawable(R.drawable.grey_ring));
        searchTV.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        searchTV.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    searchTV.dismissDropDown();
                    hideKeyboardFrom(v);
                    searchInDB(searchTV.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void extractSuggestions() {

        if (popularDestinationViewModel.initialPopularPlaces.size() > 0) {

            AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(getContext(),
                    R.layout.item_autocomplete, getSuggestions());
            searchTV.setAdapter(autoCompleteAdapter);
        }
    }

    private void searchInDB(String text) {
        Toast.makeText(getContext(), text , Toast.LENGTH_SHORT).show();
    }

    private List<DestinationSuggestion> getSuggestions() {
        List<DestinationSuggestion> destinationSuggestions = new ArrayList<>();
        for (PopularPlaces popularPlaces: popularDestinationViewModel.initialPopularPlaces) {
            destinationSuggestions.add(new DestinationSuggestion(
                    R.drawable.ic_location, popularPlaces.getCountry()
            ));
            destinationSuggestions.add(new DestinationSuggestion(
                    R.drawable.ic_hotel, popularPlaces.getDestination()
            ));
        }
        return destinationSuggestions;
    }

    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
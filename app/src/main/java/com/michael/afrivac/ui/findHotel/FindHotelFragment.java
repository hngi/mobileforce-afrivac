package com.michael.afrivac.ui.findHotel;

import android.app.Activity;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.model.FindHotel;
import com.michael.afrivac.model.HotelSuggestions;


import java.util.ArrayList;
import java.util.List;

public class FindHotelFragment extends Fragment {
    RecyclerView findHotelRecycler;
    private AutoCompleteTextView searchBar;
    ImageButton sideMenu, Avatar;

    private FindHotelViewModel findHotelViewModel;
    private FindHotelRecyclerAdapter findHotelRecyclerAdapter;

    public static FindHotelFragment newInstance() {
        return new FindHotelFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).getSupportActionBar().hide();

        findHotelViewModel = ViewModelProviders.of(this).get(FindHotelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_find_hotel, container, false);

        findHotelRecycler = root.findViewById(R.id.hotelRecycler);
        searchBar = root.findViewById(R.id.searchbar);
        Avatar = root.findViewById(R.id.avatar);
        sideMenu = root.findViewById(R.id.sidebar);

        findHotelRecyclerAdapter = new FindHotelRecyclerAdapter(getContext(), new FindHotelRecyclerAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedPosition) {
                Toast.makeText(getContext(), "You clicked an item", Toast.LENGTH_SHORT).show();
            }
        });
        findHotelRecycler.setAdapter(findHotelRecyclerAdapter);
        setUpSearch();

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((DrawerLayout) requireActivity().findViewById(R.id.drawer_layout)).openDrawer(GravityCompat.START);
                } catch (Exception ignored) {
                }
            }
        });
        Glide.with(requireActivity())
                .load(R.drawable.user3)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .circleCrop()
                .into(Avatar);
        Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        findHotelViewModel.getState().observe(getViewLifecycleOwner(), new Observer<FindHotelViewModel.State>() {
            @Override
            public void onChanged(FindHotelViewModel.State state) {
                switch (state) {
                    case NORMAL:
                        findHotelRecyclerAdapter.setHotel(findHotelViewModel.mainFindHotels);
                        extractSuggestions();
                        break;
                    case INTERNAL_SEARCH:
                        findHotelRecyclerAdapter.setHotel(findHotelViewModel.queryFindHotels);
                        break;
                }
            }
        });
        return root;
    }
    private void setUpSearch() {
        searchBar.setThreshold(1);
        searchBar.setDropDownBackgroundDrawable(getActivity().getDrawable(R.drawable.grey_ring));
        searchBar.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    searchBar.dismissDropDown();
                    hideKeyboardFrom(v);
                    searchInDB(searchBar.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
    }
    private void extractSuggestions() {

        if (findHotelViewModel.mainFindHotels.size() > 0) {

            AutoCompleteHotelAdapter autoCompleteHotelAdapter = new AutoCompleteHotelAdapter(getContext(),
                    R.layout.item_autocomplete, getSuggestions());
            searchBar.setAdapter(autoCompleteHotelAdapter);
        }
    }
    private void searchInDB(String text) {
        Toast.makeText(getContext(), text , Toast.LENGTH_SHORT).show();
    }
    private List<HotelSuggestions> getSuggestions() {
        List<HotelSuggestions> hotelSuggestions = new ArrayList<>();
        for (FindHotel findHotel: findHotelViewModel.mainFindHotels) {
            hotelSuggestions.add(new HotelSuggestions(
                    R.drawable.ic_location, findHotel.getNameText1()
            ));
            hotelSuggestions.add(new HotelSuggestions(
                    R.drawable.ic_hotel, findHotel.getNameText1()
            ));
        }
        return hotelSuggestions;
    }

    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
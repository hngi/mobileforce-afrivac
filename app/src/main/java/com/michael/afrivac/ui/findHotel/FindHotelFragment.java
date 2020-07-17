package com.michael.afrivac.ui.findHotel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michael.afrivac.FindHotelDetailsReviewActivity;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.Util.UniversalImageLoader;
import com.michael.afrivac.model.FindHotel;
import com.michael.afrivac.model.HotelSuggestions;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindHotelFragment extends Fragment {
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String userID;

    RecyclerView findHotelRecycler;
    private AutoCompleteTextView searchBar;
    ImageButton sideMenu, searchEnd;
    CircleImageView avatarButton;

    private AutoCompleteTextView search;

    private FindHotelViewModel findHotelViewModel;
    private FindHotelRecyclerAdapter findHotelRecyclerAdapter;
    final  ArrayList<FindHotel>   findHotels = new ArrayList<>();

    public static FindHotelFragment newInstance() {
        return new FindHotelFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).getSupportActionBar().hide();

        initImageLoader();

        findHotelViewModel = new ViewModelProvider(this).get(FindHotelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_find_hotel, container, false);

        search = root.findViewById(R.id.searchTV);
        findHotelRecycler = root.findViewById(R.id.hotelRecycler);
        searchBar = root.findViewById(R.id.searchTV);
        avatarButton = root.findViewById(R.id.avatar);
        sideMenu = root.findViewById(R.id.sideBar);
        findHotelRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        searchEnd = root.findViewById(R.id.search_end_button);
        searchEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.clearComposingText();
            }
        });

        findHotelRecyclerAdapter = new FindHotelRecyclerAdapter(getContext(), new FindHotelRecyclerAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedPosition) {
                //Toast.makeText(getContext(), "You clicked an item", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), FindHotelDetailsReviewActivity.class));
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
//        Glide.with(requireActivity())
//                .load(R.drawable.user3)
//                .placeholder(R.drawable.ic_account_circle_black_24dp)
//                .circleCrop()
//                .into(Avatar);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String profile_picture = snapshot.child("profileImageUrl").getValue().toString();
//
                ImageLoader.getInstance().displayImage(profile_picture, avatarButton);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        findHotelViewModel.getState().observe(getViewLifecycleOwner(), new Observer<FindHotelViewModel.State>() {
            @Override
            public void onChanged(FindHotelViewModel.State state) {
                switch (state) {
                    case NORMAL:
                        findHotelRecyclerAdapter.setHotel(findHotels);
                        findHotelRecyclerAdapter.setHotel(findHotelViewModel.initialfindHotels);
                        extractSuggestions();
                        break;
                    case INTERNAL_SEARCH:
                        findHotelRecyclerAdapter.setHotel(findHotelViewModel.queryfindHotels);
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

        if (findHotelViewModel.initialfindHotels.size() > 0) {

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
        for (FindHotel findHotel: findHotelViewModel.initialfindHotels) {
            hotelSuggestions.add(new HotelSuggestions(
                    R.drawable.ic_location, findHotel.getHotelName()
            ));
            hotelSuggestions.add(new HotelSuggestions(
                    R.drawable.ic_hotel, findHotel.getHotelName()
            ));
        }
        return hotelSuggestions;
    }

    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * init universal image loader
     */
    private void initImageLoader(){
        UniversalImageLoader imageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(imageLoader.getConfig());
    }

}

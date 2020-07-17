package com.michael.afrivac.ui.popular_destination;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.PopularDestinationDetailsActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.Util.UniversalImageLoader;
import com.michael.afrivac.model.DestinationSuggestion;
import com.michael.afrivac.model.PopularPlaces;
import com.michael.afrivac.ui.home.Popular;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PopularDestinationFragment extends Fragment {
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String userID;

    RecyclerView popularPlacesRV;
    private AutoCompleteTextView searchTV;
    ImageButton menuButton, search_end_button;
    CircleImageView avatarButton;

    private PopularDestinationViewModel popularDestinationViewModel;
    private PopularDestinationRVAdapter popularDestinationRVAdapter;
    final  ArrayList<PopularPlaces>   popularPlaces= new ArrayList<>();



    public static PopularDestinationFragment newInstance() {
        return new PopularDestinationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).getSupportActionBar().hide();

        initImageLoader();

        popularDestinationViewModel = ViewModelProviders.of(this).get(PopularDestinationViewModel.class);
        View view = inflater.inflate(R.layout.fragment_popular_destination, container, false);

        popularPlacesRV = view.findViewById(R.id.popularDestinationRV);
        menuButton = view.findViewById(R.id.menuButton);
        avatarButton = view.findViewById(R.id.avatarButton);
        search_end_button = view.findViewById(R.id.search_end_button);
        searchTV = view.findViewById(R.id.searchTV);
        popularPlacesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        popularDestinationRVAdapter = new PopularDestinationRVAdapter(getContext(), new PopularDestinationRVAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedPosition) {
                Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
                startActivity(intent);
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

//        Glide.with(requireActivity())
//                .load(R.drawable.girl2)
//                .placeholder(R.drawable.ic_account_circle_black_24dp)
//                .circleCrop()
//                .into(avatarButton);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mAuth = FirebaseAuth.getInstance();
       // userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
       // mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        //code to display users image in the screen and greet either good morningor afternoon or night
      /*  mDatabase.addValueEventListener(new ValueEventListener() {
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
        });      */


        popularDestinationViewModel.getState().observe(getViewLifecycleOwner(), new Observer<PopularDestinationViewModel.State>() {
            @Override
            public void onChanged(PopularDestinationViewModel.State state) {
                switch (state) {
                    case NORMAL:
                        popularDestinationRVAdapter.setDestinations(popularPlaces);
                        popularDestinationRVAdapter.setDestinations(popularDestinationViewModel.initialPopularPlaces);
                        extractSuggestions();
                        break;
                    case INTERNAL_SEARCH:
                        popularDestinationRVAdapter.setDestinations(popularDestinationViewModel.queryPopularPlaces);
                        break;
                }
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (popularDestinationViewModel.getState().getValue()
                                != PopularDestinationViewModel.State.NORMAL) {
                            popularDestinationViewModel.getState().setValue(PopularDestinationViewModel.State.NORMAL);
                        } else {
                            NavHostFragment.findNavController(PopularDestinationFragment.this).popBackStack();
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
                    v.clearFocus();
                    searchInDB(searchTV.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
        final View.OnClickListener searchClearer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTV.setText(null);
                popularDestinationRVAdapter.defaultData();
            }
        };
        searchTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                popularPlacesRV.setAdapter(popularDestinationRVAdapter);


            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable == null || editable.toString() == null || editable.toString().equals("")) {
                    search_end_button.setImageResource(R.drawable.ic_search);
                    search_end_button.setOnClickListener(null);
                    popularDestinationRVAdapter.defaultData();
                    popularPlacesRV.setAdapter(new PopularDestinationRVAdapter(getContext(), new PopularDestinationRVAdapter.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int selectedPosition) {
                            Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
                            startActivity(intent);
                        }
                    }));

                } else {
                    popularDestinationRVAdapter.defaultData();
                    search_end_button.setImageResource(R.drawable.ic_round_highlight_off_24);
                    popularDestinationRVAdapter.filter(editable.toString());

                   search_end_button.setOnClickListener(searchClearer);
                }

               // filter(editable.toString());
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
                    R.drawable.ic_hotel, popularPlaces.getName()
            ));
        }
        return destinationSuggestions;
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
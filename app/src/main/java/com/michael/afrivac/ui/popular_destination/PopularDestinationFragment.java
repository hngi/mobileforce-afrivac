package com.michael.afrivac.ui.popular_destination;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.PopularDestinationDetailsActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.model.DestinationSuggestion;
import com.michael.afrivac.model.PopularPlaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularDestinationFragment extends Fragment {
    RecyclerView popularPlacesRV;
    private AutoCompleteTextView searchTV;
    ImageButton menuButton, avatarButton, search_end_button;

    private PopularDestinationViewModel popularDestinationViewModel;
    private PopularDestinationRVAdapter popularDestinationRVAdapter;
    final  ArrayList<PopularPlaces>   popularPlaces= new ArrayList<>();
    final ArrayList<PopularPlaces> places = new ArrayList<>();

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
        search_end_button = view.findViewById(R.id.search_end_button);
        searchTV = view.findViewById(R.id.searchTV);
        popularPlacesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        popularDestinationRVAdapter = new PopularDestinationRVAdapter(getContext(), new PopularDestinationRVAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final int selectedPosition) {

                String url = "https://piscine-mandarine-32869.herokuapp.com/api/v1/destinations/";
                final ArrayList<Object> arrayList = new ArrayList<>();
                final String[] description = new String[1];
                final String[] name = new String[1];
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonResponse = response.getJSONObject("data");
                            JSONArray popularDestinationJsonArray = jsonResponse.getJSONArray("destination");

                            for (int popItem = 0; popItem < popularDestinationJsonArray.length(); popItem++) {

                                JSONObject popularDestinationObject = popularDestinationJsonArray.getJSONObject(popItem);

                                String destination = popularDestinationObject.getString("name");
                                name[0] = destination;
                                String country = popularDestinationObject.getString("country");
                                String summary = popularDestinationObject.getString("summary");
                                String image = popularDestinationObject.getString("imageCover");
                                double ratingNumber = popularDestinationObject.getDouble("ratingsAverage");
                                int reviewNumber = popularDestinationObject.getInt("ratingsQuantity");
                                boolean isFav = false;
                                description[0] = popularDestinationObject.getString("description");

                                PopularPlaces placeDetailItems = new PopularPlaces(country, destination, summary, image, isFav, ratingNumber, reviewNumber);
                                places.add(placeDetailItems);
                            }
                            //Bundle extraBundle = new Bundle();
                            for (int item = 0; item < places.size()-1; item++){
                                if (item == selectedPosition){
                                    try {
                                        Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
                                        Log.d("aaa", " " + places.get(selectedPosition).getImage());
                                        intent.putExtra("name", name[0]);
                                        intent.putExtra("country", places.get(selectedPosition).getCountry());
                                        intent.putExtra("image", places.get(selectedPosition).getImage());
                                        intent.putExtra("ratingNumber", places.get(selectedPosition).getRating_number());
                                        intent.putExtra("reviewNumber", places.get(selectedPosition).getReview_number());
                                        intent.putExtra("favorite", places.get(selectedPosition).isFavorite());
                                        intent.putExtra("position", selectedPosition);
                                        intent.putExtra("description", description[0]);
                                      //  intent.putExtra("photos", arrayList);
                                        startActivity(intent);
                                        break;
                                    } catch (Exception e){
                                        Log.e("error", e.getMessage());
                                    }
                                }
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmMGU4Yjc3ZmQ3NDc2MDAxN2IzNzRhNSIsImlhdCI6MTU5NDkwMzU4OCwiZXhwIjoxNTk1MTYyNzg4fQ.Ux3HFg9eeYNGE79sB2mC9xVggnAE9m9EHYwh5t4jlMU");
                        return headers;
                    }};

                Volley.newRequestQueue(getContext()).add(jsonRequest);

//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//                Gson gson = new Gson();
//                String json = sharedPreferences.getString("Destinations", null);
//                ArrayList<PopularPlaces> popularPlacesArr = new ArrayList<>();
//                Type type = new TypeToken<ArrayList<PopularPlaces>>() {}.getType();
//                popularPlacesArr = gson.fromJson(json, type);
//
//                try{
//                    Log.d("pass", "test " + popularPlacesArr.get();
//                }catch (Exception e){
//                    Log.e("fail", e.getMessage());
//                }
//
////                for (PopularPlaces places : popularPlacesArr ){
////                        if (selectedPosition == popularPlacesArr.indexOf(places)){
////                            Log.d("Log1", popularPlacesArr.get(selectedPosition).getCountry());
////                        }
////                    }
//
//                try {
//                    Log.d("LOGPDF", String.valueOf(popularPlacesArr));
//                }catch (Exception e){
//                    Log.e("LOGPDFE", e.getMessage());
//                }
//
//                Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
//                intent.putExtra("RESPONSE", popularPlacesArr);
//                startActivity(intent);
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
            }
        });


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
}
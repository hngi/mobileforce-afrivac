package com.michael.afrivac;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationActivityReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationActivityReviewsFragment extends Fragment {

    private RatingBar mRatingBar;
    private TextView ratingText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LocationActivityReviewsFragment() {




        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationActivityReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationActivityReviewsFragment newInstance(String param1, String param2) {
        LocationActivityReviewsFragment fragment = new LocationActivityReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_activity_reviews, container, false);
        // Inflate the layout for this fragment

        addListenerOnRatingBar(view);

       return inflater.inflate(R.layout.fragment_location_activity_reviews, container, false);

    }
    private void addListenerOnRatingBar(View v){
        mRatingBar = (RatingBar) v.findViewById(R.id.ratingBar2);
        ratingText = (TextView) v.findViewById(R.id.displayRating);




        //if rating is changed value in the result
        // display the current rating value in the (textView) automatically
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText(String.valueOf(rating));


            }

        });

        Button mRatingBarButton = (Button) v.findViewById(R.id.addReview);

        mRatingBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Review added", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
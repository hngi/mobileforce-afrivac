package com.michael.afrivac;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PopularDestinationDetailsReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularDestinationDetailsReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PopularDestinationReviewsRecyclerviewAdapter adapter;
    private Button btnSubmitReview, btnAddReview;

    EditText describe, tell;
    RatingBar rating;

    String DescribeHolder, TellHolder;
    float RatingHolder;
    Firebase firebase;
    DatabaseReference databaseReference;
    // Declaring String variable ( In which we are storing firebase server URL ).
    public static final String Firebase_Server_URL = "https://afrivac-a061e.firebaseio.com/";
    public static final String Database_Path = "Review_Details_Database";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PopularDestinationDetailsReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularDestinationDetailsReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularDestinationDetailsReviewFragment newInstance(String param1, String param2) {
        PopularDestinationDetailsReviewFragment fragment = new PopularDestinationDetailsReviewFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_popular_destination_details_review, container, false);

        try {

            final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("POSITION", Context.MODE_PRIVATE);
            String url = "https://piscine-mandarine-32869.herokuapp.com/api/v1/destinations/";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject jsonResponse = response.getJSONObject("data");
                        JSONArray popularDestinationJsonArray = jsonResponse.getJSONArray("destination");
                        for (int pos = 0; pos < popularDestinationJsonArray.length(); pos++){
                            JSONObject popularDestinationObject = popularDestinationJsonArray.getJSONObject(pos);
                            if (sharedPreferences.getInt("position", 0) == pos){
                                TextView ratingsAverage = view.findViewById(R.id.displayRating);
                                double ratingsA = popularDestinationObject.getDouble("ratingsAverage");
                                String ratingsAConv = "" + ratingsA;
                                ratingsAverage.setText(ratingsAConv);


                                TextView ratingsQuantity = view.findViewById(R.id.ratingsQuantity);
                                int ratingsQ =  popularDestinationObject.getInt("ratingsQuantity");
                                String ratingsQConv = "(" + ratingsQ + ")";
                                ratingsQuantity.setText(ratingsQConv);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
        }catch (Exception e){
            Log.e("error", "ERROR: " + e.getMessage());
        }

        recyclerView = view.findViewById(R.id.userRecyclerViewReview);
        btnAddReview = view.findViewById(R.id.addReview);
        btnSubmitReview =view.findViewById(R.id.submitReview);

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new PopularDestinationReviewsRecyclerviewAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.addUserReviewInfo(new UserReviewDetails("Uzumaki Naruto", "12/12/12", "This is great", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        adapter.addUserReviewInfo(new UserReviewDetails("Uchia Sasuke", "12/12/12", "Amazing Hotel", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        adapter.addUserReviewInfo(new UserReviewDetails("Monkey D Luffy", "12/12/12", "Cool", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
        adapter.addUserReviewInfo(new UserReviewDetails("Zoro", "12/12/12", "Fantabulous", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout submitReviewCons = view.findViewById(R.id.submit_review_cons);
                submitReviewCons.setVisibility(View.VISIBLE);
                btnAddReview.setVisibility(View.GONE);
            }
        });

        Firebase.setAndroidContext(PopularDestinationDetailsReviewFragment.this.getContext());
        firebase = new Firebase(Firebase_Server_URL);
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        describe = (EditText)view.findViewById(R.id.edtDesc);
         tell = (EditText)view.findViewById(R.id.edtTell);
         rating = (RatingBar)view.findViewById(R.id.ratingsStar);

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            ReviewDetails reviewDetails = new ReviewDetails();

                GetDataFromEditText();

                reviewDetails.setDescribe(DescribeHolder);
                reviewDetails.setTell(TellHolder);
                reviewDetails.setRating(RatingHolder);

                // Getting the ID from firebase database.
                String ReviewRecordIDFromServer = databaseReference.push().getKey();

                // Adding the both name and number values using student details class object using ID.
                databaseReference.child(ReviewRecordIDFromServer).setValue(reviewDetails);

                // Showing Toast message after successfully data submit.
                Toast.makeText(PopularDestinationDetailsReviewFragment.this.getActivity(),"Review successfully submited", Toast.LENGTH_LONG).show();

                ConstraintLayout submitReviewCons = view.findViewById(R.id.submit_review_cons);
                submitReviewCons.setVisibility(View.GONE);
                btnAddReview.setVisibility(View.VISIBLE);
            }
        });
    return view;
    }
    public void GetDataFromEditText() {

        DescribeHolder = describe.getText().toString().trim();

        TellHolder = tell.getText().toString().trim();

        RatingHolder = rating.getRating();
    }
}
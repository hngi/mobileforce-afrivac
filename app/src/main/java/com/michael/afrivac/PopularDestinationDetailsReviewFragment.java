package com.michael.afrivac;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.Util.Helper;

import org.w3c.dom.Text;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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
    private Helper helper;

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

        recyclerView = view.findViewById(R.id.userRecyclerViewReview);
        btnAddReview = view.findViewById(R.id.addReview);
        btnSubmitReview =view.findViewById(R.id.submitReview);

        helper = new Helper();

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new PopularDestinationReviewsRecyclerviewAdapter(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

       // adapter.addUserReviewInfo(new UserReviewDetails("Uzumaki Naruto", "12/12/12", "This is great", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
       // adapter.addUserReviewInfo(new UserReviewDetails("Uchia Sasuke", "12/12/12", "Amazing Hotel", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
       // adapter.addUserReviewInfo(new UserReviewDetails("Monkey D Luffy", "12/12/12", "Cool", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
       // adapter.addUserReviewInfo(new UserReviewDetails("Zoro", "12/12/12", "Fantabulous", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

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

                String Review = describe.getText().toString();
                Float R = rating.getRating();
                String Rating = String.valueOf(R);


                PostReviews postReviews = new PostReviews();
                postReviews.execute(Review, Rating);

         /*   ReviewDetails reviewDetails = new ReviewDetails();

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
                btnAddReview.setVisibility(View.VISIBLE);    */


            }
        });
    return view;
    }
    public void GetDataFromEditText() {

        DescribeHolder = describe.getText().toString().trim();

        TellHolder = tell.getText().toString().trim();

        RatingHolder = rating.getRating();
    }

    //code to send review to the API db
    public class PostReviews extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            String token = helper.getToken();
            String Review = strings[0];
            String Rating = strings[1];

            RequestBody requestDetails = new FormBody.Builder()
                    .add("review", Review)
                    .add("rating", Rating)
                  //  .add("destination", PhoneNumber)  //update this once popular dest is complete
                    .build();

            Request request = new Request.Builder()
                    .url("https://piscine-mandarine-32869.herokuapp.com/api/v1/reviews/")
                    .header("Authorization", "Authirization: Bearer " + token)
                    .post(requestDetails)
                    .build();

            return null;
        }
    }

}
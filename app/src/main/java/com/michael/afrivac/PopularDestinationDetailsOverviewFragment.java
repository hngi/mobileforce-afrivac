package com.michael.afrivac;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PopularDestinationDetailsOverviewFragment extends Fragment {

    private RecyclerView recyclerView, recreationRV;
    private LinearLayoutManager layoutManager, mLinearLayoutManager;
    private PopularDestinationDetailsHotelsAdapter hoteladapter;
    private PopularDestinationDetailsRecreationCentersAdapter mPopularDestinationDetailsRecreationCentersAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PopularDestinationDetailsOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularDestinationDetailsOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularDestinationDetailsOverviewFragment newInstance(String param1, String param2) {
        PopularDestinationDetailsOverviewFragment fragment = new PopularDestinationDetailsOverviewFragment();
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

        final View view = inflater.inflate(R.layout.fragment_popular_destination_details_overview, container, false);

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
                                TextView details = view.findViewById(R.id.txtOverview);
                                details.setText(popularDestinationObject.getString("description"));
                                ImageView photo1 = view.findViewById(R.id.photo1);
                                ImageView photo2 = view.findViewById(R.id.photo2);
                                ImageView photo3 = view.findViewById(R.id.photo3);

                                try {
                                    String imgURL1 = popularDestinationObject.getJSONArray("images").getString(0);
                                    String imgURL2 = popularDestinationObject.getJSONArray("images").getString(1);
                                    String imgURL3 = popularDestinationObject.getJSONArray("images").getString(2);

                                    Picasso.get().load(imgURL1).transform(new RoundedCornersTransform()).into(photo1);
                                    Picasso.get().load(imgURL2).transform(new RoundedCornersTransform()).into(photo2);
                                    Picasso.get().load(imgURL3).transform(new RoundedCornersTransform()).into(photo3);

                                }catch (Exception e){
                                    Log.e("PhotosErr", "ERR: " + e.getMessage());
                                }
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

        recyclerView = view.findViewById(R.id.hotelsItem);
        recreationRV = view.findViewById(R.id.whatToDoRV);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLinearLayoutManager = new LinearLayoutManager(getContext());

        hoteladapter = new PopularDestinationDetailsHotelsAdapter();
        mPopularDestinationDetailsRecreationCentersAdapter = new PopularDestinationDetailsRecreationCentersAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recreationRV.setLayoutManager(mLinearLayoutManager);

        recyclerView.setAdapter(hoteladapter);
        recreationRV.setAdapter(mPopularDestinationDetailsRecreationCentersAdapter);

        return view;
    }
}
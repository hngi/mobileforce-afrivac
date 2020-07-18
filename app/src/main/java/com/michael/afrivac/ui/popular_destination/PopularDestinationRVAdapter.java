package com.michael.afrivac.ui.popular_destination;

import android.content.Context;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.gson.Gson;
import com.michael.afrivac.R;
import com.michael.afrivac.model.PopularPlaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

class PopularDestinationRVAdapter extends RecyclerView.Adapter<PopularDestinationRVAdapter.PopularPlacesRVAdapterVH> {
    private PopularDestinationRVAdapter.OnItemSelectedListener onItemSelectedListener;
    private List<PopularPlaces> popularPlaces=new ArrayList<>();
    ArrayList<PopularPlaces> temp;

    private Context context;
    private ChildEventListener mChildListener;
    //private FirebaseDatabase mFirebaseDatabase;
    //private DatabaseReference mDatabaseReference;
    //private ChildEventListener mChildListener;


    public PopularDestinationRVAdapter(final Context context, OnItemSelectedListener onItemSelectedListener) {
        temp= (ArrayList<PopularPlaces>) popularPlaces;
        this.context = context;
        this.onItemSelectedListener = onItemSelectedListener;

        SharedPreferences sharedP = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        final String token = sharedP.getString("token", "Token");

        String url = "https://piscine-mandarine-32869.herokuapp.com/api/v1/destinations/";
        //RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("data");
                    JSONArray popularDestinationJsonArray = jsonResponse.getJSONArray("selectedProperties");

                    for (int pop = 0; pop < popularDestinationJsonArray.length(); pop++) {

                       JSONObject popularDestinationObject = popularDestinationJsonArray.getJSONObject(pop);

                        String destination = popularDestinationObject.getString("name");
                        String country = popularDestinationObject.getString("country");
                        String summary = popularDestinationObject.getString("summary");
                        String image = popularDestinationObject.getString("imageCover");
                        double ratingNumber = popularDestinationObject.getDouble("ratingsAverage");
                        int reviewNumber = popularDestinationObject.getInt("ratingsQuantity");
                        boolean isFav = false;

                        PopularPlaces placeDetailItems = new PopularPlaces(country, destination, summary, image, isFav, ratingNumber, reviewNumber);
                        popularPlaces.add(placeDetailItems);
                        notifyItemInserted(popularPlaces.size() - 1);

                        try {
                            Log.d("RECYCLER", "" + response.get("data"));
                        } catch (JSONException ex) {
                            Log.e("errorRv2", "NO SHIT");
                        }
                    }
                } catch (JSONException e){
                    try {
                        Log.d("RECYCLER", e.getMessage() + "  " + response.get("data"));
                    } catch (JSONException ex) {
                        Log.e("errorRv2", e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(jsonRequest);
    }


    public void setDestinations(List<PopularPlaces> popularPlaces) {
        //this.popularPlaces = popularPlaces;
       // this.popularPlaces = FirebaseUtil.sPopularPlaces;
       // notifyDataSetChanged();
    }

    public void defaultData(){
             popularPlaces=temp;
            notifyDataSetChanged();
    }

    public void filter(String text) {
        ArrayList<PopularPlaces> filteredList = new ArrayList<>();
        for (PopularPlaces item : popularPlaces) {
            if (item.getCountry().toLowerCase().contains(text.toLowerCase())||
                    item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        popularPlaces = filteredList;
        notifyDataSetChanged();
    }

    class PopularPlacesRVAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView country, destination, description, ratingNumber, engagement;
        ImageView image;
        ImageButton favoriteIcon;
        RatingBar ratingBar;

        public PopularPlacesRVAdapterVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
            destination = itemView.findViewById(R.id.destination);
            country = itemView.findViewById(R.id.country);
            description = itemView.findViewById(R.id.description);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ratingNumber = itemView.findViewById(R.id.ratingNumber);
            engagement = itemView.findViewById(R.id.engagement);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemSelectedListener.onItemSelected(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public PopularPlacesRVAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_destination, parent, false);
        return new PopularPlacesRVAdapterVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPlacesRVAdapterVH holder, final int position) {
        final PopularPlaces current = popularPlaces.get(position);
        Glide.with(context)
                .load(current.getImage())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(holder.image);
        holder.destination.setText(current.getName());
        holder.country.setText(current.getCountry());
        holder.description.setText(current.getDescription());

        if (current.getRating_number() < 5.1) {
            holder.ratingBar.setRating((float) current.getRating_number());
            holder.ratingNumber.setText(String.valueOf(
                    new BigDecimal(current.getRating_number()).setScale(2, RoundingMode.HALF_EVEN).doubleValue()));
        }

        holder.engagement.setText(String.format("(%s)", current.getReview_number()));

        int resId = current.isFavorite() ?
                R.drawable.ic_baseline_favorite_24 :
                R.drawable.ic_favorite_border_black_24dp;

        holder.favoriteIcon.setImageResource(resId);
        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current.isFavorite()) {
                    current.setFavorite(false);
                } else {
                    current.setFavorite(true);
                }
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularPlaces == null ? 0 : popularPlaces.size();
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int selectedPosition);
    }
}

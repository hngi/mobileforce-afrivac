package com.michael.afrivac.ui.popular_destination;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.R;
import com.michael.afrivac.Util.FirebaseUtil;
import com.michael.afrivac.model.PopularPlaces;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class PopularDestinationRVAdapter extends RecyclerView.Adapter<PopularDestinationRVAdapter.PopularPlacesRVAdapterVH> {
    private PopularDestinationRVAdapter.OnItemSelectedListener onItemSelectedListener;
    private List<PopularPlaces> popularPlaces=new ArrayList<>();
    ArrayList<PopularPlaces> temp = new ArrayList<>();

    private Context context;
    //private FirebaseDatabase mFirebaseDatabase;
    //private DatabaseReference mDatabaseReference;
    //private ChildEventListener mChildListener;


;

    public PopularDestinationRVAdapter(Context context, OnItemSelectedListener onItemSelectedListener) {
        temp= (ArrayList<PopularPlaces>) popularPlaces;
        this.context = context;
        this.onItemSelectedListener = onItemSelectedListener;
        //mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference("popular_destinatio");
        //this.popularPlaces = FirebaseUtil.sPopularPlaces;
               //FirebaseUtil.openFbReference("popular_destinatio");

        //code to load the popular destination child from the firebase database
      /*  mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String country=dataSnapshot.child("country").getValue().toString();
                String destination=dataSnapshot.child("name").getValue().toString();
                String description=dataSnapshot.child("description").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                double rate= Double.parseDouble(dataSnapshot.child("rating_number").getValue().toString());
                int engagement= Integer.parseInt(dataSnapshot.child("review_number").getValue().toString());

                boolean isFavourite=false;


                PopularPlaces td = new PopularPlaces(country,
                        destination,
                        description,image,
                        isFavourite,rate,engagement);



                popularPlaces.add(td);

                notifyItemInserted(popularPlaces.size()-1);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {




            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });    */

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

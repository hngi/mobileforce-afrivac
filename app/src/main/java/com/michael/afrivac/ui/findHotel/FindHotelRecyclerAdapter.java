package com.michael.afrivac.ui.findHotel;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.FindHotelDetailsReviewActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.Util.FirebaseUtil;
import com.michael.afrivac.model.FindHotel;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import static androidx.core.content.ContextCompat.startActivity;

public class FindHotelRecyclerAdapter extends RecyclerView.Adapter<FindHotelRecyclerAdapter.FindHotelHolder> {
    private OnItemSelectedListener onItemSelectedListener;
    private List<FindHotel> findHotel = new ArrayList<>();
    private Context context;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;

    public interface OnItemSelectedListener {
        void onItemSelected(int selectedPosition);
    }

    public FindHotelRecyclerAdapter(Context context, OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.onItemSelectedListener = onItemSelectedListener;
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("find_hotels");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {



                String district = Objects.requireNonNull(dataSnapshot.child("district").getValue()).toString();
                String  name = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                String town = Objects.requireNonNull(dataSnapshot.child("town").getValue()).toString();
                String image = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                String icon1 = null;
                int view_number= Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("view_number").getValue()).toString());
                double rating_number = Double.parseDouble(Objects.requireNonNull(dataSnapshot.child("rating_number").getValue()).toString());
                boolean isFavourite=false;


                FindHotel fHotelFb = new FindHotel(district, image, name, town, icon1, view_number, isFavourite, rating_number);

                findHotel.add(fHotelFb);

                notifyItemInserted(findHotel.size()-1);

            }

            // @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            //Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            // @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            // @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setHotel(List<FindHotel> findHotel) {
        this.findHotel = findHotel;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FindHotelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hotel, parent, false);
        return new FindHotelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindHotelHolder holder, final int position) {
        final FindHotel current = findHotel.get(position);
        Glide.with(context)
                .load(current.getImage())
                .placeholder(R.drawable.hotel)
                .into(holder.image);

        holder.district.setText(current.getDistrict());
        holder.name.setText(current.getHotelName());
        holder.town.setText(current.getTown());

        if (current.getRating() < 5.1) {
            holder.ratingBar.setRating((float) current.getRating());
            holder.ratingNumber.setText(String.valueOf(
                    BigDecimal.valueOf(current.getRating()).setScale(2, RoundingMode.HALF_EVEN).doubleValue()));
        }

        holder.viewsNumber.setText(String.format("(%s)", current.getViewNumber()));

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,  FindHotelDetailsReviewActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return findHotel == null ? 0 : findHotel.size();
    }



    class FindHotelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView  name, district, town, ratingNumber, viewsNumber;
        ImageView image, icon1;
        ImageButton favoriteIcon;
        RatingBar ratingBar;
        public FindHotelHolder(View view) {
            super(view);
            name = view.findViewById(R.id.hotel_name_tv);
            district = view.findViewById(R.id.nameTxt2);
            town = view.findViewById(R.id.town_tv);
            viewsNumber = view.findViewById(R.id.numberRate);


            //imageView and Button
            image = view.findViewById(R.id.hotelImage);
            favoriteIcon = view.findViewById(R.id.favoriteIcon);
            icon1 =view.findViewById(R.id.icon1);
            //RatingBar and Number
            ratingBar = view.findViewById(R.id.ratingBar);
            ratingNumber = view.findViewById(R.id.ratingNumber);

        }

        @Override
        public void onClick(View view) {
            onItemSelectedListener.onItemSelected(getAdapterPosition());
        }
    }
}
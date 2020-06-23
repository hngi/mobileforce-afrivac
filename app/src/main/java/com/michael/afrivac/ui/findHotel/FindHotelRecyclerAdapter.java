package com.michael.afrivac.ui.findHotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.michael.afrivac.R;
import com.michael.afrivac.model.FindHotel;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FindHotelRecyclerAdapter extends RecyclerView.Adapter<FindHotelRecyclerAdapter.FindHotelRecyclerAdapterVH> {

    private FindHotelRecyclerAdapter.OnItemSelectedListener onItemSelectedListener;
    private List<FindHotel> findHotel;
    private Context mContext;

    public FindHotelRecyclerAdapter(Context mContext, FindHotelRecyclerAdapter.OnItemSelectedListener onItemSelectedListener) {
        this.mContext = mContext;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void setHotel(List<FindHotel> findHotel) {
        this.findHotel = findHotel;
        notifyDataSetChanged();
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int selectedPosition);
    }

    class FindHotelRecyclerAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameText1, nameText2;
        ImageView hotelImage;
        ImageView icon1, icon2, icon3, icon4, icon5;
        ImageButton favoriteIcon;
        RatingBar ratingBar;
        TextView numberRate;

        public FindHotelRecyclerAdapterVH(@NonNull View itemViews) {
            super(itemViews);
            hotelImage = itemViews.findViewById(R.id.hotelImage);
            favoriteIcon = itemViews.findViewById(R.id.favoriteIcon);
            nameText1 = itemViews.findViewById(R.id.nameTxt);
            nameText2 = itemViews.findViewById(R.id.nameTxt2);
            icon1 = itemViews.findViewById(R.id.icon1);
            icon2 = itemViews.findViewById(R.id.icon2);
            icon3 = itemViews.findViewById(R.id.icon3);
            icon4 = itemViews.findViewById(R.id.icon4);
            icon5 = itemViews.findViewById(R.id.icon5);
            ratingBar = itemViews.findViewById(R.id.ratingBar);
            numberRate = itemViews.findViewById(R.id.numberRate);
            itemViews.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemSelectedListener.onItemSelected(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public FindHotelRecyclerAdapter.FindHotelRecyclerAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemViews = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hotel, parent, false);
        return new FindHotelRecyclerAdapter.FindHotelRecyclerAdapterVH(itemViews);
    }

    @Override
    public void onBindViewHolder(@NonNull FindHotelRecyclerAdapterVH holder, final int position) {
        final FindHotel current = findHotel.get(position);
        Glide.with(mContext)
                .load(current.getHotelImage())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(holder.hotelImage);
        holder.nameText1.setText(current.getNameText1());
        holder.nameText2.setText(current.getNameText2());

        if (current.getRating() < 5.1) {
            holder.ratingBar.setRating((float) current.getRating());
            holder.numberRate.setText(String.valueOf(
                    new BigDecimal(current.getRating()).setScale(2, RoundingMode.HALF_EVEN).doubleValue()));
        }
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
        return findHotel == null ? 0: findHotel.size();
    }
}


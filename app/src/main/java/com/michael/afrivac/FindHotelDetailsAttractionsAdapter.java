package com.michael.afrivac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FindHotelDetailsAttractionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_hotel_details_attractions_recyclerview_item, parent, false);
       AttractionViewHolder attractionViewHolder = new AttractionViewHolder(view);
        return attractionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class AttractionViewHolder extends RecyclerView.ViewHolder{


        public AttractionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

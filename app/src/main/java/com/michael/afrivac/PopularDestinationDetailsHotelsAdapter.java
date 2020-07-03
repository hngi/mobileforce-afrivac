package com.michael.afrivac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularDestinationDetailsHotelsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_destination_recyclerview_hotels_item, parent, false);
        HotelsViewHolder hotelsViewHolder = new HotelsViewHolder(view);
                return hotelsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class HotelsViewHolder extends RecyclerView.ViewHolder{


        public HotelsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

package com.michael.afrivac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularDestinationDetailsRecreationCentersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_destination_details_recreation_recyclerview_item, parent, false);
        RecreationViewHolder recreationViewHolder = new RecreationViewHolder(view);
        return recreationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class RecreationViewHolder extends RecyclerView.ViewHolder{


        public RecreationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
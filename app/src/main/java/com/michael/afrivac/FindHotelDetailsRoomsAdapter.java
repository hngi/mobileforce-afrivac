package com.michael.afrivac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FindHotelDetailsRoomsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_hotels_recyclerview_rooms_item, parent, false);
        RoomsViewHolder roomsViewHolder = new RoomsViewHolder(view);
        return roomsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class RoomsViewHolder extends RecyclerView.ViewHolder{


        public RoomsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

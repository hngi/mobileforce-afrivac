package com.michael.afrivac.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.afrivac.R;

public class ViewRoomsAdapter extends RecyclerView.Adapter<ViewRoomsAdapter.ViewRoomsHolder> {

    //TODO: complete the list
    public ViewRoomsAdapter() {

    }

    @NonNull
    @Override
    public ViewRoomsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_more_rooms_recycler_items, parent, false);
        return new ViewRoomsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRoomsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewRoomsHolder extends RecyclerView.ViewHolder {
        public ViewRoomsHolder(View itemView) {
            super(itemView);
             // TODO: to complete find view by id for the cardview, TextViews  and ImageViews holding the view more rooms data
            // CardView cardView;
            // TextView textView1, textView2 ..... till you get all the textView you want to find
            // ImageView imageView1, imageView2, ImageView3; this is because it is only three imageView that you will the id's position
            // Button bookBtn;

        }
    }
}

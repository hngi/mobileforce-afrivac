package com.michael.afrivac.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.michael.afrivac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.MyViewHolder> {
    private List<Place> mPlaces;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView townNameTextView;
       public TextView countryNameTextView;
       public ImageView image;

       public MyViewHolder(View itemView) {
           super(itemView);

           townNameTextView = itemView.findViewById(R.id.town_name);
           countryNameTextView = itemView.findViewById(R.id.country_name);
           image = itemView.findViewById(R.id.discover_image);
       }
    }


    public DiscoverAdapter(ArrayList<Place> places) {
        mPlaces = places;
    }

    public DiscoverAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discover_africa_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Place place = mPlaces.get(position);
        TextView textView = holder.townNameTextView;
        textView.setText(place.getDestination());

        TextView txtView = holder.countryNameTextView;
        txtView.setText(place.getName());

//        ImageView imageView = holder.image;
//        imageView.setImageResource(place.getImage());
        Picasso.get().load(place.getImage()).placeholder(R.mipmap.ic_launcher).resize(500,500).centerCrop().into(holder.image);
    }


    @Override
    public int getItemCount() {
        return mPlaces.size();
    }
}

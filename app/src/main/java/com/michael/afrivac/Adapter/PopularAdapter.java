package com.michael.afrivac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.afrivac.R;



import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {
    Context context;
    ArrayList<PopularPlace> popularPlaces;

    public PopularAdapter(Context c, ArrayList<PopularPlace> p) {
        context = c;
        popularPlaces = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_populardestination, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.countryName.setText(popularPlaces.get(position).getName());
        holder.name.setText(popularPlaces.get(position).getCountry());
        holder.description.setText(popularPlaces.get(position).getDescription());
        holder.review.setText(String.valueOf(popularPlaces.get(position).getReview_number()));
        holder.rate.setText(String.valueOf(popularPlaces.get(position).getRating_number()));
        Picasso.get().load(popularPlaces.get(position).getImage()).placeholder(R.mipmap.ic_launcher).resize(500,500).centerInside().into(holder.image);

    }

    @Override
    public int getItemCount() {
        return popularPlaces.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countryName, name, description, review, rate;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.txt_destName);
            name = itemView.findViewById(R.id.txt_countryName);
            description = itemView.findViewById(R.id.txt_description);
            image = itemView.findViewById(R.id.img_country);
            review = itemView.findViewById(R.id.txt_review);
            rate = itemView.findViewById(R.id.txt_rating);

        }
    }
}

package com.michael.afrivac.Adapter;

import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.afrivac.LocationActivityOverviewFragment;
import com.michael.afrivac.PopularDestinationDetailsActivity;
import com.michael.afrivac.R;


import com.michael.afrivac.model.DiscoverAfrica;
import com.michael.afrivac.model.PopularPlaces;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> implements Filterable, View.OnClickListener  {
    Context context;
    ArrayList<PopularPlaces> popularPlaces;
    ArrayList<PopularPlaces> popularPlacesFull;

    public PopularAdapter(Context c, ArrayList<PopularPlaces> p) {
        context = c;
        popularPlaces = p;
        popularPlacesFull = new ArrayList<>(p);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_populardestination, parent, false));
//        myViewHolder.popularLayout.setOnClickListener(this);

        View v;
        v = LayoutInflater.from(context).inflate(R.layout.layout_populardestination, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        myViewHolder.popularLayout.setOnClickListener((View.OnClickListener) this);
        return new MyViewHolder(v);
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, PopularDestinationDetailsActivity.class);
        context.startActivity(intent);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout popularLayout;
        TextView countryName, name, description, review, rate;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.destination);
            name = itemView.findViewById(R.id.country);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            review = itemView.findViewById(R.id.engagement);
            rate = itemView.findViewById(R.id.ratingNumber);
            popularLayout = itemView.findViewById(R.id.popular_places_homepage);

        }
    }

    @Override
    public Filter getFilter() {
        return popularFilter;
    }

    private Filter popularFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PopularPlaces> filteredList = new ArrayList<>();

            if (constraint == null ||constraint.length() == 0){
                filteredList.addAll(popularPlacesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PopularPlaces popular : popularPlacesFull) {
                    if (popular.getCountry().toLowerCase().contains(filterPattern)) {
                        filteredList.add(popular);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            popularPlaces.clear();
            popularPlaces.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

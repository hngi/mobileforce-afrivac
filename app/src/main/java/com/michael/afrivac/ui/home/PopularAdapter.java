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

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {
        private List<Popular> mPopular;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView townNameTextView,review;
        public TextView countryNameTextView,rate;
        public ImageView image;
        public TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);

            townNameTextView = itemView.findViewById(R.id.popular_town);
            countryNameTextView = itemView.findViewById(R.id.popular_country);
            image = itemView.findViewById(R.id.popular_image);
            description = itemView.findViewById(R.id.popular_place_description);
            review = itemView.findViewById(R.id.popular_review);
            rate = itemView.findViewById(R.id.popular_rating);
        }
    }

    public PopularAdapter(ArrayList<Popular> populars) {
        mPopular = populars;
    }

    public com.michael.afrivac.ui.home.PopularAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_destination, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(com.michael.afrivac.ui.home.PopularAdapter.MyViewHolder holder, int position) {
        Popular popular = mPopular.get(position);
        TextView textView = holder.townNameTextView;
        textView.setText(popular.getName());

        TextView txtView = holder.countryNameTextView;
        txtView.setText(popular.getCountry());

//        ImageView imageView = holder.image;
//        imageView.setImageResource(popular.getmImage());

        TextView desc = holder.description;
        desc.setText(popular.getDescription());
        holder.review.setText(String.valueOf(mPopular.get(position).getReview_number()));
        holder.rate.setText(String.valueOf(mPopular.get(position).getRating_number()));
        Picasso.get().load(mPopular.get(position).getImage()).placeholder(R.mipmap.ic_launcher).resize(500,500).centerCrop().into(holder.image);
    }


    @Override
    public int getItemCount() {
        return mPopular.size();
    }
}

package com.michael.afrivac.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.michael.afrivac.R;
import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> implements View.OnClickListener {
        private List<Popular> mPopular;
        Context  mContext;

    public PopularAdapter(Context mContext, List<Popular>mPopular){
        this.mContext = mContext;
        this.mPopular = mPopular;

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, "you clicked on", Toast.LENGTH_SHORT).show();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView townNameTextView;
        public TextView countryNameTextView;
        public ImageView image;
        public TextView description;


        public MyViewHolder(View itemView) {
            super(itemView);

            townNameTextView = itemView.findViewById(R.id.popular_town);
            countryNameTextView = itemView.findViewById(R.id.popular_country);
            image = itemView.findViewById(R.id.popular_image);
            description = itemView.findViewById(R.id.popular_place_description);
        }

    }

    public PopularAdapter(ArrayList<Popular> populars) {
        mPopular = populars;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.popular_destination, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);


        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(com.michael.afrivac.ui.home.PopularAdapter.MyViewHolder holder, int position) {
        Popular popular = mPopular.get(position);
        TextView textView = holder.townNameTextView;
        textView.setText(popular.getmTownName());

        TextView txtView = holder.countryNameTextView;
        txtView.setText(popular.getmCountryName());

        ImageView imageView = holder.image;
        imageView.setImageResource(popular.getmImage());

        TextView desc = holder.description;
        desc.setText(popular.getmDescription());
    }


    @Override
    public int getItemCount() {
        return mPopular.size();
    }
}

package com.michael.afrivac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.GoogleMapsActivity;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.R;

import com.michael.afrivac.Util.Helper;
import com.michael.afrivac.model.DiscoverAfrica;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.MyViewHolder> implements Filterable {
    Context context;
    Helper helper = new Helper();
    ArrayList<DiscoverAfrica> discoverAfrica;
    private ArrayList<DiscoverAfrica> discoverAfricaFull;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private MainActivity mainActivity = new MainActivity();

    public DiscoverAdapter(Context c, ArrayList<DiscoverAfrica> d){
        context = c;
        discoverAfrica = d;
        discoverAfricaFull = new ArrayList<>(d);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiscoverAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_discover_africa,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.countryName.setText(discoverAfrica.get(position).getName());
        holder.destination.setText(discoverAfrica.get(position).getDestination());


        holder.countryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoogleMapsActivity.class);
                intent.putExtra("id_discover", "DiscoverAfrica");
                intent.putExtra("name", discoverAfrica.get(position).getName());
                context.startActivity(intent);
                //helper.gotoGoogleMapActivity(holder.itemView.getContext(), intent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.toastMessage(holder.itemView.getContext(), "country name is : "+ discoverAfrica.get(position).getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return discoverAfrica.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countryName,destination;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.txt_name);
            destination = itemView.findViewById(R.id.txt_dest);
            image= itemView.findViewById(R.id.image_country);
        }
    }

    @Override
    public Filter getFilter() {
        return discoverFilter;
    }

    private Filter discoverFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DiscoverAfrica> filteredList = new ArrayList<>();

            if (constraint == null ||constraint.length() == 0){
                filteredList.addAll(discoverAfricaFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DiscoverAfrica discover : discoverAfricaFull) {
                    if (discover.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(discover);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            discoverAfrica.clear();
            discoverAfrica.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

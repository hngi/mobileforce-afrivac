package com.michael.afrivac.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.afrivac.OurData;
import com.michael.afrivac.R;

public class ListAdapter extends RecyclerView.Adapter{

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return OurData.titles.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImage;
        private TextView mTextView;
        private TextView mEgyptTextView;

        public ListViewHolder(View itemView){
            super(itemView);
            mEgyptTextView = (TextView) itemView.findViewById(R.id.egypt_text);
            mTextView = (TextView) itemView.findViewById(R.id.cairo_text);
            mImage = (ImageView) itemView.findViewById(R.id.girl);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            mTextView.setText(OurData.titles[position]);
            mEgyptTextView.setText(OurData.subTitles[position]);
            mImage.setImageResource(OurData.picturePath[position]);
        }

        @Override
        public void onClick(View v) {

        }

    }

}

package com.michael.afrivac.ui.memories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.afrivac.PopularDestinationReviewsRecyclerviewAdapter;
import com.michael.afrivac.R;
import com.michael.afrivac.UserReviewDetails;

import java.util.ArrayList;
import java.util.List;

public class MemoriesAdapter extends RecyclerView.Adapter<MemoriesAdapter.ViewHolder> {

    private List<MemoriesList> usersMemories = new ArrayList<>();


    public MemoriesAdapter (Context context) {



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memories, parent, false);
        ViewHolder myViewHolder = new ViewHolder(myView);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MemoriesList mList = usersMemories.get(position);
        holder.setTitle(mList.getTitle());
        holder.setDescription(mList.getDescription());

    }

    @Override
    public int getItemCount() {
        return usersMemories.size();
    }

    public void addUserMemory(MemoriesList mList){
        usersMemories.add(mList);
        notifyItemInserted(usersMemories.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView Description;

        public void setTitle(String title) {
            this.Title.setText(title);
        }

        public void setDescription(String description) {
            this.Description.setText(description);
        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.mem_title);
            Description = itemView.findViewById(R.id.description);
        }
    }
}

package com.michael.afrivac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;

import java.util.ArrayList;
import java.util.List;

public class PopularDestinationReviewsRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserReviewDetails> usersReviewDetailsArray = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_destination_reviews_recyclerview_item, parent, false);
        ReviewAdapterHolder myViewHolder = new ReviewAdapterHolder(myView);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ReviewAdapterHolder) holder).set_user_details(usersReviewDetailsArray.get(position));
    }

    @Override
    public int getItemCount() {
        return usersReviewDetailsArray.size();
    }

    public void addUserReviewInfo(UserReviewDetails userReviewDetails){
        usersReviewDetailsArray.add(userReviewDetails);
        notifyItemInserted(usersReviewDetailsArray.size() - 1);
    }

    public class ReviewAdapterHolder extends RecyclerView.ViewHolder{
            TextView mUsername, mDate, remark, review;

        public void setUsername(String username) {
            this.mUsername.setText(username);
        }

        public void setDate(String date) {
            this.mDate.setText(date);
        }

        public void setRemark(String remark) {
            this.remark.setText(remark);
        }

        public void setReview(String review) {
            this.review.setText(review);
        }

        private void set_user_details(UserReviewDetails userReviewDetails){
            this.setUsername(userReviewDetails.getUsersName());
            this.setReview(userReviewDetails.getReview());
            this.setRemark(userReviewDetails.getTitle());
            this.setDate(userReviewDetails.getDate());
        }

        public ReviewAdapterHolder(@NonNull View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.username_review);
            mDate = itemView.findViewById(R.id.reviewDate);
            remark = itemView.findViewById(R.id.review_remark);
            review = itemView.findViewById(R.id.users_review);

        }
    }
}

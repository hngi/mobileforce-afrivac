package com.michael.afrivac;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.Event;
import com.michael.afrivac.Util.FirebaseUtil;
import com.michael.afrivac.Util.Helper;
import com.michael.afrivac.model.PopularPlaces;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PopularDestinationReviewsRecyclerviewAdapter extends RecyclerView.Adapter<PopularDestinationReviewsRecyclerviewAdapter.ReviewAdapterHolder> {

    private List<UserReviewDetails> usersReviewDetailsArray = new ArrayList<>();
    ArrayList<UserReviewDetails> temp = new ArrayList<>();

    private Context context;
    private LoginActivity loginActivity = new LoginActivity();
    EditText reviewtxt;
 //   private FirebaseDatabase mFirebaseDatabase;
 //   private DatabaseReference mDatabaseReference;

    public PopularDestinationReviewsRecyclerviewAdapter (Context context) {
        temp= (ArrayList<UserReviewDetails>) usersReviewDetailsArray;
       // mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
       // mDatabaseReference = FirebaseDatabase.getInstance().getReference("popular_destinatio").child("review");
        Log.i("db_popdest", "test");

    /*    mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {

                String date = snapshot.child("date").getValue().toString();
                String message_description = snapshot.child("message_description").getValue().toString();
                String message_title = snapshot.child("message_title").getValue().toString();
                String name = snapshot.child("name").getValue().toString();
                String profile_image = snapshot.child("profile_image").getValue().toString();
                String star_num = snapshot.child("star_num").getValue().toString();
                Log.i("get db children", "success");

                UserReviewDetails uRD = new UserReviewDetails(name, date, message_title, message_description);
                usersReviewDetailsArray.add(uRD);
                notifyItemInserted(usersReviewDetailsArray.size()-1);
                Log.i("database", date);


            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });    */


    }

    @NonNull
    @Override
    public ReviewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_destination_reviews_recyclerview_item, parent, false);
        ReviewAdapterHolder myViewHolder = new ReviewAdapterHolder(myView);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterHolder holder, int position) {
   //     ((ReviewAdapterHolder) holder).set_user_details(usersReviewDetailsArray.get(position));
        final UserReviewDetails current = usersReviewDetailsArray.get(position);
        holder.setUsername(current.getUsersName());
        holder.setDate(current.getDate());
        holder.setRemark(current.getTitle());
        holder.setReview(current.getReview());

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

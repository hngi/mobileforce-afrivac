package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewRoomsActivity extends AppCompatActivity {

    RecyclerView mViewRoomsRecyclerView;
    Button bookingButton;

    // TODO: finish the job bro
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_more_rooms_1);

        mViewRoomsRecyclerView = findViewById(R.id.cocoon_hotel_recycler_view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mViewRoomsRecyclerView.setLayoutManager(layoutManager);
        mViewRoomsRecyclerView.setHasFixedSize(true);

        bookingButton= findViewById(R.id.booking_btn);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: do your intent, the code here executes on main thread after user presses button
            }
        });

    }
}
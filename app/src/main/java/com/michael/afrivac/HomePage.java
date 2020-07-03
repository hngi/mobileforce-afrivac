package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michael.afrivac.Adapter.DiscoverAdapter;
import com.michael.afrivac.Adapter.PopularAdapter;

import com.michael.afrivac.model.DiscoverAfrica;
import com.michael.afrivac.model.PopularPlaces;


import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    DatabaseReference reference,discoverReference;
    RecyclerView recyclerView,rv_discoverAfrica;
    ArrayList<PopularPlaces> list;
    ArrayList<DiscoverAfrica> discoverList;
    PopularAdapter popularAdapter;
    DiscoverAdapter discoverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.rv_popularDest);
        rv_discoverAfrica = findViewById(R.id.rv_discovery);
        rv_discoverAfrica.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<PopularPlaces>();
        discoverList = new ArrayList<DiscoverAfrica>();

        reference= FirebaseDatabase.getInstance().getReference().child("popular_destinatio");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren())
                {
                    PopularPlaces p = datasnapshot.getValue(PopularPlaces.class);
                    list.add(p);
                }
                popularAdapter = new PopularAdapter(HomePage.this,list);
                recyclerView.setAdapter(popularAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        discoverReference =  FirebaseDatabase.getInstance().getReference().child("discover_africa");
        discoverReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren())
                {
                    DiscoverAfrica d = datasnapshot.getValue(DiscoverAfrica.class);
                    discoverList.add(d);
                }
                discoverAdapter = new DiscoverAdapter(HomePage.this,discoverList);
                rv_discoverAfrica.setAdapter(discoverAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
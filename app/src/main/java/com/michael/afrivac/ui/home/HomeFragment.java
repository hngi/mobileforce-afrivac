package com.michael.afrivac.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michael.afrivac.HomePage;
import com.michael.afrivac.LocationActivity;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.PopularDestinationDetailsActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.model.DiscoverAfrica;
import com.michael.afrivac.model.PopularPlaces;
import com.michael.afrivac.ui.account.AccountFragment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Calendar;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class HomeFragment extends Fragment {

    DatabaseReference reference,discoverReference,mDatabase;
    RecyclerView recyclerView,rv_discoverAfrica;
    ArrayList<PopularPlaces> list;
    ArrayList<DiscoverAfrica> discoverList;
    com.michael.afrivac.Adapter.PopularAdapter popularAdapter;
    com.michael.afrivac.Adapter.DiscoverAdapter discoverAdapter;

    FirebaseAuth mAuth;
    String userID;

    //    private HomeViewModel homeViewModel;
    CardView cairo, nairobi, popular, pop2, pop3;
    EditText search_view;
    ImageView profile_image;
    SearchView searchView;
    ImageButton menuButton;
    TextView welcome_text;

    private int radius;
//for the discover Africa recycler view
//    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    //for the popular destinations recycler view
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private int currentTime;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);z

        //code to remove the action bar
        ((MainActivity) requireActivity()).getSupportActionBar().hide();


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.popular_destination_recycler);
        rv_discoverAfrica = root.findViewById(R.id.my_recycler_view);
        rv_discoverAfrica.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<PopularPlaces>();
        discoverList = new ArrayList<DiscoverAfrica>();
        profile_image = root.findViewById(R.id.profile_image);
        menuButton = root.findViewById(R.id.menuButton);
        welcome_text = root.findViewById(R.id.welcome_text);

        Calendar calender = Calendar.getInstance();
        currentTime = calender.get(Calendar.HOUR_OF_DAY);


        mAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        mDatabase.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String user_name;
                    user_name = (String) snapshot.child("username").getValue();

                    if (currentTime>= 0 && currentTime<12){
                        welcome_text.setText(getString(R.string.good_morning) + user_name + getString(R.string.visit));
                    } else if (currentTime>12 && currentTime <17){
                        welcome_text.setText(getString(R.string.good_afternoon) + user_name + getString(R.string.visit));
                    } else if (currentTime>=17 && currentTime<22){
                        welcome_text.setText(getString(R.string.good_evening) + user_name + getString(R.string.visit));
                    } else if (currentTime>=22){
                        welcome_text.setText(getString(R.string.good_night) + user_name + getString(R.string.see_you));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((DrawerLayout) requireActivity().findViewById(R.id.drawer_layout)).openDrawer(GravityCompat.START);
                } catch (Exception ignored) { }
            }
        });

//        recyclerView = root.findViewById(R.id.my_recycler_view);

//        ArrayList<Place> places = new ArrayList<>();
//
//        // use a linear layout manager
////        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
////        recyclerView.setLayoutManager(layoutManager);
//
//        //sets the adapter for the discover Africa recycler view
////        DiscoverAdapter discoverAdapter = new DiscoverAdapter(places);
////        recyclerView.setAdapter(discoverAdapter);
//
//        //page indicator for the discover Africa recyclerView
//        ScrollingPagerIndicator recycleIndicator = root.findViewById(R.id.indicator);
//        recycleIndicator.setSelectedDotColor(ContextCompat.getColor(root.getContext(),R.color.colorBlue));
//        recycleIndicator.setDotColor(ContextCompat.getColor(root.getContext(), R.color.indicator));
//        recycleIndicator.setFadingEdgeLength(10);
//        recycleIndicator.attachToRecyclerView(rv_discoverAfrica);
////
//        ArrayList<Popular> populars = new ArrayList<>();
//
//
//        // use a Vertical linear layout manager
//        layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView2.setLayoutManager(layoutManager2);

        //sets the adapter for the popular destinations recycler view
//        PopularAdapter popularAdapter = new PopularAdapter(populars);
//        recyclerView2.setAdapter(popularAdapter);

        reference= FirebaseDatabase.getInstance().getReference().child("popular_destinatio");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren())
                {
                    PopularPlaces p = datasnapshot.getValue(PopularPlaces.class);
                    list.add(p);
                }
                popularAdapter = new com.michael.afrivac.Adapter.PopularAdapter(getActivity(),list);
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
                discoverAdapter = new com.michael.afrivac.Adapter.DiscoverAdapter(getActivity(),discoverList);
                rv_discoverAfrica.setAdapter(discoverAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), ProfilePageActivity.class);
                //startActivity(intent);
                Fragment fragment = new AccountFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        return root;
    }
}
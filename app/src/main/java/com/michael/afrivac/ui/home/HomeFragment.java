package com.michael.afrivac.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michael.afrivac.R;
import com.michael.afrivac.ui.account.AccountFragment;

import java.util.ArrayList;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class HomeFragment extends Fragment {

    //    private HomeViewModel homeViewModel;
    CardView cairo, nairobi, popular, pop2, pop3;
    EditText search_view;
    ImageView star1,star2, star3, star4, star5, pop2_star1, pop2star2, pop2_star3, pop2_star4, pop2_star5,
            pop3_star1, pop3star2, pop3_star3, pop3_star4, pop3_star5,
            profile_image, favorite, fav2, fav3;
    SearchView searchView;
    private int radius;
//for the discover Africa recycler view
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    DatabaseReference reference,discoverReference;

    //for the popular destinations recycler view
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    ArrayList<Place> places = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        // cards
//          cairo = root.findViewById(R.id.caira_card);
//        nairobi = root.findViewById(R.id.nairobi_card);
//        popular = root.findViewById(R.id.caira_card);
//        pop2 = root.findViewById(R.id.madagascar_card);
//        pop3 = root.findViewById(R.id.são_tomé_e_card);
        //profile image
        profile_image = root.findViewById(R.id.profile_image);

//        // fav icons
//        favorite = root.findViewById(R.id.fav);
//        fav2 = root.findViewById(R.id.fav2);
//        fav3 = root.findViewById(R.id._fav2);
//
//        //rating stars
//        star1 = root.findViewById(R.id.star2);
//        star2 = root.findViewById(R.id.star3);
//        star3 = root.findViewById(R.id.star4);
//        star4 = root.findViewById(R.id.star5);
//        star5 = root.findViewById(R.id.empty_Star);
//        pop2_star1 = root.findViewById(R.id.s_tar3);
//        pop2star2 = root.findViewById(R.id.s_tar2);
//        pop2_star3 = root.findViewById(R.id.s_tar4);
//        pop2_star4 = root.findViewById(R.id.s_tar5);
//        pop2_star5 = root.findViewById(R.id.empty_Star);
//        pop3_star1 = root.findViewById(R.id._star_2);
//        pop3star2 = root.findViewById(R.id._star_3);
//        pop3_star3 = root.findViewById(R.id._star_4);
//        pop3_star4 = root.findViewById(R.id._star_5);
//        pop3_star5 = root.findViewById(R.id._emptyStar);

//        radius =64;
//
//        nairobi.setRadius(radius);
//        cairo.setRadius(radius);
//        pop2.setRadius(radius);
//        pop3.setRadius(radius);
//        popular.setRadius(radius);

        //For discover Africa recycler view
        recyclerView = root.findViewById(R.id.rv_discovery1);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //sets the adapter for the discover Africa recycler view
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(places);
        recyclerView.setAdapter(discoverAdapter);


        //page indicator for the discover Africa recyclerView
//        ScrollingPagerIndicator recycleIndicator = root.findViewById(R.id.indicator);
//        recycleIndicator.setSelectedDotColor(ContextCompat.getColor(root.getContext(),R.color.colorBlue));
//        recycleIndicator.setDotColor(ContextCompat.getColor(root.getContext(), R.color.indicator));
//        recycleIndicator.setFadingEdgeLength(10);
//        recycleIndicator.attachToRecyclerView(recyclerView);





        discoverReference = FirebaseDatabase.getInstance().getReference().child("discover_africa");
        discoverReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren())
                {
                    Place p = datasnapshot.getValue(Place.class);
                    places.add(p);
                }
                //sets the adapter for the discover Africa recycler view HomePage.this,list
                DiscoverAdapter discoverAdapter = new DiscoverAdapter(places);
                recyclerView.setAdapter(discoverAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        //For popular destinations recycler view
        recyclerView2 = root.findViewById(R.id.rv_popularDest1);

        final ArrayList<Popular> populars = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("popular_destinatio");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren())
                {
                    Popular popularPlace = datasnapshot.getValue(Popular.class);
                    populars.add(popularPlace);
                }
                //sets the adapter for the popular destinations recycler view
                PopularAdapter popularAdapter = new PopularAdapter(populars);
                recyclerView2.setAdapter(popularAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // use a Vertical linear layout manager
        layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager2);



//        final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
//
//        popular.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP){
//                    popular.startAnimation(animation);
//                }
//                return true;
//            }
//        });
//
//        pop2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP){
//                    pop2.startAnimation(animation);
//                }
//                return true;
//            }
//        });
//
//        pop3.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP){
//                    pop3.startAnimation(animation);
//                    v.performClick();
//                    try {
//                        finalize();
//                    } catch (Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//                }
//                return true;
//            }
//        });


//        favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (favorite.getDrawable().getConstantState() ==
//                        getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
//                    favorite.setImageResource(R.drawable.fav);
//                } else {
//                    favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                }
//
//            }
//        });
//
//        fav2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (fav2.getDrawable().getConstantState() ==
//                        getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
//                    fav2.setImageResource(R.drawable.fav);
//                } else {
//                    fav2.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                }
//
//            }
//        });

//        fav3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (fav3.getDrawable().getConstantState() ==
//                        getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
//                    fav3.setImageResource(R.drawable.fav);
//                } else {
//                    fav3.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                }
//
//            }
//        });

//        star1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                star1.setImageResource(R.drawable.ic_star_black_24dp);
//                star2.setImageResource(R.drawable.ic_star_border_black_24dp);
//                star3.setImageResource(R.drawable.ic_star_border_black_24dp);
//                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        star2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                star1.setImageResource(R.drawable.ic_star_black_24dp);
//                star2.setImageResource(R.drawable.ic_star_black_24dp);
//                star3.setImageResource(R.drawable.ic_star_border_black_24dp);
//                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        star3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                star1.setImageResource(R.drawable.ic_star_black_24dp);
//                star2.setImageResource(R.drawable.ic_star_black_24dp);
//                star3.setImageResource(R.drawable.ic_star_black_24dp);
//                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        star4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                star1.setImageResource(R.drawable.ic_star_black_24dp);
//                star2.setImageResource(R.drawable.ic_star_black_24dp);
//                star3.setImageResource(R.drawable.ic_star_black_24dp);
//                star4.setImageResource(R.drawable.ic_star_black_24dp);
//                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        star5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                star1.setImageResource(R.drawable.ic_star_black_24dp);
//                star2.setImageResource(R.drawable.ic_star_black_24dp);
//                star3.setImageResource(R.drawable.ic_star_black_24dp);
//                star4.setImageResource(R.drawable.ic_star_black_24dp);
//                star5.setImageResource(R.drawable.ic_star_black_24dp);
//            }
//        });
//
//        pop2_star1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2star2.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop2_star3.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop2_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop2_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        pop2star2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2star2.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star3.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop2_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop2_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        pop2_star3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2star2.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star3.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop2_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        pop2_star4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2star2.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star3.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star4.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        pop2_star5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2star2.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star3.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star4.setImageResource(R.drawable.ic_star_black_24dp);
//                pop2_star5.setImageResource(R.drawable.ic_star_black_24dp);
//            }
//        });
//
//        pop3_star1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3star2.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop3_star3.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop3_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop3_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        pop3star2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3star2.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star3.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop3_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop3_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        pop3_star3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3star2.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star3.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
//                pop3_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        pop3_star4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3star2.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star3.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star4.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
//            }
//        });
//
//        pop3_star5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3star2.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star3.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star4.setImageResource(R.drawable.ic_star_black_24dp);
//                pop3_star5.setImageResource(R.drawable.ic_star_black_24dp);
//            }
//        });

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

        /*cairo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
                startActivity(intent);
            }
        });

        nairobi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
                startActivity(intent);
            }
        });

        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
                startActivity(intent);
            }
        });

        pop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
                startActivity(intent);
            }
        });

        pop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PopularDestinationDetailsActivity.class);
                startActivity(intent);
            }
        });*/
//
//        cairo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), LocationActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        nairobi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), LocationActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        popular.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), LocationActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        pop2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), LocationActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        pop3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), LocationActivity.class);
//                startActivity(intent);
//            }
//        });

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
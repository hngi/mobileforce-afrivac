package com.michael.afrivac.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.michael.afrivac.LocationActivity;
import com.michael.afrivac.LoginActivity;
import com.michael.afrivac.ProfilePageActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.ui.account.AccountFragment;

public class HomeFragment extends Fragment {

    //    private HomeViewModel homeViewModel;
    RelativeLayout cairo, nairobi, popular;
    ImageView star1,star2, star3, star4, star5, profile_image, favorite;
    SearchView searchView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        cairo = root.findViewById(R.id.cairo);
        nairobi = root.findViewById(R.id.nairobi);
        popular = root.findViewById(R.id.popular);
        profile_image = root.findViewById(R.id.profile_image);

        favorite = root.findViewById(R.id.favorite);

        star1 = root.findViewById(R.id.star1);
        star2 = root.findViewById(R.id.star2);
        star3 = root.findViewById(R.id.star3);
        star4 = root.findViewById(R.id.star4);
        star5 = root.findViewById(R.id.star5);

        searchView = root.findViewById(R.id.search_view);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favorite.getDrawable().getConstantState() ==
                        getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
                    favorite.setImageResource(R.drawable.fav);
                } else {
                    favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }

            }
        });

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_border_black_24dp);
                star3.setImageResource(R.drawable.ic_star_border_black_24dp);
                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_border_black_24dp);
                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                star4.setImageResource(R.drawable.ic_star_black_24dp);
                star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                star4.setImageResource(R.drawable.ic_star_black_24dp);
                star5.setImageResource(R.drawable.ic_star_black_24dp);
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

        cairo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });

        nairobi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });

        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });

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

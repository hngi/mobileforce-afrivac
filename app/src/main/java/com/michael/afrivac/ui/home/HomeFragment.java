package com.michael.afrivac.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    CardView cairo, nairobi, popular, pop2, pop3;
    EditText search_view;
    ImageView star1,star2, star3, star4, star5, pop2_star1, pop2star2, pop2_star3, pop2_star4, pop2_star5,
            pop3_star1, pop3star2, pop3_star3, pop3_star4, pop3_star5,
            profile_image, favorite, fav2, fav3;
    SearchView searchView;
    private int radius;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // cards
        cairo = root.findViewById(R.id.camelCard);
        nairobi = root.findViewById(R.id.nairobi_card);
        popular = root.findViewById(R.id.pop1);
        pop2 = root.findViewById(R.id.pop2);
        pop3 = root.findViewById(R.id.pop3);
        //profile image
        profile_image = root.findViewById(R.id.profile_image);

        // fav icons
        favorite = root.findViewById(R.id.fav);
        fav2 = root.findViewById(R.id.fav_2);
        fav3 = root.findViewById(R.id.fav_3);

        //rating stars
        star1 = root.findViewById(R.id.star1);
        star2 = root.findViewById(R.id.star2);
        star3 = root.findViewById(R.id.star3);
        star4 = root.findViewById(R.id.star4);
        star5 = root.findViewById(R.id.star5);
        pop2_star1 = root.findViewById(R.id.pop2_star1);
        pop2star2 = root.findViewById(R.id.pop2_star2);
        pop2_star3 = root.findViewById(R.id.pop2_star3);
        pop2_star4 = root.findViewById(R.id.pop2_star4);
        pop2_star5 = root.findViewById(R.id.pop2_star5);
        pop3_star1 = root.findViewById(R.id.pop3_star1);
        pop3star2 = root.findViewById(R.id.pop3_star2);
        pop3_star3 = root.findViewById(R.id.pop3_star3);
        pop3_star4 = root.findViewById(R.id.pop3_star4);
        pop3_star5 = root.findViewById(R.id.pop3_star5);

        radius =64;

        nairobi.setRadius(radius);
        cairo.setRadius(radius);
        pop2.setRadius(radius);
        pop3.setRadius(radius);
        popular.setRadius(radius);

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

        fav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav2.getDrawable().getConstantState() ==
                        getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
                    fav2.setImageResource(R.drawable.fav);
                } else {
                    fav2.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }

            }
        });

        fav3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav3.getDrawable().getConstantState() ==
                        getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
                    fav3.setImageResource(R.drawable.fav);
                } else {
                    fav3.setImageResource(R.drawable.ic_favorite_border_black_24dp);
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

        pop2_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop2star2.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop2_star3.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop2_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop2_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        pop2star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop2star2.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star3.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop2_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop2_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        pop2_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop2star2.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star3.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop2_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        pop2_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop2star2.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star3.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star4.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        pop2_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop2_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop2star2.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star3.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star4.setImageResource(R.drawable.ic_star_black_24dp);
                pop2_star5.setImageResource(R.drawable.ic_star_black_24dp);
            }
        });

        pop3_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop3star2.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop3_star3.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop3_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop3_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        pop3star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop3star2.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star3.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop3_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop3_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        pop3_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop3star2.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star3.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star4.setImageResource(R.drawable.ic_star_border_black_24dp);
                pop3_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        pop3_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop3star2.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star3.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star4.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star5.setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        });

        pop3_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop3_star1.setImageResource(R.drawable.ic_star_black_24dp);
                pop3star2.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star3.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star4.setImageResource(R.drawable.ic_star_black_24dp);
                pop3_star5.setImageResource(R.drawable.ic_star_black_24dp);
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

        pop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });

        pop3.setOnClickListener(new View.OnClickListener() {
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

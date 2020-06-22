package com.michael.afrivac.ui.findHotel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.michael.afrivac.R;

public class FindHotelFragment extends Fragment {

    private FindHotelViewModel findHotelViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        findHotelViewModel =
                ViewModelProviders.of(this).get(FindHotelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_find_hotel, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        findHotelViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
package com.michael.afrivac.ui.popularDestination;

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

public class PopularDestinationFragment extends Fragment {

    private PopularDestinationViewModel popularDestinationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        popularDestinationViewModel =
                ViewModelProviders.of(this).get(PopularDestinationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_destination, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        popularDestinationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
package com.michael.afrivac.ui.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.michael.afrivac.R;

public class SupportFragment extends Fragment {

    private SupportViewModel supportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        supportViewModel =new ViewModelProvider(this).get(SupportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_support, container, false);
        return root;
    }
}
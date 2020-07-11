package com.michael.afrivac.ui.memories;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.michael.afrivac.MainActivity;
import com.michael.afrivac.MyCustomDialog;
import com.michael.afrivac.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MemoriesFragment extends Fragment implements MyCustomDialog.OnInputSelected{

    public static MemoriesFragment newInstance() {
        return new MemoriesFragment();
    }

    private static final String TAG = "MemoriesFragment";

    public void sendInput(String input) {
        Log.d(TAG, "sendInput: found incoming input: " + input);

        mInputDisplay.setText(input);
    }

    private Button mOpenDialog;
    public TextView mInputDisplay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_memories, container, false);

        mOpenDialog = view.findViewById(R.id.open_dialog);
        mInputDisplay = view.findViewById(R.id.input_display);

        mOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");

                MyCustomDialog dialog = new MyCustomDialog();
                dialog.setTargetFragment(MemoriesFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");
            }
        });


        return view;
    }
}
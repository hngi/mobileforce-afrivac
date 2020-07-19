package com.michael.afrivac.ui.memories;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.MyCustomDialog;
import com.michael.afrivac.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class MemoriesFragment extends Fragment implements MyCustomDialog.OnInputSelected{

    public static MemoriesFragment newInstance() {
        return new MemoriesFragment();
    }

    private static final String TAG = "MemoriesFragment";

    public void sendInput(String input) {
        Log.d(TAG, "sendInput: found incoming input: " + input);

        //mdescription.setText(input);
    }

    @Override
    public void sendInput1(String input2) {
        Log.d(TAG, "sendInput: found incoming input: " + input2);

        //mInputDisplay.setText(input2);
    }

    MemoriesAdapter memoriesAdapter;
    private LinearLayoutManager layoutManager;

    public TextView mInputDisplay,mdescription,save,show;
    public RecyclerView myRecycler;
    SharedPreferences sharedPreferences;
    static final String myPreferences = "mypref";
    static final String title = "titlekey";
    static final String Description = "descriptionkey";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_memories, container, false);


        myRecycler = view.findViewById(R.id.mem_recycler);
        //mInputDisplay = view.findViewById(R.id.input_display);
       // mdescription = view.findViewById(R.id.description);
        sharedPreferences  = requireContext().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        memoriesAdapter = new MemoriesAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());

        myRecycler.setLayoutManager(layoutManager);
        myRecycler.setAdapter(memoriesAdapter);

        if (sharedPreferences.contains(title)){
           // mInputDisplay.setText(sharedPreferences.getString(title,""));
        }
        if (sharedPreferences.contains(Description)){
           // mdescription.setText(sharedPreferences.getString(Description,""));
        }
        save = view.findViewById(R.id.save);

         save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   sharedPreferences = requireContext().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
                if (sharedPreferences.contains(title)){
                    mInputDisplay.setText(sharedPreferences.getString(title,""));
                }
                if (sharedPreferences.contains(Description)){
                    mdescription.setText(sharedPreferences.getString(Description,""));
                }   */

                MyCustomDialog dialog = new MyCustomDialog();
                String title = dialog.getInput1text();
                String description = dialog.getInput2text();

                memoriesAdapter.addUserMemory(new MemoriesList(title, description));


            }
        });


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: opening dialog");

                MyCustomDialog dialog = new MyCustomDialog();
                dialog.setTargetFragment(MemoriesFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");
            }
        });
//
//        mOpenDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: opening dialog");
//
//                MyCustomDialog dialog = new MyCustomDialog();
//                dialog.setTargetFragment(MemoriesFragment.this, 1);
//                dialog.show(getFragmentManager(), "MyCustomDialog");
//            }
//        });


        return view;
    }
}

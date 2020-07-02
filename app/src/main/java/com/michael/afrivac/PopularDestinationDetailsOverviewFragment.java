package com.michael.afrivac;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PopularDestinationDetailsOverviewFragment extends Fragment {

    private RecyclerView recyclerView, recreationRV;
    private LinearLayoutManager layoutManager, mLinearLayoutManager;
    private PopularDestinationDetailsHotelsAdapter hoteladapter;
    private PopularDestinationDetailsRecreationCentersAdapter mPopularDestinationDetailsRecreationCentersAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PopularDestinationDetailsOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularDestinationDetailsOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularDestinationDetailsOverviewFragment newInstance(String param1, String param2) {
        PopularDestinationDetailsOverviewFragment fragment = new PopularDestinationDetailsOverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_popular_destination_details_overview, container, false);
        recyclerView = view.findViewById(R.id.hotelsItem);
        recreationRV = view.findViewById(R.id.whatToDoRV);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLinearLayoutManager = new LinearLayoutManager(getContext());

        hoteladapter = new PopularDestinationDetailsHotelsAdapter();
        mPopularDestinationDetailsRecreationCentersAdapter = new PopularDestinationDetailsRecreationCentersAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recreationRV.setLayoutManager(mLinearLayoutManager);

        recyclerView.setAdapter(hoteladapter);
        recreationRV.setAdapter(mPopularDestinationDetailsRecreationCentersAdapter);

        return view;
    }
}
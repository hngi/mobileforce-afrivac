package com.michael.afrivac.ui.support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.afrivac.Adapter.AskedQuestionsAdapter;
import com.michael.afrivac.Model.AskedQuestions;
import com.michael.afrivac.R;
import com.michael.afrivac.TermsAndConditions;
import com.michael.afrivac.Util.Helper;

import java.util.ArrayList;
import java.util.List;

public class SupportFragment extends Fragment {

    private Helper helper;
    private DatabaseReference data;
    private AskedQuestionsAdapter questionsAdapter;
    private List<AskedQuestions> questions;
    private SupportViewModel supportViewModel;
    ImageView backArrow;

    private RecyclerView recyclerView;
    private RelativeLayout contactUs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        supportViewModel =new ViewModelProvider(this).get(SupportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_support, container, false);
        helper = new Helper(getContext());

        contactUs = root.findViewById(R.id.support_contact_us);
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.gotoContactUsActivity(getContext());
            }
        });

        backArrow = root.findViewById(R.id.back_button_sImageView);
        recyclerView = root.findViewById(R.id.question_and_answer_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.gotoMainActivity(getContext());
            }
        });

        TextView textView = root.findViewById(R.id.feedback_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.hng.howtodo"));
                intent.setPackage("com.android.vending");
                startActivity(intent);
            }
        });

        TextView termAndConditions = root.findViewById(R.id.terms_and_condition_textView);
        termAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TermsAndConditions.class);
                startActivity(intent);

            }
        });

        displayFrequentlyAskedQuestions();
        return root;
    }

    public void displayFrequentlyAskedQuestions(){
        helper.progressDialogStart("Pls Wait", "Most asked questions are loading");
        data = FirebaseDatabase.getInstance().getReference().child("questions").child("frequently_asked");
        questions = new ArrayList<>();
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AskedQuestions question = dataSnapshot.getValue(AskedQuestions.class);
                    if(question.getDescription() != null && question.getTitle() != null){
                        questions.add(question);
                    }

                    questionsAdapter = new AskedQuestionsAdapter(getContext(), questions);
                    recyclerView.setAdapter(questionsAdapter);
                }
                helper.progressDialogEnd();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
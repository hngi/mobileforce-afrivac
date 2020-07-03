package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michael.afrivac.Adapter.AskedQuestionsAdapter;
import com.michael.afrivac.Model.AskedQuestions;
import com.michael.afrivac.Util.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactUsActivity2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText title, description;
    private LinearLayout linearLayout;
    private Button buttonContactUs, addMessage;
    private String userID;

    private Helper helper;
    private DatabaseReference reference;
    private FirebaseUser user;
    private List<AskedQuestions> askedQuestions;
    private AskedQuestionsAdapter questionsAdapter;
    Animation animation;
    private String titleStr;
    private String descriptionStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us2);

        helper = new Helper(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("questions").child("user_questions").child(user.getUid());

        recyclerView = findViewById(R.id.asked_questions_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        linearLayout = findViewById(R.id.linear_layout);
        title = findViewById(R.id.contact_us_title);
        description = findViewById(R.id.contact_us_description);

        ///diplay meesages
        displayMessages();

        buttonContactUs = findViewById(R.id.button_contactus);
        buttonContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleStr = title.getText().toString();
                descriptionStr = description.getText().toString();
                userID = user.getUid();
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                buttonContactUs.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        contactUs();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        });

        addMessage = findViewById(R.id.add_message);
        addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMessage.setVisibility(View.GONE);
                buttonContactUs.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        helper.gotoMainActivity(this);
    }

    public void contactUs(){
        if(titleStr.trim().isEmpty()){
            title.findFocus();
            helper.toastMessage(this, "enter what you want to tell us pls");
        }else{
            helper.progressDialogStart("Please Wait", "We are getting you message");
            if(descriptionStr.trim().isEmpty()){
                descriptionStr = "no detail needed";
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+"allenkamadje@mgail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, titleStr);
            intent.putExtra(Intent.EXTRA_TEXT, descriptionStr + "\n" + userID);
            startActivity(intent);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", titleStr);
            hashMap.put("description", descriptionStr);
            reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        helper.progressDialogEnd();
                        title.clearComposingText();
                        description.clearComposingText();
                        buttonContactUs.setVisibility(View.GONE);
                        addMessage.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        //startActivity(new Intent(getApplicationContext(), ContactUsActivity2.class));
                    }else{
                        helper.progressDialogEnd();
                        helper.toastMessage(getApplicationContext(), "Failed to save account info \n" + task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void displayMessages(){
        helper.progressDialogStart("Pls Wait", "Loading your messages");
        askedQuestions = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                askedQuestions.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AskedQuestions question = dataSnapshot.getValue(AskedQuestions.class);
                    if(question.getTitle() != null && question.getDescription() != null){
                        askedQuestions.add(question);
                    }

                    questionsAdapter = new AskedQuestionsAdapter(getApplicationContext(), askedQuestions);
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
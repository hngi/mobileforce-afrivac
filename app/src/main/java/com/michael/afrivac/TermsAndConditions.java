package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TermsAndConditions extends AppCompatActivity {

    public void backToSignUp(View view) {

        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
    }
}
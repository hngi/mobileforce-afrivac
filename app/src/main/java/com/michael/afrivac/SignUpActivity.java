package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michael.afrivac.Auth.AuthViewModel;
import com.michael.afrivac.Util.Helper;

import java.util.Scanner;

public class SignUpActivity extends AppCompatActivity {

    Helper helper;
    private AuthViewModel authViewModel;

    EditText Username;
    EditText Email;
    EditText Phone;
    EditText Country;
    EditText Password;
    EditText ConfirmPassword;
    TextView ToSignIn;
    Button  signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        authViewModel = new AuthViewModel();
        helper = new Helper();

        ToSignIn = findViewById(R.id.toSignIn);
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.SignUp(v);
            }
        });

        ToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }

}

package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private String Email, Password;
    private TextView sign_in, signUp, forgotPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        sign_in = findViewById(R.id.singin_into_account);

        signUp = findViewById(R.id.singin_goto_signup);
        forgotPassword = findViewById(R.id.signin_forgot_password);

         Email = email.getText().toString().trim();
         Password = password.getText().toString().trim();


        //Email = email.getText().toString().trim();
        // Password = password.getText().toString().trim();


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 //Email = email.getText().toString().trim();
                 //Password = password.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    email.setError("enter your email");
                    return;

                }if (TextUtils.isEmpty(Password)){
                    password.setError("enter password");
                }else {
                    checkNetwork();
                }
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //finish();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
                finish();
            }
        });
    }
    private void checkNetwork(){
        if (isNetWorkAvailable()){
            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Log.d("login", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else {
                        Log.w("sign in", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed...", Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }

                }
            });

        }else {
            Toast.makeText(getApplicationContext(), "network error", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isNetWorkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
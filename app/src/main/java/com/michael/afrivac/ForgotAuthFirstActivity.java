package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotAuthFirstActivity extends AppCompatActivity {
    private EditText email;
    private TextView reset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_auth_first);

        email = findViewById(R.id.forgot_auth_email);
        reset = findViewById(R.id.forgot_auth_reset);

        mAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mail = email.getText().toString().trim();
                if (mail.isEmpty())
                {
                    email.setError("Please enter your valid email");
                    email.requestFocus();
                }else {
                    {
                        mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ForgotAuthFirstActivity.this,
                                            "Please check your email for reset link", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));//Change the signUpActivity to signinActivity
                                }else {
                                    String message = task.getException().getMessage();
                                    Toast.makeText(ForgotAuthFirstActivity.this, "Error Occurred" + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}

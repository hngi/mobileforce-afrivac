package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Scanner;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText Username;
    EditText Email;
    EditText Phone;
    EditText Country;
    EditText Password;
    EditText ConfirmPassword;
    TextView ToSignIn;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        Country = findViewById(R.id.country);
        Password = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirmpassword);
        ToSignIn = findViewById(R.id.toSignIn);

        String PasswordAuth;
        mAuth = FirebaseAuth.getInstance();

        ToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }


    /*@Override
    public void onClick(View view) {
        if (view.getId() == R.id.toSignIn) {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }*/

    public void SignUp(View view) {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                String email;
                String password;
                email = Email.getText().toString();
                password = Password.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                    Email.requestFocus();

                } else if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter a Password", Toast.LENGTH_SHORT).show();
                    Password.requestFocus();

                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(i);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });



    }


/*    public void PasswordChecker (View view) {

        String[] keys = new String[2];  // Since you are reading 2 keys, so size = 2
        boolean redo = false;
        int count = 0;    // To maintain the number of keys read. should stop at count = 2

        do {

            redo = false;
            Scanner keycode =  new Scanner(System.in);

            System.out.println("Input keycode No: + " + (count + 1));
            String key1 = keycode.nextLine();

            if (key1.length() < 6 || key1.length() > 8) {
                redo = true;

            } else {
         //       my extra code will be here

                if (!upper) {
                    System.out.println("must contain at least one uppercase character");
                    redo = true;

                } else if (!lower) {
                    System.out.println("must contain at least one lowercase character");
                    redo = true;

                } else if (!number) {
                    System.out.println("must contain at least one number");
                    redo = true;

                } else {
                    keys[count++] = key1;  // Enter a new key in array
                }
            }

        } while (redo || count < 2);

        if (keys[0].equals(keys[1])) {
            System.out.println("Keys are equal");
        }

    }*/

}

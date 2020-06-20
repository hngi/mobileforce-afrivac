package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        sign_in = findViewById(R.id.singin_into_account);
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Email.length() == 0 && Password.length() == 0) {
                    if (Email.equals("admins") && Password.equals("admins")) {
                        Toast.makeText(getApplicationContext(), "Redirecting to...", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong log in details, please try again", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Email/Password must not be empty", Toast.LENGTH_LONG).show();
                }

            }


        });
    }
}
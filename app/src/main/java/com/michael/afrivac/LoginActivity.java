package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;


    private EditText email, password;
    private TextView sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //initializing Google Signup features
        signInButton = findViewById(R.id.signin_with_google);
        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        sign_in = findViewById(R.id.singin_into_account);

        //Configure sign in to request the user ID, Email Adress and basic
        //profile ID, and Basic profile are included in the DEFAULT_SIGN_IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Build a GoogleSignInClient with options specified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.signin_with_google).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signin_with_google:
                    signIn();
                    break;
                // ...
            }

        }
        private void signIn() {
            Intent googleSignInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(googleSignInIntent, RC_SIGN_IN);

        }
        public void onActivityActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

            //Result returned from launching the intent from GooglSignInClient.getSignInIntent(---);
            if (requestCode == RC_SIGN_IN){
            //The Task returned from this call is always completed, no need to attach a listener
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
        private void handleSignInResult(Task<GoogleSignInAccount>completedTask){
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                //SignIn successfully, show authentication UI
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } catch (ApiException e){
                //the ApiException code status indicates the detailed failure reason
                //please refer to to the GoogleSignInStatusCodes class reference for more information
                Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode() );
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
            }
        }



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

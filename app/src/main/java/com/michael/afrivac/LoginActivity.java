package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michael.afrivac.Auth.AuthViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.Auth.AuthViewModel;
import com.michael.afrivac.Util.Helper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;

    private EditText email, password;
    private String Email, Password;
    private TextView sign_in, signUp, forgotPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private AuthViewModel authViewModel;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing Google Signup features
//        signInButton = findViewById(R.id.signin_with_google);
        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        sign_in = findViewById(R.id.singin_into_account);
        signUp = findViewById(R.id.singin_goto_signup);
        forgotPassword = findViewById(R.id.signin_forgot_password);

        helper = new Helper();

        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();


        mAuth = FirebaseAuth.getInstance();
        authViewModel = new AuthViewModel();
        helper = new Helper();

        sign_in = findViewById(R.id.singin_into_account);
        signUp = findViewById(R.id.singin_goto_signup);
        forgotPassword = findViewById(R.id.signin_forgot_password);


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.logIn(v);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.gotoSignUpActivity(getApplicationContext());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
                finish();
            }
        });

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In error", "signInResult:failed code="+ e.getStatusCode());
            Toast.makeText(LoginActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
        }
    }
//    @Override
//    protected void onStart(){
//        // Check for existing Google Sign In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//            if (account != null){
//                startActivity(new Intent (LoginActivity.this, MainActivity.class));
//            }
//            super.onStart();


    private void checkNetwork(){
        if (isNetWorkAvailable()){
            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Log.d("login", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else {
                        Log.w("sign in", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, getString(R.string.authFailed), Toast.LENGTH_LONG).show();
                    }

                }
            });
        }else {
            Toast.makeText(getApplicationContext(), getString(R.string.netError), Toast.LENGTH_LONG).show();
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
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            helper.toastMessage(this, getString(R.string.userId)+ user.getUid());
            helper.gotoMainActivity(this);
        }

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            startActivity(new Intent (LoginActivity.this, MainActivity.class));
        }
        super.onStart();

    }


}


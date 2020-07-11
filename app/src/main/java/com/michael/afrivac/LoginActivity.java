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
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.appevents.AppEventsLogger;

import com.github.aakira.compoundicontextview.CompoundIconTextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.Auth.AuthViewModel;
import com.michael.afrivac.Util.Helper;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;

    private EditText email, password;
    private String Email, Password;
    private TextView sign_in, signUp, forgotPassword, resend_email_ver;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private AuthViewModel authViewModel;
    private Helper helper;
    private Button googleSignIn;
    private Animation animation;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing Google Signup features
//        signInButton = findViewById(R.id.signin_with_google);
        resend_email_ver = findViewById(R.id.resend_email_verification);
        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        sign_in = findViewById(R.id.singin_into_account);
        signUp = findViewById(R.id.singin_goto_signup);
        forgotPassword = findViewById(R.id.signin_forgot_password);

        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        FacebookSdk.sdkInitialize(getApplicationContext());
        loginButton = findViewById(R.id.signin_with_facebook);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
                Toast.makeText(LoginActivity.this, "Facebook login success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Facebook login failed", Toast.LENGTH_SHORT).show();

            }
        });

        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        setupFirebaseAuth();

        helper = new Helper(this);


        mAuth = FirebaseAuth.getInstance();
        authViewModel = new AuthViewModel();

        sign_in = findViewById(R.id.singin_into_account);
        googleSignIn = findViewById(R.id.signin_with_google);
        //facebookSignIn = findViewById(R.id.signin_with_facebook);
        signUp = findViewById(R.id.singin_goto_signup);
        forgotPassword = findViewById(R.id.signin_forgot_password);

        resend_email_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendVerificationDialog verificationDialog = new ResendVerificationDialog();
                verificationDialog.show(getSupportFragmentManager(), "resend_verification_dialog");
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                sign_in.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(!isEmpty(email.getText().toString()) && !isEmpty(password.getText().toString())){
                            helper.progressDialogStart("Login to User Account", "Please wait while we log-in into your account");
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            helper.progressDialogEnd();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    helper.progressDialogEnd();
                                    String message = e.getMessage();
                                    Toast.makeText(LoginActivity.this, "Error Occurred " + message,
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else if(isEmpty(email.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                        } else if (isEmpty(password.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                        } else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                            Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                googleSignIn.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO: continue the OnClickListener implementation
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
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

                startActivity(new Intent(getApplicationContext(), ForgotAuthFirstActivity.class));

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

    public void handleFacebookToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                }
            }
        });

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
//    @Override
//    public void onStart() {
//        super.onStart();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user != null){
//            helper.toastMessage(this, getString(R.string.userId)+ user.getUid());
//            helper.gotoMainActivity(this);
//        }
//
//        // Check for existing Google Sign In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null){
//            startActivity(new Intent (LoginActivity.this, MainActivity.class));
//        }
//        super.onStart();
//
//    }

    private void setupFirebaseAuth(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    if (user.isEmailVerified()){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, "Authenticated with " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Please check your email inbox for your verification link",
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            startActivity(new Intent (LoginActivity.this, MainActivity.class));
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
    private boolean isEmpty(String string) {
        return string.equals("");
    }


}


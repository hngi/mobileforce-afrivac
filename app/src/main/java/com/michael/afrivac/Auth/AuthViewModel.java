package com.michael.afrivac.Auth;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.LoginActivity;
import com.michael.afrivac.R;
import com.michael.afrivac.Util.Helper;

import java.util.HashMap;

public class AuthViewModel {

    private Helper helper = new Helper();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //reference to realtime database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference().child("Users");

    private EditText email, password, confirmPassword, userName, phoneNumber;
    Spinner country;
    private String emailStr, passwordStr, confirmPasswordStr, userNameStr, phoneNumberStr, countryStr;
    private static final String TAG = "com.michael.afrivac.Auth.AuthViewModel.allen";

    public void SignUp(@NonNull final View view){
        email = view.getRootView().findViewById(R.id.email);
        password = view.getRootView().findViewById(R.id.password);
        confirmPassword = view.getRootView().findViewById(R.id.confirmpassword);
        userName = view.getRootView().findViewById(R.id.username);
        phoneNumber = view.getRootView().findViewById(R.id.phone);
        country = view.getRootView().findViewById(R.id.country);

        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        confirmPasswordStr = confirmPassword.getText().toString();
        userNameStr = userName.getText().toString();
        phoneNumberStr = phoneNumber.getText().toString();
        countryStr = country.toString();

        final Helper helper1 = new Helper(view.getContext());

        if(emailStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter Email Address");
            email.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            helper.toastMessage(view.getContext(), "Valid Email Required");
        }else if(userNameStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter username");
            userName.requestFocus();
        }else if(passwordStr.trim().isEmpty() || confirmPasswordStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Fill Password field");
            password.requestFocus();
        }else if(passwordStr.length() <6 || confirmPasswordStr.length() < 6){
            helper.toastMessage(view.getContext(), "Your password must be at least 6 characters long");
        }else if(!confirmPasswordStr.equals(passwordStr)){
            helper.toastMessage(view.getContext(), "Passwords do not Match");
        }else if(phoneNumberStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter a Phone Number");
            phoneNumber.requestFocus();
        }else if(!Patterns.PHONE.matcher(phoneNumberStr).matches()){
            helper.toastMessage(view.getContext(), "Enter a valid phone number");
        }else if(countryStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter your country of origin");
            country.requestFocus();
        }else{
            //helper function to start progress dialog
            helper1.progressDialogStart("Login to User Account", "Please wait while we log-in into your account");

            mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "createUserWithEmail: success");
//                        helper.toastMessage(view.getContext(), "You are logged in successfully");

                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        assert firebaseUser != null;
                        String userID = firebaseUser.getUid();
                        String fullName = firebaseUser.getDisplayName();
                        DatabaseReference reference = databaseReference.child(userID);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userID);
                        hashMap.put("Full Name", fullName);
                        hashMap.put("username", userNameStr);
                        hashMap.put("email", emailStr);
                        hashMap.put("number", phoneNumberStr);
                        hashMap.put("country", countryStr);
                        hashMap.put("profileImageUrl", "default");
                        hashMap.put("language", "default");
                        hashMap.put("gender", "default");
                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    helper1.progressDialogEnd();
                                    Log.d(TAG, "Save Account Info: success");

                                    //call the email verification method and sign the user.
                                    // Redirect them to the login activity
                                    sendVerEmail(view);
                                    FirebaseAuth.getInstance().signOut();
                                    helper.gotoLoginAcitivity(view.getContext());
                                }else {
                                    helper1.progressDialogEnd();
                                    Log.w(TAG, "saveAccountInfo:failure", task.getException());
                                    helper.toastMessage(view.getContext(), "Failed to save account info \n" + task.getException().getMessage());
                                }
                            }
                        });
                    }else{
                        //dismiss progress dialog
                        helper1.progressDialogEnd();
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        helper.toastMessage(view.getContext(), "Failed to Register \n" + task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void logIn(@NonNull final View view){
        email = view.getRootView().findViewById(R.id.signin_email);
        password = view.getRootView().findViewById(R.id.signin_password);

        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();

        final Helper helper1 = new Helper(view.getContext());

        if(emailStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Enter Email Address");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            helper.toastMessage(view.getContext(), "Valid Email Required");
        }else if(passwordStr.trim().isEmpty()){
            helper.toastMessage(view.getContext(), "Fill Password field");
        }else if(passwordStr.length() <6){
            helper.toastMessage(view.getContext(), "Fill Password field");
        }else{
            //helper function to start progress dialog
            helper1.progressDialogStart("Login to User Account", "Please wait while we log-in into your account");

            mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        helper1.progressDialogEnd();
                        Log.d(TAG, "signInWithEmail: Success");
                        helper.gotoMainActivity(view.getContext());
                        helper.toastMessage(view.getContext(), "You are logged in successfully");
                    }
                    else{
                        helper1.progressDialogEnd();
                        Log.d(TAG, "signInWithEmail: Failure", task.getException());
                        helper.toastMessage(view.getContext(), "Failed to login \n" + task.getException().getMessage());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.getMessage();
                    helper.toastMessage(view.getContext(), "Error Occurred " + message);
                }
            });
        }
    }

    //method for sending email verification upon registration(sign-up)
    private void sendVerEmail(@NonNull final View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=  null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(view.getContext(), "Email verification sent " +
                                                "\nPlease check your inbox for your verification link",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(view.getContext(), "Could not send Email verification sent ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}

package com.michael.afrivac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ResendVerificationDialog extends DialogFragment {

    private static final String TAG = "ResendVerificationDialo";

    //widgets
    private EditText mConfirmPassword, mConfirmEmail;

    //vars
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.resend_verification_dialog, container, false);
        mConfirmPassword = view.findViewById(R.id.confirm_password);
        mConfirmEmail =  view.findViewById(R.id.confirm_email);
        mContext = getActivity();

        TextView confirmDialog = view.findViewById(R.id.dialogConfirm);
        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isEmpty(mConfirmEmail.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())){

                    //temporarily authenticate and resend verification email
                    authenticateAndResendEmail(mConfirmEmail.getText().toString(),
                            mConfirmPassword.getText().toString());
                }else{
                    Toast.makeText(mContext, "all fields must be filled out", Toast.LENGTH_SHORT).show();
                }


            }
        });

        TextView cancel = view.findViewById(R.id.dialogCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });


        return view;
    }

    /**
     * reauthenticate so we can send a verification email again
     * @param email
     * @param password
     */
    private void authenticateAndResendEmail(String email, String password) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendVerificationEmail();
                            FirebaseAuth.getInstance().signOut();
                            getDialog().dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.getMessage();
                Toast.makeText(mContext, message + "\nCould not send email", Toast.LENGTH_LONG).show();
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });
    }

    /**
     * sends an email verification link to the user
     */
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(mContext, "couldn't send email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    /**
     * Return true if the @param is null
     * @param string
     * @return
     */
    private boolean isEmpty(String string){
        return string.equals("");
    }
}
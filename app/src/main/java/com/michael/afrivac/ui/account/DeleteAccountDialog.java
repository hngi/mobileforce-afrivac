package com.michael.afrivac.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.michael.afrivac.LoginActivity;
import com.michael.afrivac.R;

import java.util.Objects;


public class DeleteAccountDialog extends DialogFragment {
    private EditText mConfirmPassword, mConfirmEmail;
    private Context mContext;

    public DeleteAccountDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delete_account, container, false);

        mConfirmPassword = view.findViewById(R.id.confirm_password);
        mConfirmEmail =  view.findViewById(R.id.confirm_email);
        mContext = getActivity();


        TextView confirm = view.findViewById(R.id.confirm_text);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });

        TextView cancel = view.findViewById(R.id.cancel_text);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        return view;

    }

    private void deleteAccount(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(mConfirmEmail.getText().toString(), mConfirmPassword.getText().toString());
        assert currentUser != null;
        currentUser.reauthenticate(credential)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    currentUser.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(mContext, "Account successfully deleted", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.getMessage();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
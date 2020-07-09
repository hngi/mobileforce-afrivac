package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michael.afrivac.Auth.AuthViewModel;
import com.michael.afrivac.Util.Helper;

import java.util.Scanner;

public class SignUpActivity extends AppCompatActivity {

    Helper helper;
    private AuthViewModel authViewModel;

    EditText Username;
    CheckBox mCheckBox;
    EditText Email;
    EditText Phone;
    EditText Country;
    EditText Password;
    EditText ConfirmPassword;
    TextView ToSignIn;
    Button  signUp;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        authViewModel = new AuthViewModel();
        helper = new Helper();
        mCheckBox = findViewById(R.id.checkBox);
        ToSignIn = findViewById(R.id.toSignIn);
        signUp = findViewById(R.id.signUp);
        ConfirmPassword = findViewById(R.id.confirmpassword);

        ConfirmPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    signUp.callOnClick();
                }
                return false;
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //--if the Terms & Conditions checkedBox
                if (mCheckBox.isChecked()) {

                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                    signUp.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            authViewModel.SignUp(v);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                }else{
                    Toast.makeText(SignUpActivity.this, "Check Terms and Agreements Box", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        helper.gotoLoginAcitivity(getApplicationContext());
    }
}

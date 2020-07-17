package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.michael.afrivac.Auth.AuthViewModel;
import com.michael.afrivac.Util.Helper;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    Helper helper;
    private AuthViewModel authViewModel;

    EditText Username;
    CheckBox mCheckBox;
    EditText email;
    EditText phone;
    Spinner country;
    EditText password;
    EditText confirmPassword;
    TextView ToSignIn;
    Button  signUp;
    Animation animation;
    private CountryCodePicker mCodePicker;
    TextView termsAndConditons;
    private  final String signUP_URL ="http://piscine-mandarine-32869.herokuapp.com/api/v1/auth/signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        authViewModel = new AuthViewModel();
        helper = new Helper();

        Username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        country = findViewById(R.id.country);
        mCheckBox = findViewById(R.id.checkBox);
        ToSignIn = findViewById(R.id.toSignIn);
        signUp = findViewById(R.id.signUp);
        confirmPassword = findViewById(R.id.confirmpassword);
       phone = findViewById(R.id.phone);
        //ConfirmPassword = findViewById(R.id.confirmpassword);
        termsAndConditons = findViewById(R.id.termsAndConditions);

        mCodePicker = findViewById(R.id.ccp);

        mCodePicker.registerCarrierNumberEditText(phone);


        confirmPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    signUp.callOnClick();
                }
                return false;
            }
        });



        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                            String Email = email.getText().toString();
                            String Password = password.getText().toString();
                            String Phone = phone.getText().toString();
                            String Name = Username.getText().toString();
                            String Country = country.toString();

                            RegisterUser userReg = new RegisterUser();
                            userReg.execute(Email, Password, Phone, Name, Country);
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
        //This Sign up button works for Api authentication

//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String Email = email.getText().toString().trim();
//                final String Password = password.getText().toString().trim();
//                final String Phone = phone.getText().toString().trim();
//                new RegisterUser().execute(Email,Phone,Password);
//
//            }
//        });

        ToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        termsAndConditons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TermsAndConditions.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        helper.gotoLoginAcitivity(getApplicationContext());
    }
    public  class RegisterUser extends AsyncTask<String, Void , String>{

        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Password = strings[1];
            String PhoneNumber = strings[2];
            String Name = strings[3];
            String Country = strings[4];
            String finalURL = signUP_URL;

            RequestBody requestDetails = new FormBody.Builder()
                    .add("name", Name)
                    .add("email", Email)
                    .add("number", PhoneNumber)
                    .add("password", Password)
                    .add("country", Country)
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://piscine-mandarine-32869.herokuapp.com/api/v1/auth/signup")
                    .post(requestDetails)
                    .build();
            //Response response = null;

            try {
                Response response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()) {
                    String result = response.body().string();
                    Log.i("SignupResponse", "response successful");
                    if (result.equalsIgnoreCase("1000")) {
                       showToast("Registration Successful");
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }else if(result.equalsIgnoreCase("1001")){
                        showToast("User Already Exist");
                    }else{
                        showToast("Oops try again");
                    }
                }else {
                    Log.i("SignupResponse", "Unsuccessful");
                }

                    }catch (Exception e){
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(SignUpActivity.this, "loading", Toast.LENGTH_SHORT).show();
        }
    }
    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignUpActivity.this,Text,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

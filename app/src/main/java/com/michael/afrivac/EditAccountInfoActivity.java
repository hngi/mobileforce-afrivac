package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michael.afrivac.ui.account.AccountFragment;

public class EditAccountInfoActivity extends AppCompatActivity {

    EditText User_name_edit2, Edit_phone2, User_location2, User_gender, User_language2;
    TextView  Edit_email2;
    Button Btn_save;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String userID;

    public void SaveDetails(View view) {

        String newName = User_name_edit2.getText().toString();
        String newPhone = Edit_phone2.getText().toString();
        String newCountry = User_location2.getText().toString();
        String newGender = User_gender.getText().toString();
        String newLanguage = User_language2.getText().toString();

        mDatabase.child("username").setValue(newName);
        mDatabase.child("number").setValue(newPhone);
        mDatabase.child("country").setValue(newCountry);
        mDatabase.child("gender").setValue(newGender);
        mDatabase.child("language").setValue(newLanguage);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Toast.makeText(this, "Your details have been updated successfully!", Toast.LENGTH_SHORT).show();

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_fragment);

        User_name_edit2 = findViewById(R.id.user_name_edit2);
        Edit_phone2 = findViewById(R.id.edit_phone2);
        User_location2 = findViewById(R.id.user_location2);
        User_language2 = findViewById(R.id.user_language2);
        User_gender = findViewById(R.id.user_gender);
        Edit_email2 = findViewById(R.id.edit_email2);
        Btn_save = findViewById(R.id.btn_save);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String user_name = snapshot.child("username").getValue().toString();
                String user_gender = snapshot.child("gender").getValue().toString();
                String user_email = snapshot.child("email").getValue().toString();
                String user_language = snapshot.child("language").getValue().toString();
                String user_number = snapshot.child("number").getValue().toString();
                String user_country = snapshot.child("country").getValue().toString();

                if(user_name != null) {
                    User_name_edit2.setText(user_name);
                }

                if(user_email != null) {
                    Edit_email2.setText(user_email);
                }

                if(user_gender != null) {
                    User_gender.setText(user_gender);
                }
                if(user_language != null) {
                    User_language2.setText(user_language);
                }
                if(user_number != null) {
                    Edit_phone2.setText(user_number);
                }
                if(user_country != null) {
                    User_location2.setText(user_country);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}

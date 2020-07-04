package com.michael.afrivac;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.Util.Helper;
import com.michael.afrivac.model.Wallet;

public class WalletPageActivity extends AppCompatActivity {

    private String TAG = "WalletPageActivity";

    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    String userID;

    //wallet widgets
    private Helper helper;
    private EditText cardNumber, validUntilDate, cvv, cardName;
    private Button saveDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_page);
        setTitle("Cocoon Hotel");
        helper = new Helper(this);
        cardNumber = findViewById(R.id.credit_card_number);
        validUntilDate = findViewById(R.id.valid_until_date);
        cvv = findViewById(R.id.cvv);
        cardName = findViewById(R.id.cardName);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        saveDetails = findViewById(R.id.saveDetails);
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.progressDialogStart("PLease wait", "Saving Wallet Info");
                String card_number = cardNumber.getText().toString();
                String valid_until_date = validUntilDate.getText().toString();
                String cvv_text = cvv.getText().toString();
                String card_name = cardName.getText().toString();

                Wallet newWallet = new Wallet();
                newWallet.setCardnumber(card_number);
                newWallet.setValiduntil(valid_until_date);
                newWallet.setCvv(Long.parseLong(cvv_text));
                newWallet.setCardname(card_name);

                mDatabase.child("Wallets").child(userID).setValue(newWallet).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            helper.progressDialogEnd();
                            helper.toastMessage(getApplicationContext(), "Wallet Info Successfully Saved");
                            helper.gotoMainActivity(getApplicationContext());
                        }else{
                            helper.progressDialogEnd();
                            helper.toastMessage(getApplicationContext(), "Error Occured \n" + task.getException());
                        }
                    }
                });
            }
        });
    }
}
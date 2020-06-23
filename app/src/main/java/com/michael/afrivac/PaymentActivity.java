package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.michael.afrivac.Util.Helper;

public class PaymentActivity extends AppCompatActivity {

    Helper helper = new Helper();

    private Button confirmButton;

    EditText cardNumber, validity, CVV, cardName;
    private String cardNumberStr;
    private String validityStr;
    private String cvvStr;
    private String cardNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        confirmButton = findViewById(R.id.confirm_button);
        cardNumber = findViewById(R.id.card_number_input);
        validity = findViewById(R.id.valid_until_input);
        CVV = findViewById(R.id.cvv_input);
        cardName = findViewById(R.id.card_name_input);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNumberStr = cardNumber.getText().toString().trim();
                validityStr = validity.getText().toString().trim();
                cvvStr = CVV.getText().toString().trim();
                cardNameStr = cardName.getText().toString().trim();

                checkToProceed();
            }
        });
    }

    public void checkToProceed(){
        if(cardNumberStr.trim().isEmpty()){
            helper.toastMessage(this, "Enter card Number");
            cardNumber.requestFocus();
        }else if(cardNumberStr.length() < 12){
            helper.toastMessage(this, "Enter a valid card number");
            cardNumber.requestFocus();
        }else if(validityStr.trim().isEmpty()){
            helper.toastMessage(this, "Enter validity");
            validity.requestFocus();
        }else if(cvvStr.trim().isEmpty()){
            helper.toastMessage(this, "Enter CVV");
            CVV.requestFocus();
        }else if(cardNameStr.trim().isEmpty()){
            helper.toastMessage(this, "Enter card name");
            cardName.requestFocus();
        }else{
            String array_string[] = {cardNumberStr, validityStr, cvvStr, cardNameStr};
            Intent intent = new Intent(this, payment_page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("array_string", array_string);
            startActivity(intent);
        }
    }
}
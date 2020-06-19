package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public void convertCurrency(View view){
        EditText editText = (EditText) findViewById(R.id.edtText);
        int currency = Integer.parseInt(editText.getText().toString());
//        double dollar;
        double result;

        switch (currency){
            case 1: //Dollar to Naira
                double dollar = 360;
                result = currency * dollar;
                break;
            case 2: //Pounds to Naira
                double pounds = 450;
                result = currency * pounds;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + currency);
        }

        Toast.makeText(MainActivity.this, Double.toString(result), Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
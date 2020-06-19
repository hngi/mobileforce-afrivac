package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final EditText editText;
        final EditText editText2;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

//creates a pop up date picker window for the start date edit text view
//finds the view and then sets the input type to null so the keyboard won't be enabled for the view
        editText = findViewById(R.id.start_date);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker;
                //creates the date picker
                    picker = new DatePickerDialog(BookingActivity.this,
                            //sets a listener to know react when a date has been chosen
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                //sets the text for the edit text view after the user has selected a date
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    editText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();
            }
        });

//creates a pop up date picker window for the start date edit text view
//finds the view and then sets the input type to null so the keyboard won't be enabled for the view
        editText2 = findViewById(R.id.end_date);
        editText2.setInputType(InputType.TYPE_NULL);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker;

                picker = new DatePickerDialog(BookingActivity.this,
                        //sets a listener to know react when a date has been chosen
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            //sets the text for the edit text view after the user has selected a date
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editText2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }
}

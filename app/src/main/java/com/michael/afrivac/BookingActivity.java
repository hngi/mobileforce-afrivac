package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {

    /*
     *creating a booking details class that can hold all the user details so that they can be easily accessible
     */
    public static class BookingDetails{
        EditText checkInDate, checkOutDate; // variables to store the check-in and check-out dates of the user
        EditText numberOfGuests, numberOfRooms; // edit field variables used to take in the number of guest and rooms the user wants
        EditText fullName, Email, additionalInfo;
        CheckBox useCabService, reservingForSomeone;
    }

    ImageView dropDownCheckIn, dropDownCheckOut ; // variable to access the dropdown image view icon.
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button payNowButton, payAtHotel;
    EditText dateSelected, GuestsNumberSelected, RoomsNumberSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        final BookingDetails person = new BookingDetails();
        person.checkInDate =  (EditText) findViewById(R.id.check_in_date);
        person.checkOutDate = (EditText) findViewById(R.id.check_out_date);
        dropDownCheckIn = (ImageView) findViewById(R.id.check_in_arrow);
        payNowButton = (Button) findViewById(R.id.pay_button);
        GuestsNumberSelected = (EditText) findViewById(R.id.no_of_guests);
        RoomsNumberSelected = (EditText) findViewById(R.id.no_of_rooms);
        person.numberOfGuests = GuestsNumberSelected;
        person.numberOfRooms = RoomsNumberSelected;

                                         /*
                                an on-click event is set to the image view attached to the check-in edit view in order to create a calendar dialog
                                that will enable users selected that desired booking date easily.
                                 */
        dropDownCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDate();
                person.checkInDate = dateSelected;
            }
        });
        dropDownCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
                person.checkOutDate = dateSelected;
            }
        });

                                /*
                                    -this code runs when the pay Now button is clicked by the user
                                    - the purpose of the code is to take the user to the next activity where he/she is to pay for her request
                                    -An on-click event is used to trigger the payment button and then the function required to take the user to the next page is called.
                                */
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityBookingPage();
            }
        });

    }           //method declaration for the payment page activity
    public void openActivityBookingPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

                            /*
                               the show date method is called each time the dropdown arrow button is clicked to select a date. the selected date
                               */

    public  void showDate () {

        //this block of code initializes the variables required to store the user wil input
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(BookingActivity.this,
                android.R.style.Theme_Holo_Dialog,
                mDateSetListener, day, month, year);
        dialog.show();

        //date listener method to know the values the user selected and then cast it to an Edit View.
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                month += 1; // the date picker is zero indexed for months, so adding 1 to offset the index value assigned
                String date = day + "/" + month + "/" + year;
                dateSelected.setText(date);

            }
        };
    }


}


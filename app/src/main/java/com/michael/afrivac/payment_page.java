package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.Util.Helper;

import java.util.HashMap;

public class payment_page extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference data;
    private Helper helper;

    private Button payButton;
    private TextView roomType, roomNumber, guest, numberofNight, payAmount, hotelName;
    private String roomTypeStr;
    private String roomNUmberStr;
    private String guestStr;
    private String numberOfNightStr;
    private String payAmountStr;
    private String hotelNameStr;
    public String TAG = "com/michael/afrivac/payment_page.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        user = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance().getReference().child("RoomPaymentInfo").child(user.getUid());
        helper = new Helper();

        payButton = findViewById(R.id.button_pay);
        roomType = findViewById(R.id.deluxe_room);
        roomNumber = findViewById(R.id.some_id);
        guest = findViewById(R.id.adults);
        numberofNight = findViewById(R.id.some_id1);
        payAmount = findViewById(R.id.pay_amount);
        hotelName = findViewById(R.id.cocoon_hotel);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomTypeStr = roomType.getText().toString().trim();
                roomNUmberStr = roomNumber.getText().toString().trim();
                guestStr = guest.getText().toString().trim();
                numberOfNightStr = numberofNight.getText().toString().trim();
                payAmountStr = payAmount.getText().toString().trim();

                //hotel name
                hotelNameStr = hotelName.getText().toString().trim();

                confirmPayment();

            }
        });
    }

    public void confirmPayment(){
        final Helper helper1 = new Helper(this);
        DatabaseReference hotelData = data.child("HotelName");

        final DatabaseReference roomData = hotelData.child("roomData");
        final HashMap<String, String> roomDetailsHashMap = new HashMap<>();
        roomDetailsHashMap.put("roomType", roomTypeStr);
        roomDetailsHashMap.put("roomNumber", roomNUmberStr);
        roomDetailsHashMap.put("guest", guestStr);
        roomDetailsHashMap.put("numberofNight", numberOfNightStr);
        roomDetailsHashMap.put("payAmount", payAmountStr);

        DatabaseReference paymentData = hotelData.child("paymentData");
        Intent intent = getIntent();
        String array_string[] = intent.getStringArrayExtra("array_string");
        HashMap<String, String> paymentDetailsHashMap = new HashMap<>();
        paymentDetailsHashMap.put("cardNumber", array_string[0]);
        paymentDetailsHashMap.put("validity", array_string[1]);
        paymentDetailsHashMap.put("CVV", array_string[2]);
        paymentDetailsHashMap.put("cardName", array_string[3]);
        paymentData.setValue(paymentDetailsHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    helper1.progressDialogStart("Payment Ongoing", "Please wait a while");
                    Log.d(TAG, "room payment: success");
                    helper.toastMessage(getApplicationContext(), "payment successful");

                    roomData.setValue(roomDetailsHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                helper1.progressDialogStart("Room Reservation Ongoing", "Please wait a while");
                                Log.d(TAG, "room reservation: success");
                                helper.toastMessage(getApplicationContext(), "Room Reservation successful");
                                helper.gotoMainActivity(getApplicationContext());
                            }else{
                                helper1.progressDialogEnd();
                                Log.w(TAG, "room reservation: failure", task.getException());
                                helper.toastMessage(getApplicationContext(), "room reservation failed\n" + task.getException().getMessage());
                            }
                        }
                    });

                }else{
                    helper1.progressDialogEnd();
                    Log.w(TAG, "room payment: failure", task.getException());
                    helper.toastMessage(getApplicationContext(), "payment and room reservation failed \n" + task.getException().getMessage());
                }
            }
        });
    }
}

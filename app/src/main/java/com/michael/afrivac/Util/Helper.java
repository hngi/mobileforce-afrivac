package com.michael.afrivac.Util;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.michael.afrivac.BookingActivity;
import com.michael.afrivac.LocationActivity;
import com.michael.afrivac.LoginActivity;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.SignUpActivity;

public class Helper {
    Intent intent;
    public Helper(){
    }

    public void toastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void gotoMainActivity(Context context) {
        intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void gotoSignUpActivity(Context context) {
        intent = new Intent(context, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void gotoLoginAcitivity(Context context) {
        intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void gotoBookingActivity(Context context){
        intent = new Intent(context, BookingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /*public void gotoBookingPagActivity(Context context){
        intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }*/

    /*public void gotoForgotPasswordActivity(Context context){
        intent = new Intent(context, ForgetPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void gotoContactUsActivity(Context context){
        intent = new Intent(context, ContactUsActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }*/

    public void gotoLocationActivity(Context context){
        intent = new Intent(context, LocationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /*public void gotoProfilePageActivity(Context context){
        intent = new Intent(context, ProfilePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }*/
}

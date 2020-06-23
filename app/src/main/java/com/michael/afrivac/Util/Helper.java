package com.michael.afrivac.Util;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.michael.afrivac.BookingActivity;
import com.michael.afrivac.LocationActivity;
import com.michael.afrivac.LoginActivity;
import com.michael.afrivac.MainActivity;
import com.michael.afrivac.PaymentActivity;
import com.michael.afrivac.SignUpActivity;
import com.michael.afrivac.payment_page;

public class Helper {
    Context context;
    Intent intent;
    ProgressDialog progressDialog;
    public Helper(){
    }

    public Helper(Context context){
        this.context = context;
    }

    //progress dialog functions
    public void progressDialogStart(String titleMessage, String detailMessage){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(titleMessage);
        progressDialog.setMessage(detailMessage);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);
    }

    public void progressDialogEnd(){
        progressDialog.dismiss();
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

    public void gotoPaymentActivity(Context context){
        intent = new Intent(context, PaymentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void gotoConfirmationPageActivity(Context context){
        intent = new Intent(context, payment_page.class);
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

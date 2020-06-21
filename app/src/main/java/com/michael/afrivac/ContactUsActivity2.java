package com.michael.afrivac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUsActivity2 extends AppCompatActivity {
    private EditText contactUsMessage;
    private Button sendmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us2);
        contactUsMessage=findViewById(R.id.editText_contactus);
        sendmessage=findViewById(R.id.button_contactus);
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message=contactUsMessage.getText().toString().trim();

                if (message.isEmpty()||message.length()==0||message==null){
                    contactUsMessage.requestFocus();
                    contactUsMessage.setError("Please enter a Message");
                }else{
                    try {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"faithckorir@gmail.com"});
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Message");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);

                    }catch (Exception e){
                        Toast.makeText(ContactUsActivity2.this, "No Email widget found", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
}
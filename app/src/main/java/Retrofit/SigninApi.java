package Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.michael.afrivac.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninApi extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPassword, userName, phoneNumber;
    private String email, password, confirmPasswordStr, name, number, country, countryCode, phoneNumberWithCountryCode;
    Spinner countrySpinner;
    Button signUp;
    private CountryCodePicker mCodePicker, cc;
//    String signUpUrl = "https://piscine-mandarine-32869.herokuapp.com/api/v1/auth/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        userName = findViewById(R.id.username);
        countrySpinner = findViewById(R.id.country);
        phoneNumber = findViewById(R.id.phone);

        signUp = findViewById(R.id.signUp);

        mCodePicker = findViewById(R.id.ccp);

//        phoneNumberWithCountryCode = mCodePicker.getFullNumberWithPlus() + phoneNumber.getText().toString().replaceAll(" ", "");

        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        confirmPasswordStr = confirmPassword.getText().toString().trim();
        name = userName.getText().toString().trim();
        country = countrySpinner.toString().trim();
        number = phoneNumber.getText().toString().trim();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    register();

            }
        });
    }

    private void register() {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().createUser(name, email, password, number, country);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Toast.makeText(SigninApi.this, s, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

package com.example.otp_verification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class enter_mobile_number_screen extends AppCompatActivity {
    EditText InputMobileNumber;
    Button ButtonGetOtp;
    ProgressBar ProgressBarSendingOtp;
    LinearLayout Input_Number_Layout;
    ImageView github_link;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_mobile_number_screen);

        /*<------------Night mode disable--------->*/

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        /*----------------Hooks-----------------*/

        InputMobileNumber = findViewById(R.id.Input_Mobile_Number);

        ButtonGetOtp = findViewById(R.id.Button_Get_Otp);

        ProgressBarSendingOtp = findViewById(R.id.Progress_Bar_Sending_Otp);

        Input_Number_Layout = findViewById(R.id.Input_Number_Layout);

        github_link = findViewById(R.id.GitHub_Link);

        /*<------------Handle_Github_link_On_click_Listener--------->*/

        github_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/joymridha2004/OTP-Verification-Java-Application"));
                startActivity(intent);
            }
        });

        /*----------------Focus Input Layout-----------------*/

        Input_Number_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMobileNumber.requestFocus();
            }
        });

        ButtonGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InputMobileNumber.getText().toString().trim().isEmpty()) {
                    if (InputMobileNumber.getText().toString().trim().length() == 10) {

                        ProgressBarSendingOtp.setVisibility(View.VISIBLE);
                        ButtonGetOtp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + InputMobileNumber.getText().toString(), 60, TimeUnit.SECONDS, enter_mobile_number_screen.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                ProgressBarSendingOtp.setVisibility(View.GONE);
                                ButtonGetOtp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                ProgressBarSendingOtp.setVisibility(View.GONE);
                                ButtonGetOtp.setVisibility(View.VISIBLE);
                                Toast.makeText(enter_mobile_number_screen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String BackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                ProgressBarSendingOtp.setVisibility(View.GONE);
                                ButtonGetOtp.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(), verify_enter_otp.class);
                                intent.putExtra("Mobile_Number", InputMobileNumber.getText().toString());
                                intent.putExtra("BackendOtp", BackendOtp);
                                startActivity(intent);
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
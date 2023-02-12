package com.example.otp_verification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verify_enter_otp extends AppCompatActivity {
    EditText InputOtp1, InputOtp2, InputOtp3, InputOtp4, InputOtp5, InputOtp6;
    Button ButtonOtpSubmit;

    String GetOTPBackend;

    ProgressBar ProgressBarVerifyOtp;

    TextView OtpResendTV, ShowMobileNumberTV;

    ImageView github_link_2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_enter_otp);
        /*----------------Hooks-----------------*/

        InputOtp1 = findViewById(R.id.Input_Otp_1);
        InputOtp2 = findViewById(R.id.Input_Otp_2);
        InputOtp3 = findViewById(R.id.Input_Otp_3);
        InputOtp4 = findViewById(R.id.Input_Otp_4);
        InputOtp5 = findViewById(R.id.Input_Otp_5);
        InputOtp6 = findViewById(R.id.Input_Otp_6);

        ButtonOtpSubmit = findViewById(R.id.Button_Otp_Submit);

        ShowMobileNumberTV = findViewById(R.id.Show_Mobile_Number_TV);

        ProgressBarVerifyOtp = findViewById(R.id.Progress_Bar_Verify_Otp);

        OtpResendTV = findViewById(R.id.Otp_Resend_TV);

        github_link_2 = findViewById(R.id.GitHub_Link_2);
        /*----------------Get Mobile Number-----------------*/

        ShowMobileNumberTV.setText(String.format("+91-%s", getIntent().getStringExtra("Mobile_Number")));

        GetOTPBackend = getIntent().getStringExtra("BackendOtp");

        /*<------------Handle_Github_link_On_click_Listener--------->*/

        github_link_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/joymridha2004/OTP-Verification-Java-Application"));
                startActivity(intent);
            }
        });


        /*----------------Set On Click Listener On ButtonOtpSubmit-----------------*/

        ButtonOtpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!InputOtp1.getText().toString().trim().isEmpty() && !InputOtp2.getText().toString().trim().isEmpty() && !InputOtp3.getText().toString().trim().isEmpty() && !InputOtp4.getText().toString().trim().isEmpty() && !InputOtp5.getText().toString().trim().isEmpty() && !InputOtp6.getText().toString().trim().isEmpty()) {
                    String EnterCodeOtp = InputOtp1.getText().toString() +
                            InputOtp2.getText().toString() +
                            InputOtp3.getText().toString() +
                            InputOtp4.getText().toString() +
                            InputOtp5.getText().toString() +
                            InputOtp6.getText().toString();

                    if (GetOTPBackend != null) {
                        ProgressBarVerifyOtp.setVisibility(View.VISIBLE);
                        ButtonOtpSubmit.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(GetOTPBackend, EnterCodeOtp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                ProgressBarVerifyOtp.setVisibility(View.GONE);
                                ButtonOtpSubmit.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), dash_board.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "OTP Verify", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(verify_enter_otp.this, "Please the Correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter All Number", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter All Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        NumberOtpMove();
        OtpResendTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("Mobile_Number"), 60, TimeUnit.SECONDS, verify_enter_otp.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(verify_enter_otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String NewBackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        GetOTPBackend = NewBackendOtp;
                        Toast.makeText(verify_enter_otp.this, "OTP Resend Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void NumberOtpMove() {
        InputOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    InputOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        InputOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    InputOtp3.requestFocus();
                } else if (charSequence.toString().trim().isEmpty()) {
                    InputOtp1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        InputOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    InputOtp4.requestFocus();
                } else if (charSequence.toString().trim().isEmpty()) {
                    InputOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        InputOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    InputOtp5.requestFocus();
                } else if (charSequence.toString().trim().isEmpty()) {
                    InputOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        InputOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    InputOtp6.requestFocus();
                } else if (charSequence.toString().trim().isEmpty()) {
                    InputOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        InputOtp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()) {
                    InputOtp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
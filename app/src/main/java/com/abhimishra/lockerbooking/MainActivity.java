package com.abhimishra.lockerbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyCallback;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView mobileNumberText;
    private EditText enteredMobileNumberField;
    private TextView loginRefID;
    private Button getOtpBtn;
    private Button verifyOtpBtn;
    private EditText enterOtpField;
    private String verificationId;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        mAuth = FirebaseAuth.getInstance();
        refIdlinkClickMethod();
        getOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // What happens after clicking Send OTP button
                if (TextUtils.isEmpty(enteredMobileNumberField.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter valid phone number", Toast.LENGTH_LONG).show();
                } else {
                    String phone = enteredMobileNumberField.getText().toString();
                    bar.setVisibility(View.VISIBLE);
                    sendVerificationCode(phone);
                }
            }
        });

        verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(enterOtpField.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Wrong Otp Entered", Toast.LENGTH_LONG).show();
                } else {
                    verifyCode(enterOtpField.getText().toString());
                }

            }
        });


    }

    private void refIdlinkClickMethod() {
        loginRefID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRefIDLinkClick();

            }
        });
    }

    private void loginRefIDLinkClick() {
        // // write a logic of what happens when click on Ref ID link on login page
        startActivity(new Intent(MainActivity.this, login_Ref_Id.class));
    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(MainActivity.this, "Verification failed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

            super.onCodeSent(s, token);
            verificationId = s;
            Toast.makeText(MainActivity.this, "code Sent", Toast.LENGTH_SHORT).show();
            verifyOtpBtn.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);

        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, Home_Activity.class));
                        }

                    }
                });
    }

    private void findViews() {
        mobileNumberText = findViewById(R.id.text_view_mobile_number);
        enteredMobileNumberField = findViewById(R.id.edit_text_mobile_number_in_box);
        loginRefID = findViewById(R.id.text_view_ref_id);
        getOtpBtn = findViewById(R.id.get_otp_btn);
        enterOtpField = findViewById(R.id.edit_text_Enter_Otp);
        verifyOtpBtn = findViewById(R.id.button_idBtnVerify);
        bar = findViewById(R.id.progress_bar_loginPage);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(MainActivity.this, Home_Activity.class));
            finish();
        }
    }
}
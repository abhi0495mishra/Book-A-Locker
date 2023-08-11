package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.REFERENCE_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DAORepositoryImpl;
import com.abhimishra.lockerbooking.databases.DatabaseContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ForgotReferenceId extends AppCompatActivity {


    private TextView enteredMobNoTxt;
    private EditText enteredMobNo;
    private Button getOtpBtn;
    private EditText enteredOtp;
    private Button verifyOtpBtn;
    private ProgressBar bar;

    private String refID;

    private TextView referenceIDFromDB;

    private TextView backToLoginPage;
    private DAORepositoryImpl dbRepository;
    private FirebaseAuth mAuth;
    private String verificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_reference_id);

        findViewOnForgotRefIDPage();

        //Initialize the dbRepository of class DAORepositoryImpl
        dbRepository = new DAORepositoryImpl(getBaseContext());

        mAuth = FirebaseAuth.getInstance();

        getOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dbRepository.checkIfMobileNumberAlreadyExists(enteredMobNo.getText().toString())) {

                    if (TextUtils.isEmpty(enteredMobNo.getText().toString())) {
                        Toast.makeText(ForgotReferenceId.this, "Please enter valid phone number", Toast.LENGTH_LONG).show();
                    } else {
                        String phone = enteredMobNo.getText().toString();
                        bar.setVisibility(View.VISIBLE);

                        //Fetch Reference ID from DB and show to the user
                        refID = dbRepository.fetchReferenceID(enteredMobNo.getText().toString());
                        referenceIDFromDB.setText("Reference ID is " + refID );

                        //sendVerificationCode(phone);
                        verifyOtpBtn.setEnabled(true);
                        bar.setVisibility(View.INVISIBLE);
                    }

                } else {

                    Toast.makeText(ForgotReferenceId.this, "User does not exist. Kindly register on Sign up page", Toast.LENGTH_LONG).show();
                }


            }
        });


        verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(enteredOtp.getText().toString())) {
                    Toast.makeText(ForgotReferenceId.this, "Please enter OTP", Toast.LENGTH_LONG).show();
                } else {

                    //TODO - Implement Error handling for Invalid user (FirebaseAuthInvalidUserException)

                    //Verify OTP
                    verifyCode(enteredOtp.getText().toString());

                    //Fetch Reference ID from DB and show to the user
                    String refID = dbRepository.fetchReferenceID(enteredMobNo.getText().toString());
                    referenceIDFromDB.setText("Reference ID is " + refID );

                }

            }
        });

        backToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotReferenceId.this, login_Ref_Id.class);
                intent.putExtra(REFERENCE_ID,refID);
                startActivity(intent);
            }
        });

    }

    private void findViewOnForgotRefIDPage() {

        enteredMobNoTxt = findViewById(R.id.text_view_enter_registered_mobile_number);
        enteredMobNo = findViewById(R.id.edit_text_enter_registered_mobile_number);
        getOtpBtn = findViewById(R.id.get_otp_btn_forgotRef_page);
        enteredOtp =  findViewById(R.id.edit_text_Enter_Otp_forgotRef_page);
        verifyOtpBtn = findViewById(R.id.button_verify_otp_forgotRef_page);
        bar = findViewById(R.id.progress_bar_forgotRef_page);
        referenceIDFromDB = findViewById(R.id.text_view_reference_id_from_DB);
        backToLoginPage = findViewById(R.id.text_view_back_to_login_page);

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

            Toast.makeText(ForgotReferenceId.this, "Verification failed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

            super.onCodeSent(s, token);
            verificationId = s;
            Toast.makeText(ForgotReferenceId.this, "code Sent", Toast.LENGTH_SHORT).show();


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
                            Toast.makeText(ForgotReferenceId.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }




    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            startActivity(new Intent(ForgotReferenceId.this, Home_Activity.class));
//            finish();
//        }

    }








}
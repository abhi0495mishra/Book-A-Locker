package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.REF_ID_TO_SEND;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyCallback;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DAORepositoryImpl;
import com.abhimishra.lockerbooking.databases.DatabaseContract;
import com.abhimishra.lockerbooking.databases.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private String ref_ID_to_send;
    private FirebaseAuth mAuth;
    private TextView mobileNumberText;
    private EditText enteredMobileNumberField;
    private TextView loginRefID;
    private Button getOtpBtn;
    private Button verifyOtpBtn;
    private EditText enterOtpField;
    private String verificationId;
    private ProgressBar bar;

    private DAORepositoryImpl dbRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        //Initialize the dbRepository of class DAORepositoryImpl
        dbRepository = new DAORepositoryImpl(getBaseContext());

        mAuth = FirebaseAuth.getInstance();
        refIdlinkClickMethod();
        getOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dbRepository.checkIfMobileNumberAlreadyExists(enteredMobileNumberField.getText().toString())) {

                    Toast.makeText(MainActivity.this, "User already exists, please login with reference Id", Toast.LENGTH_LONG).show();

                } else {
                    // What happens after clicking Get OTP button
                    if (TextUtils.isEmpty(enteredMobileNumberField.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Please enter valid phone number", Toast.LENGTH_LONG).show();
                    } else {
                        String phone = enteredMobileNumberField.getText().toString();
                        bar.setVisibility(View.VISIBLE);
                        sendVerificationCode(phone);

                    }

                }


            }
        });

        verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(enterOtpField.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Wrong Otp Entered", Toast.LENGTH_LONG).show();
                } else {

                    //Verify OTP and Login to Homepage
                    verifyCode(enterOtpField.getText().toString());

                    //Generate Unique Reference ID for new user
                    String uniqueReferenceId = generateUniqueReferenceId();

                    // Store values of Mobile Number and Reference ID into the Database
                    Map<String, String> map = new HashMap<>();
                    map.put(DatabaseContract.User.COLUMN_NAME_MOBILE_NUMBER, enteredMobileNumberField.getText().toString());
                    map.put(DatabaseContract.User.COLUMN_NAME_REFERENCE_ID, uniqueReferenceId);
                    dbRepository.insert(map, DatabaseContract.User.TABLE_NAME);

                    //move to next activity
                    ref_ID_to_send = uniqueReferenceId;
                    goToHomeActivity();

                }

            }
        });


    }

    private void goToHomeActivity() {

        Intent intent = new Intent(this,Home_Activity.class);
        intent.putExtra(REF_ID_TO_SEND,ref_ID_to_send);
        startActivity(intent);

    }

    private String generateUniqueReferenceId() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // Generate Random Number of 6 digits
        return String.valueOf(randomNumber);
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
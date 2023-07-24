package com.abhimishra.lockerbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;
import java.util.UUID;

public class Home_Activity extends AppCompatActivity {

    private TextView welcomeText;
    private Button logOutBtn;
    FirebaseAuth mAuth;

    private TextView textReferenceId;

    private String uniqueReferenceId;

    private Button bookAlockerBtn;

    private Button myBookingsBtn;
    //private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        welcomeText = findViewById(R.id.text_view_welcome_text);
        logOutBtn = findViewById(R.id.btn_logoutbtn);
        bookAlockerBtn = findViewById(R.id.button_bookALockerBtn);
        myBookingsBtn = findViewById(R.id.button_myBookingsBtn);

        //code for reference ID
        textReferenceId = findViewById(R.id.text_view_reference_id);
        uniqueReferenceId = generateUniqueReferenceId();
        textReferenceId.setText("Reference ID: " + uniqueReferenceId);

        bookAlockerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, select_available_lockers.class);
                startActivity(intent);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Home_Activity.this, MainActivity.class));
                finish();
            }
        });


    }

    private String generateUniqueReferenceId() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // Generate Random Number of 6 digits
        return String.valueOf(randomNumber);
    }
}
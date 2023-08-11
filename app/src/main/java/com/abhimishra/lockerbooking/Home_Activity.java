package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.REF_ID_TO_SEND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DAORepositoryImpl;
import com.abhimishra.lockerbooking.databases.DatabaseContract;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;
import java.util.UUID;

public class Home_Activity extends AppCompatActivity {

    private TextView welcomeText;
    private Button logOutBtn;
    FirebaseAuth mAuth;

    private TextView textReferenceId;

    private String referenceID;

    private Button bookAlockerBtn;

    private Button myBookingsBtn;
    //private DatabaseHelper databaseHelper;

    private DAORepositoryImpl dbRepository;

    private String uniqueReferenceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize the dbRepository of class DAORepositoryImpl
        dbRepository = new DAORepositoryImpl(getBaseContext());

        mAuth = FirebaseAuth.getInstance();
        welcomeText = findViewById(R.id.text_view_welcome_text);
        logOutBtn = findViewById(R.id.btn_logoutbtn);
        bookAlockerBtn = findViewById(R.id.button_bookALockerBtn);
        myBookingsBtn = findViewById(R.id.button_myBookingsBtn);


        //code for reference ID
        textReferenceId = findViewById(R.id.text_view_reference_id);


        //Fetch the Reference ID from Main Activity
        Intent intent = getIntent();
        String refIdTxt = intent.getStringExtra(REF_ID_TO_SEND);
        //show ref Id on Home Page when user is signed UP!!
        textReferenceId.setText("Reference ID: " + refIdTxt);


        bookAlockerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Ref ID is " + refIdTxt);
                Intent intent = new Intent(Home_Activity.this, select_available_lockers.class);
                intent.putExtra(REF_ID_TO_SEND,refIdTxt);
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


}
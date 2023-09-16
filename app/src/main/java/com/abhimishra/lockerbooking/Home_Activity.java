package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.BOOKING_ID;
import static com.abhimishra.lockerbooking.Constants.PAYMENT_AMOUNT;
import static com.abhimishra.lockerbooking.Constants.REF_ID_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_END_DATE_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_ITEM_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_START_DATE_TO_SEND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DAORepositoryImpl;
import com.google.firebase.auth.FirebaseAuth;

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

    private String refIdText;
    private String startDate;
    private String endDate;
    private String lockerSelected;
    private String paymentAmount;
    private String booking_id;


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


        //Get Intents from Booking Confirmation Page.
        refIdText = intent.getStringExtra(REF_ID_TO_SEND);
        startDate = intent.getStringExtra(SELECTED_START_DATE_TO_SEND);
        endDate = intent.getStringExtra(SELECTED_END_DATE_TO_SEND);
        lockerSelected = intent.getStringExtra(SELECTED_ITEM_TO_SEND);
        paymentAmount = intent.getStringExtra(PAYMENT_AMOUNT);
        booking_id = intent.getStringExtra(BOOKING_ID);

        bookAlockerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkBookingID = dbRepository.fetchBookingID(refIdTxt);

                if(checkBookingID == null){

                    System.out.println("Ref ID is " + refIdTxt);
                    Intent intent = new Intent(Home_Activity.this, Select_Available_Lockers.class);
                    intent.putExtra(REF_ID_TO_SEND,refIdTxt);
                    startActivity(intent);
                } else {
                    Toast.makeText(Home_Activity.this,"Booking already Done. Check in the 'My Bookings' section.",Toast.LENGTH_LONG).show();

                }

            }
        });

        myBookingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkBookingID = dbRepository.fetchBookingID(refIdTxt);

                if(checkBookingID == null){

                    Toast.makeText(Home_Activity.this,"No Bookings",Toast.LENGTH_LONG).show();

                }else{

                    //fetch the user's booking data from the database
                    String startDate = dbRepository.fetchStartDate(checkBookingID);
                    String endDate = dbRepository.fetchEndDate(checkBookingID);
                    String bookingAmount = dbRepository.fetchBookingPayment(checkBookingID);
                    String lockerSelected = dbRepository.fetchSelectedLocker(checkBookingID);

                    Intent intent = new Intent(Home_Activity.this,My_bookings.class);
                    intent.putExtra(REF_ID_TO_SEND,refIdTxt);
                    intent.putExtra(BOOKING_ID,checkBookingID);
                    intent.putExtra(SELECTED_START_DATE_TO_SEND,startDate);
                    intent.putExtra(SELECTED_END_DATE_TO_SEND,endDate);
                    intent.putExtra(SELECTED_ITEM_TO_SEND,lockerSelected);
                    intent.putExtra(PAYMENT_AMOUNT,bookingAmount);
                    startActivity(intent);

                }



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
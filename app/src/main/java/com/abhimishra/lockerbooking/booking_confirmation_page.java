package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.BOOKING_ID;
import static com.abhimishra.lockerbooking.Constants.PAYMENT_AMOUNT;
import static com.abhimishra.lockerbooking.Constants.PAYMENT_MODE;
import static com.abhimishra.lockerbooking.Constants.REF_ID_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_END_DATE_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_ITEM_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_START_DATE_TO_SEND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhimishra.lockerbooking.databases.DAORepositoryImpl;
import com.abhimishra.lockerbooking.databases.DatabaseContract;

import java.util.HashMap;
import java.util.Map;

public class booking_confirmation_page extends AppCompatActivity {

    private ImageView greeTickImg;
    private TextView bookingSucessText;
    private TextView bookingID;
    private Button backToHomePageBtn;
    private TextView successTxt;

    private TextView refIDTextView;
    private DAORepositoryImpl dbRepository;

    private String refIdText;
    private String startDate;
    private String endDate;
    private String lockerSelected;

    private String paymentMode;
    private String paymentAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation_page);
        findviewsOnBookingConfirmationPage();

        //Initialize the dbRepository of class DAORepositoryImpl
        dbRepository = new DAORepositoryImpl(getBaseContext());

        //get the values from Intent
        Intent intent = getIntent();
        refIdText = intent.getStringExtra(REF_ID_TO_SEND);
        startDate = intent.getStringExtra(SELECTED_START_DATE_TO_SEND);
        endDate = intent.getStringExtra(SELECTED_END_DATE_TO_SEND);
        lockerSelected = intent.getStringExtra(SELECTED_ITEM_TO_SEND);
        paymentMode = intent.getStringExtra(PAYMENT_MODE);
        paymentAmount = intent.getStringExtra(PAYMENT_AMOUNT);

        //set the reference ID on top
        refIDTextView.setText("Reference ID: " + refIdText);

        // Set the Booking ID
        String uniqueBookingID = BookingIDGenerator.generateBookingID();
        bookingID.setText("BookingID: " + uniqueBookingID);


        //creating HashMap to insert data into Database
        Map<String, String> map = new HashMap<>();

        //insert the data into database

        //Booking ID
        map.put(DatabaseContract.Booking.COLUMN_NAME_BOOKING_ID, uniqueBookingID);
        //Reference ID
        map.put(DatabaseContract.Booking.COLUMN_NAME_USER_REFERENCE_ID, refIdText);
        //Start Date
        map.put(DatabaseContract.Booking.COLUMN_NAME_START_DATE, startDate);
        //End Date
        map.put(DatabaseContract.Booking.COLUMN_NAME_END_DATE, endDate);
        //Locker ID
        map.put(DatabaseContract.Booking.COLUMN_NAME_LOCKER_ID, lockerSelected);
        //Mode of Payment
        map.put(DatabaseContract.Booking.COLUMN_NAME_PAYMENT_MODE, paymentMode);
        //Payment Amount
        map.put(DatabaseContract.Booking.COLUMN_NAME_PAYMENT_AMOUNT, paymentAmount);

        //insert the Map in Booking table
        dbRepository.insert(map, DatabaseContract.Booking.TABLE_NAME);

        //Current locker unavailable in LOCKERS table
        dbRepository.updateLockerAvailabilityFalse(lockerSelected);

        backToHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(booking_confirmation_page.this, Home_Activity.class);
                intent.putExtra(REF_ID_TO_SEND, refIdText);
                intent.putExtra(SELECTED_START_DATE_TO_SEND,startDate);
                intent.putExtra(SELECTED_END_DATE_TO_SEND,endDate);
                intent.putExtra(BOOKING_ID, uniqueBookingID);
                intent.putExtra(SELECTED_ITEM_TO_SEND,lockerSelected);
                intent.putExtra(PAYMENT_AMOUNT,paymentAmount);
                startActivity(intent);
            }
        });

    }

    private void findviewsOnBookingConfirmationPage() {
        greeTickImg = findViewById(R.id.image_view_booking_successImage);
        bookingSucessText = findViewById(R.id.text_view_bookingSuccessfulText);
        bookingID = findViewById(R.id.text_view_uniqueBookingID);
        successTxt = findViewById(R.id.text_view_congrats_message);
        backToHomePageBtn = findViewById(R.id.btn_backToHomePage);
        refIDTextView = findViewById(R.id.text_view_reference_id);

    }

}
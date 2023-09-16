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
import android.widget.TextView;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DAORepositoryImpl;

import org.w3c.dom.Text;

public class My_bookings extends AppCompatActivity {

    private TextView bookingID_myBookings;
    private TextView startDate_myBookings;
    private TextView endDate_myBookings;
    private TextView totalFees_myBookings;

    private TextView lockerSelected_myBookings;
    private TextView returnToHomePage_myBookings;
    private TextView refId_myBookings;
    private Button cancelBtn;
    private String startDate;
    private String endDate;
    private String refIdText;
    private String lockerSelected;
    private String paymentAmount;
    private String booking_id;
    private DAORepositoryImpl dbRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        findviews();


        //Initialize the dbRepository of class DAORepositoryImpl
        dbRepository = new DAORepositoryImpl(getBaseContext());

        //Get all the intents
        Intent intent = getIntent();
        refIdText = intent.getStringExtra(REF_ID_TO_SEND);
        startDate = intent.getStringExtra(SELECTED_START_DATE_TO_SEND);
        endDate = intent.getStringExtra(SELECTED_END_DATE_TO_SEND);
        lockerSelected = intent.getStringExtra(SELECTED_ITEM_TO_SEND);
        paymentAmount = intent.getStringExtra(PAYMENT_AMOUNT);
        booking_id = intent.getStringExtra(BOOKING_ID);

        //set reference Id in the top

        refId_myBookings.setText("Reference ID: " + refIdText);

        //Display the details on the My Bookings page

        bookingID_myBookings.setText("Booking ID: " + booking_id);
        startDate_myBookings.setText("Start date: " + startDate);
        endDate_myBookings.setText("End Date: " + endDate);
        lockerSelected_myBookings.setText("Selected Locker: " + lockerSelected);
        totalFees_myBookings.setText("Payment Amount: " + paymentAmount);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cancel entry from the database
                dbRepository.cancelBooking(booking_id);

                //change locker availability
                dbRepository.updateLockerAvailabilityTrue(lockerSelected);
                // display cancelled Toast Message
                Toast.makeText(My_bookings.this,"Booking Cancelled successfully",Toast.LENGTH_LONG).show();

                //Refresh the page and go to homepage
                Intent intent = new Intent(My_bookings.this, Home_Activity.class);
                intent.putExtra(REF_ID_TO_SEND,refIdText);
                startActivity(intent);
            }
        });
        returnToHomePage_myBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_bookings.this, Home_Activity.class);
                intent.putExtra(REF_ID_TO_SEND, refIdText);
                intent.putExtra(BOOKING_ID, booking_id);
                intent.putExtra(SELECTED_START_DATE_TO_SEND,startDate);
                intent.putExtra(SELECTED_END_DATE_TO_SEND,endDate);
                intent.putExtra(SELECTED_ITEM_TO_SEND,lockerSelected);
                intent.putExtra(PAYMENT_AMOUNT,paymentAmount);
                startActivity(intent);
            }
        });


    }

    private void findviews() {
        bookingID_myBookings = findViewById(R.id.text_view_booking_id_my_bookings);
        startDate_myBookings = findViewById(R.id.text_view_start_date_my_bookings);
        endDate_myBookings = findViewById(R.id.text_view_End_date_my_bookings);
        totalFees_myBookings = findViewById(R.id.text_view_total_fee_my_bookings);
        lockerSelected_myBookings = findViewById(R.id.text_view_locker_selected_my_bookings);
        cancelBtn = findViewById(R.id.button_cancelBtn);
        returnToHomePage_myBookings = findViewById(R.id.text_view_back_to_login_page_my_bookings);
        refId_myBookings = findViewById(R.id.text_view_reference_id_my_bookings);

    }
}
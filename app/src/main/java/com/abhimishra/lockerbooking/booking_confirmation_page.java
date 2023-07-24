package com.abhimishra.lockerbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class booking_confirmation_page extends AppCompatActivity {

    private ImageView greeTickImg;
    private TextView bookingSucessText;
    private TextView bookingID;
    private Button backToHomePageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation_page);
        findviewsOnBookingConfirmationPage();


        String uniqueBookingID = BookingIDGenerator.generateBookingID();
        bookingID.setText("BookingID: "+uniqueBookingID);

        backToHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(booking_confirmation_page.this, Home_Activity.class);
                startActivity(intent);
            }
        });

    }

    private void findviewsOnBookingConfirmationPage() {
        greeTickImg = findViewById(R.id.image_view_booking_successImage);
        bookingSucessText = findViewById(R.id.text_view_bookingSuccessfulText);
        bookingID = findViewById(R.id.text_view_uniqueBookingID);
        backToHomePageBtn = findViewById(R.id.btn_backToHomePage);
    }
}
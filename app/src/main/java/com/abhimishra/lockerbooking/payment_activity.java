package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.PAYMENT_AMOUNT;
import static com.abhimishra.lockerbooking.Constants.PAYMENT_MODE;
import static com.abhimishra.lockerbooking.Constants.REF_ID_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_END_DATE_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_ITEM_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_START_DATE_TO_SEND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DatabaseContract;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class payment_activity extends AppCompatActivity {

    private TextView paymentOptionsText;
    private RadioButton creditDebitBtn;
    private RadioButton payLaterBtn;
    private Button finalPayBtn;
    private TextView contactThroughGmail;

    private String refIdText;

    private String startDate;
    private String endDate;
    private String lockerSelected;

    private String paymentAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentOptionsText = findViewById(R.id.text_view_paymentOptions);
        creditDebitBtn = findViewById(R.id.creditDebitRadioButton);
        payLaterBtn = findViewById(R.id.payLaterRadioButton);
        contactThroughGmail = findViewById(R.id.text_view_contactGmail);
        finalPayBtn = findViewById(R.id.finalPayButton);

        //get the value of referene ID from Start/End Date page

        Intent intent = getIntent();
        refIdText = intent.getStringExtra(REF_ID_TO_SEND);
        startDate = intent.getStringExtra(SELECTED_START_DATE_TO_SEND);
        endDate = intent.getStringExtra(SELECTED_END_DATE_TO_SEND);
        lockerSelected = intent.getStringExtra(SELECTED_ITEM_TO_SEND);
        paymentAmount = intent.getStringExtra(PAYMENT_AMOUNT);

        finalPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performPayment();
            }
        });


        contactThroughGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToContactThroughGmail = new Intent(Intent.ACTION_SENDTO);
                intentToContactThroughGmail.setData(Uri.parse("mailto:"));
                startActivity(intentToContactThroughGmail);
            }
        });


    }

    private void performPayment() {

        Intent intent = new Intent(payment_activity.this, booking_confirmation_page.class);
        intent.putExtra(REF_ID_TO_SEND, refIdText);
        intent.putExtra(SELECTED_START_DATE_TO_SEND,startDate);
        intent.putExtra(SELECTED_END_DATE_TO_SEND,endDate);
        intent.putExtra(SELECTED_ITEM_TO_SEND,lockerSelected );
        intent.putExtra(PAYMENT_AMOUNT,paymentAmount);

        if (creditDebitBtn.isChecked()) {
            // Simulate credit/debit card payment
            // Replace with your payment processing logic

            Toast.makeText(this, "Credit/Debit Card Payment Successful", Toast.LENGTH_SHORT).show();
            intent.putExtra(PAYMENT_MODE, "Credit/Debit Card");
            startActivity(intent);

        } else if (payLaterBtn.isChecked()) {
            // Simulate "Pay Later" payment
            // Replace with your payment processing logic

            //Move to Next Activity
            Toast.makeText(this, "Pay Later Payment Successful", Toast.LENGTH_SHORT).show();
            intent.putExtra(PAYMENT_MODE, "Pay Later");
            startActivity(intent);

        } else {
            // No payment option selected
            Toast.makeText(this, "Please select a payment option", Toast.LENGTH_SHORT).show();
        }
    }
}
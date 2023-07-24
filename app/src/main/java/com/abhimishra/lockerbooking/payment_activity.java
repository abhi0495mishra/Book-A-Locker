package com.abhimishra.lockerbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class payment_activity extends AppCompatActivity {

    private TextView paymentOptionsText;
    private RadioButton creditDebitBtn;
    private RadioButton payLaterBtn;
    private Button finalPayBtn;
    private TextView contactThroughGmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentOptionsText = findViewById(R.id.text_view_paymentOptions);
        creditDebitBtn = findViewById(R.id.creditDebitRadioButton);
        payLaterBtn = findViewById(R.id.payLaterRadioButton);
        contactThroughGmail = findViewById(R.id.text_view_contactGmail);
        finalPayBtn = findViewById(R.id.finalPayButton);

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


        if (creditDebitBtn.isChecked()) {
            // Simulate credit/debit card payment
            // Replace with your payment processing logic
            Toast.makeText(this, "Credit/Debit Card Payment Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(payment_activity.this, booking_confirmation_page.class);
            startActivity(intent);

        } else if (payLaterBtn.isChecked()) {
            // Simulate "Pay Later" payment
            // Replace with your payment processing logic
            Toast.makeText(this, "Pay Later Payment Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(payment_activity.this, booking_confirmation_page.class);
            startActivity(intent);

        } else {
            // No payment option selected
            Toast.makeText(this, "Please select a payment option", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.PAYMENT_AMOUNT;
import static com.abhimishra.lockerbooking.Constants.REF_ID_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_END_DATE_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_ITEM_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_START_DATE_TO_SEND;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DatabaseContract;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class select_dateTime extends AppCompatActivity {

    private TextView startDateLabel;
    private EditText startDateEditBox;

    private Button calculateFee;

    private TextView noOfDays;
    private TextView noOfHours;
    private TextView totalFee;

    private TextView endDateLabel;
    private EditText endDateEditBox;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private Button confirmAndPayBtn;

    private String paymentAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_time);

        //Find views on this page
        findViews();


        //get the value of reference ID from Locker selection page

        Intent intent = getIntent();
        String refIdText = intent.getStringExtra(REF_ID_TO_SEND);
        String lockerSelected = intent.getStringExtra(SELECTED_ITEM_TO_SEND);


        // Initialize calendar and date/time formats
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        startDateEditBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noOfDays.setVisibility(View.INVISIBLE);
                noOfHours.setVisibility(View.INVISIBLE);
                totalFee.setVisibility(View.INVISIBLE);
                confirmAndPayBtn.setEnabled(false);

                showDateTimePickerDialog(startDateEditBox);
            }
        });

        endDateEditBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog(endDateEditBox);
            }
        });


        calculateFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Convert the Difference of Start date & End date in Seconds
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime format1 = LocalDateTime.parse(startDateEditBox.getText().toString(),formatter);
                LocalDateTime format2 = LocalDateTime.parse(endDateEditBox.getText().toString(),formatter);

                long seconds = ChronoUnit.SECONDS.between(format1, format2);

                //All below tasks execute when CALCULATE button is clicked
                //15 hours is equal to 54000 seconds.

                if(seconds>54000){

                    paymentAmount = calculateDaysAndFee(seconds) + ""; //Method to Calculate No of Days

                    noOfDays.setVisibility(View.VISIBLE);
                    totalFee.setVisibility(View.VISIBLE);

                }else{

                    paymentAmount = calculateHoursAndFee(seconds) + ""; //Method to Calculate No of Hour
                    noOfHours.setVisibility(View.VISIBLE);
                    totalFee.setVisibility(View.VISIBLE);

                }

                confirmAndPayBtn.setVisibility(View.VISIBLE); // Visible the Confirm and Pay Button
                confirmAndPayBtn.setEnabled(true); // Enable the Confirm and Pay button

            }
        });

        confirmAndPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(select_dateTime.this, "Details Saved", Toast.LENGTH_LONG).show();

                // Move to next Activity
                Intent intent = new Intent(select_dateTime.this, payment_activity.class);
                intent.putExtra(SELECTED_START_DATE_TO_SEND, startDateEditBox.getText().toString());
                intent.putExtra(SELECTED_END_DATE_TO_SEND, endDateEditBox.getText().toString());
                intent.putExtra(REF_ID_TO_SEND, refIdText);
                intent.putExtra(SELECTED_ITEM_TO_SEND, lockerSelected);
                intent.putExtra(PAYMENT_AMOUNT,paymentAmount);

                startActivity(intent);

            }
        });


    }


    private double calculateHoursAndFee(long seconds) {

        //calculate total hours
        double totalHoursRequested = seconds * ((double) 1 / 3600);

        // display no of hours
        noOfHours.setText("Number of Hours: " + String.format("%.2f",totalHoursRequested));

        //logic of Fee as per Hour
        double totalCharge = 0.5 * totalHoursRequested;

        //Set Fee in edit box
        totalFee.setText("Total Fees: " + String.format("%.2f",totalCharge));

        return totalCharge;

    }

    private double calculateDaysAndFee(long seconds) {

        // 1 Day - 86400 sec
        double totalDaysRequested = seconds * ((double) 1 / 86400);

        // display no of Days
        noOfDays.setText("Number of Days: " + String.format("%.2f",totalDaysRequested));

        //logic of Fee as per Day
        double totalCharge = 10 * totalDaysRequested;

        //Set Fee in edit box
        totalFee.setText("Total Fees: " + String.format("%.2f",totalCharge));

        return  totalCharge;

    }

    private void findViews() {
        startDateLabel = findViewById(R.id.start_Date_Label);
        startDateEditBox = findViewById(R.id.start_Date_EditText);
        endDateLabel = findViewById(R.id.end_Date_Label);
        endDateEditBox = findViewById(R.id.end_Date_EditText);
        confirmAndPayBtn = findViewById(R.id.button_confirm_and_pay);
        calculateFee = findViewById(R.id.button_calculate);
        noOfDays = findViewById(R.id.text_view_number_of_days);
        noOfHours = findViewById(R.id.text_view_number_of_hours);
        totalFee = findViewById(R.id.text_view_total_fees);
    }


    private void showDateTimePickerDialog(final EditText editText) {
        // Get current date and time
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                select_dateTime.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set selected date
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Create TimePickerDialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                select_dateTime.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Set selected time
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);

                                        // Format date and time strings
                                        String selectedDate = dateFormat.format(calendar.getTime());
                                        String selectedTime = timeFormat.format(calendar.getTime());
                                        String selectedDateTime = selectedDate + " " + selectedTime;

                                        // Set selected date and time in the EditText
                                        editText.setText(selectedDateTime);
                                    }
                                },
                                hour,
                                minute,
                                true
                        );

                        // Show TimePickerDialog
                        timePickerDialog.show();
                    }
                },
                year,
                month,
                day
        );

        // Show DatePickerDialog
        datePickerDialog.show();
    }
}

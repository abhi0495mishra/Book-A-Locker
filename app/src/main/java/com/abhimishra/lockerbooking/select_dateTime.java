package com.abhimishra.lockerbooking;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class select_dateTime extends AppCompatActivity {

    private TextView startDateLabel;
    private EditText startDateEditBox;
    private TextView endDateLabel;
    private EditText endDateEditBox;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private Button confirmAndPayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_time);

        startDateLabel = findViewById(R.id.start_Date_Label);
        startDateEditBox = findViewById(R.id.start_Date_EditText);
        endDateLabel = findViewById(R.id.end_Date_Label);
        endDateEditBox = findViewById(R.id.end_Date_EditText);
        confirmAndPayBtn = findViewById(R.id.button_confirm_and_pay);

        confirmAndPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(select_dateTime.this,"Details Saved",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(select_dateTime.this, payment_activity.class);
                startActivity(intent);

            }
        });

        // Initialize calendar and date/time formats
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        startDateEditBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog(startDateEditBox);
            }
        });

        endDateEditBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog(endDateEditBox);
            }
        });
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

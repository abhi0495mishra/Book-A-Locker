package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.REF_ID_TO_SEND;
import static com.abhimishra.lockerbooking.Constants.SELECTED_ITEM_TO_SEND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DAORepositoryImpl;
import com.abhimishra.lockerbooking.databases.DatabaseContract;

import java.util.HashMap;
import java.util.Map;

public class select_available_lockers extends AppCompatActivity {

    private TextView lockerAvailabilityText;

    private ListView lockerAvailability;

    private DAORepositoryImpl dbRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_available_lockers);

        lockerAvailability = findViewById(R.id.list_view_locker_availability);
        lockerAvailabilityText = findViewById(R.id.text_view_lockerAvailability_text);

        //get the reference ID from HomePage
        Intent intent = getIntent();
        String refIDTxt = intent.getStringExtra(REF_ID_TO_SEND);

        //Initialize the dbRepository of class DAORepositoryImpl
        dbRepository = new DAORepositoryImpl(getBaseContext());


        // TODO - Fetch these lockers from available lockers
        String[] lockers = {"L1","L2","L3","L4","L5","L6","L7","L8","L9","L10"};
        ArrayAdapter<String> lockerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lockers);
        lockerAvailability.setAdapter(lockerAdapter);

        lockerAvailability.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                System.out.println("Selected Item is " + selectedItem);

                Toast.makeText(select_available_lockers.this, "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();


                //Navigate to Next Page
                Intent intent = new Intent(select_available_lockers.this, select_dateTime.class);
                intent.putExtra(SELECTED_ITEM_TO_SEND, selectedItem);
                intent.putExtra(REF_ID_TO_SEND,refIDTxt);
                startActivity(intent);
            }
        });

    }
}
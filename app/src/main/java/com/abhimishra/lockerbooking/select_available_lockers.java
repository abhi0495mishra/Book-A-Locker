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

        //Fetch locker from the Database and display on the page
        String[] lockers = dbRepository.fetchAvailableLockers();
        ArrayAdapter<String> lockerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lockers);
        lockerAvailability.setAdapter(lockerAdapter);

        lockerAvailability.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(select_available_lockers.this, "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();


                //Navigate to Next Page
                Intent intent = new Intent(select_available_lockers.this, select_dateTime.class);
                intent.putExtra(SELECTED_ITEM_TO_SEND, selectedItem);
                intent.putExtra(REF_ID_TO_SEND, refIDTxt);
                startActivity(intent);
            }
        });

    }

}
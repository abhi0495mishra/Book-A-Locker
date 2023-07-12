package com.abhimishra.lockerbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home_Activity extends AppCompatActivity {

    private TextView welcomeText;
    private Button logOutBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        welcomeText = findViewById(R.id.text_view_welcome_text);
        logOutBtn = findViewById(R.id.btn_logoutbtn);

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
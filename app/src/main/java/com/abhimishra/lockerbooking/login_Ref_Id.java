package com.abhimishra.lockerbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login_Ref_Id extends AppCompatActivity {

    private TextView textloginRefId;
    private EditText enterRefId;
    private TextView textloginMobileNo;
    private EditText enterMobileNo;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ref_id);
        findviewsOnRefIdLoginPage();
    }

    private void findviewsOnRefIdLoginPage() {
        textloginRefId = findViewById(R.id.text_view_login_refId);
        enterRefId = findViewById(R.id.edit_text_login_refId);
        textloginMobileNo = findViewById(R.id.text_view_login_MobileNo);
        enterMobileNo = findViewById(R.id.edit_text_login_MobileNo);
        submitBtn = findViewById(R.id.btn_login_refId_submit);
    }
}
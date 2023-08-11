package com.abhimishra.lockerbooking;

import static com.abhimishra.lockerbooking.Constants.REFERENCE_ID;
import static com.abhimishra.lockerbooking.Constants.REF_ID_TO_SEND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abhimishra.lockerbooking.databases.DAORepositoryImpl;

public class login_Ref_Id extends AppCompatActivity {


    private TextView textloginRefId;
    private EditText enterRefId;
    private TextView textloginMobileNo;
    private EditText enterMobileNo;

    private TextView forgotRefIdLink;
    private Button submitBtn;

    private TextView newUserSignUpLink;
    private DAORepositoryImpl dbRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ref_id);
        findviewsOnRefIdLoginPage();

        dbRepository = new DAORepositoryImpl(getBaseContext());

        Intent intent = getIntent();
        String referenceID = intent.getStringExtra(REFERENCE_ID);

        if (referenceID != null){
            //Logic of Auto-Fill
            enterRefId.setText(referenceID);
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (enterRefId.equals("") || enterMobileNo.equals("")){
                    Toast.makeText(login_Ref_Id.this,"Please enter the details",Toast.LENGTH_LONG).show();
                }else{

                    Boolean checkMobNumAndRefID = dbRepository.checkMobNumAndRefID(enterMobileNo.getText().toString(),
                            enterRefId.getText().toString());

                    if(checkMobNumAndRefID){
                        Toast.makeText(login_Ref_Id.this,"Sign in Successfull",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(login_Ref_Id.this,Home_Activity.class);
                        intent.putExtra(REF_ID_TO_SEND,enterRefId.getText().toString());
                        startActivity(intent);
                    }else {
                        Toast.makeText(login_Ref_Id.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        newUserSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_Ref_Id.this,MainActivity.class);
                startActivity(intent);
            }
        });

        forgotRefIdLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(login_Ref_Id.this,ForgotReferenceId.class);
                startActivity(intent);
            }
        });

    }

    private void findviewsOnRefIdLoginPage() {
        textloginRefId = findViewById(R.id.text_view_login_refId);
        enterRefId = findViewById(R.id.edit_text_login_refId);
        textloginMobileNo = findViewById(R.id.text_view_login_MobileNo);
        enterMobileNo = findViewById(R.id.edit_text_login_MobileNo);
        submitBtn = findViewById(R.id.btn_login_refId_submit);
        newUserSignUpLink = findViewById(R.id.text_view_new_user_sign_Up_Here);
        forgotRefIdLink = findViewById(R.id.text_view_forgot_ref_Id);
    }
}
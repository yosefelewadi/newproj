package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Informations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);
        Intent intent=getIntent();
        String sName=intent.getStringExtra("name");
        String sEmail=intent.getStringExtra("email");
        String sPhone=intent.getStringExtra("phone");
        String sAge=intent.getStringExtra("age");
        String sAddress=intent.getStringExtra("address");
        String sGender=intent.getStringExtra("gender");
        TextView name=findViewById(R.id.result_name);
        TextView email=findViewById(R.id.result_email);
        TextView phone=findViewById(R.id.result_phone);
        TextView age=findViewById(R.id.result_age);
        TextView address=findViewById(R.id.result_address);
        TextView gender=findViewById(R.id.result_gender);
        name.append(" ' "+ sName +" '");
        email.append(" ' "+sEmail+" '");
        phone.append(" ' "+sPhone+" '");
        age.append(" ' "+sAge+" '");
        address.append(" ' "+sAddress+" '");
        gender.append(" ' "+sGender+" '");
    }
}

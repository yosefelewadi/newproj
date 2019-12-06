package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText phone;
    EditText age;
    EditText address;
    EditText gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.text_name);
        email=findViewById(R.id.text_email);
        phone=findViewById(R.id.phone_number);
        age=findViewById(R.id.age);
        address=findViewById(R.id.text_address);
        gender=findViewById(R.id.gender);
            }

    public void onOkClicked(View view) {
        String Name1=name.getText().toString();
        String Email1=email.getText().toString();
        String Phone1=phone.getText().toString();
        String Age1=age.getText().toString();
        String Address1=address.getText().toString();
        String Gender1=gender.getText().toString();
        Intent intent=new Intent(this,Informations.class);
        intent.putExtra("name",Name1);
        intent.putExtra("email",Email1);
        intent.putExtra("phone",Phone1);
        intent.putExtra("age",Age1);
        intent.putExtra("address",Address1);
        intent.putExtra("gender",Gender1);
        startActivity(intent);
    }
}

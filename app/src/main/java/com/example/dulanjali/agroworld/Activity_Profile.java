package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

public class Activity_Profile extends AppCompatActivity {

    TextInputLayout fullName,email,phoneNo,password;
    TextView fullNameLabel,userNameLabel;

    DatabaseReference demoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile);

        fullName = findViewById(R.id.prof_name);
        email = findViewById(R.id.prof_mail);
        phoneNo = findViewById(R.id.prof_phone);
        password = findViewById(R.id.prof_pw_);
        fullNameLabel = findViewById(R.id.full_name);
        userNameLabel = findViewById(R.id.user_name);

        showAllUserData();


    }

    private void showAllUserData()
    {
        Intent dash = getIntent();
        String user_username = dash.getStringExtra("username");
        String user_name = dash.getStringExtra("name");
        String user_email = dash.getStringExtra("email");
        String user_phoneNo = dash.getStringExtra("phoneNo");
        String user_password = dash.getStringExtra("password");

        fullNameLabel.setText(user_name);
        userNameLabel.setText(user_username);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);


    }
}

package com.example.dulanjali.agroworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Profile extends AppCompatActivity {

    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, userNameLabel;
    DatabaseReference databaseRef;

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

       databaseRef = FirebaseDatabase.getInstance().getReference();

       databaseRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String nameFromDB = dataSnapshot.child("name").getValue(String.class);
               String userNameFromDB = dataSnapshot.child("username").getValue(String.class);
               String emailFromDB = dataSnapshot.child("email").getValue(String.class);
               String phoneNoFromDB = dataSnapshot.child("phoneNo").getValue(String.class);
               String passwordFromDB = dataSnapshot.child("password").getValue(String.class);

               fullNameLabel.setText(nameFromDB);
               userNameLabel.setText(userNameFromDB);
               fullName.getEditText().setText(nameFromDB);
               email.getEditText().setText(emailFromDB);
               phoneNo.getEditText().setText(phoneNoFromDB);
               password.getEditText().setText(passwordFromDB);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }


}
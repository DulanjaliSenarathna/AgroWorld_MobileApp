package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Activity_Dashboard extends AppCompatActivity {

    Button logout;
    ImageView updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dashboard);

        logout = findViewById(R.id.log_out);
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Dashboard.this,Activity_Login.class));
                    }
                }
        );

        updateProfile = findViewById(R.id.update_profile);
        updateProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Dashboard.this,Activity_Profile.class));
                    }
                }
        );
    }
}

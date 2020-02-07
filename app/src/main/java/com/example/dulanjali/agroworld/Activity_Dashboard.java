package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Dashboard extends AppCompatActivity {

    private Button logout;
    private ImageView updateProfile;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dashboard);

        mAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.log_out);
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logOut();
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

    private void logOut()
    {
        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin()
    {
        Intent intent = new Intent(Activity_Dashboard.this, Activity_Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null)
        {
            sendToLogin();
        }
    }



}

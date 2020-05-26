package com.example.dulanjali.agroworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Activity_SplashScreen.this,Activity_Login.class));
            }
        },5000);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

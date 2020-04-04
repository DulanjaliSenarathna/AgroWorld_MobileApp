package com.example.dulanjali.agroworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

                if(isFirstTime)
                {
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(),OnBoarding.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),Activity_Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        },5000);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

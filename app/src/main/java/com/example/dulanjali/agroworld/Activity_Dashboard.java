package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Dashboard extends AppCompatActivity {

    //Views
    private ImageView temperature,humidity,soil,motion,blog,profile;
    private RequestQueue mQueue;
    ImageView check_wifi,wifi_status;
    TextView wifi_st_txt;




    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dashboard);

        mAuth = FirebaseAuth.getInstance();

       //link views with xml
       temperature = findViewById(R.id.imageTemperature);
        humidity = findViewById(R.id.imageHumidity);
        soil=findViewById(R.id.imageSoil);
        motion = findViewById(R.id.imageMotion);
        blog = findViewById(R.id.imageBlog);
        profile = findViewById(R.id.imageProfile);
        check_wifi = findViewById(R.id.imageWifi);
        wifi_status = findViewById(R.id.connectionIv);
        wifi_st_txt = findViewById(R.id.connectionTv);

        mQueue = Volley.newRequestQueue(this);

        //image click to check network status
        check_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function call to check network status
                checkNetworkConnectionStatus();
            }
        });


        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        profile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Dashboard.this,Activity_Profile.class));
                    }
                }
        );

        blog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Dashboard.this,Activity_Blog.class));
                    }
                }
        );

        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(Activity_Dashboard.this,SoilMoistureActivity.class));
                jsonParse();
            }
        });


    }

    private void jsonParse()
    {
        String url = "https://io.adafruit.com/api/v2/dulanjali/feeds/soil-moisture/";
    }

    private void checkNetworkConnectionStatus()
    {
        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected())//connected with either mobile or wifi
        {
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiConnected) // wifi connected
            {
                wifi_status.setImageResource(R.drawable.wifi);
                wifi_st_txt.setText("Connected with wifi");
            }
            else if (mobileConnected) // mobile data connected
            {
                wifi_status.setImageResource(R.drawable.data);
                wifi_st_txt.setText("Connected with mobile data");
            }
        }
        else // no internet connection
        {
            wifi_status.setImageResource(R.drawable.nowifi);
            wifi_st_txt.setText("No internet connection");
        }

    }

    private void logOut()
    {
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
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null)
        {
            Intent loginPageIntent = new Intent(Activity_Dashboard.this,Activity_Login.class);
            loginPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginPageIntent);
            finish();

        }

    }
}

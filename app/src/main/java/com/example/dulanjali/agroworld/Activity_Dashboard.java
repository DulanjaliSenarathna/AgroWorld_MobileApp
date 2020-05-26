package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Activity_Dashboard extends AppCompatActivity {

    //Views
    private ImageView temperature,humidity,soil,motion,blog,profile;
    ImageView check_wifi,wifi_status;
    TextView wifi_st_txt,username;
    private DatabaseReference getUserDataReference;
    private FirebaseUser fuser;
    private FirebaseAuth mAuth;
    String clientId = MqttClient.generateClientId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dashboard);

        //Reference to firebase authentication
        mAuth = FirebaseAuth.getInstance();

       //link views with xml
       temperature = findViewById(R.id.imageTemperature);
        humidity = findViewById(R.id.imageHumidity);
        soil=findViewById(R.id.imageSoil);
        motion = findViewById(R.id.imageMotion);
        blog = findViewById(R.id.imageBlog);
        profile = findViewById(R.id.prof_icon);
        check_wifi = findViewById(R.id.imageWifi);
        wifi_status = findViewById(R.id.connectionIv);
        wifi_st_txt = findViewById(R.id.connectionTv);

        // image click to check profile details and update profile details
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Dashboard.this,Activity_Profile.class));

            }
        });


        //image click to check network status
        check_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function call to check network status
                checkNetworkConnectionStatus();
            }
        });

        // image click to check temperature details
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Dashboard.this, Activity_TemperatureData.class));

            }
        });

        // image click to check humidity details
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Dashboard.this, Activity_HumidityData.class));

            }
        });

        // image click to check soil moisture details and control water pump
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Activity_Dashboard.this, Activity_SoilMoisture_Water_Pump.class));

            }
        });

        // image click to check motion details
        motion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Dashboard.this, Activity_MotionDetection.class));

            }
        });

        // image click to add, view and search posts
        blog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Dashboard.this,Activity_Blog.class));
                    }
                }
        );

        mqttConnection();


    }

    private void mqttConnection()
    {
        final MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "ssl://io.adafruit.com:8883",
                        clientId);

        try {

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("dulanjali");
            String aio = "aio_btJp99cUWnF74pM7IoFix0oqNYiA";
            char[] ch = new char[aio.length()];

            // Copy character by character into array
            for (int i = 0; i < aio.length(); i++) {
                ch[i] = aio.charAt(i);
            }
            options.setPassword(ch);

            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(Activity_Dashboard.this, "Success", Toast.LENGTH_SHORT).show();

                    String topic = "dulanjali/feeds/soil-moisture";
                    int qos = 1;
                    try {
                        IMqttToken subToken = client.subscribe(topic, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                // The message was published
                              //  Toast.makeText(Activity_Dashboard.this, "Subscribed !", Toast.LENGTH_SHORT).show();
                                showToast();

                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                exception.printStackTrace();
                                // The subscription could not be performed, maybe the user was not
                                // authorized to subscribe on the specified topic e.g. using wildcards

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    exception.printStackTrace();
                    Toast.makeText(Activity_Dashboard.this, "Fail", Toast.LENGTH_SHORT).show();

                }
            });
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String soilMoistureValue = new String(message.getPayload());
                    Double valueDoble = Double.parseDouble(soilMoistureValue);
                    if (valueDoble >= 600) {
                        Toast.makeText(Activity_Dashboard.this, "Low soil moisture ( "+ valueDoble +" ) detected ... starting the water pump ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void showToast()
    {
        
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

    //on start method
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

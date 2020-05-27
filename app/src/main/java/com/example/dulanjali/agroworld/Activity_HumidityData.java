package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.example.dulanjali.agroworld.common.Stables;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_HumidityData extends AppCompatActivity {

    HalfGauge last_hum_data;
    Button prev_hum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_data);

        last_hum_data = findViewById(R.id.hum);
        prev_hum = findViewById(R.id.prev_humi);

        jsonParseHumidity();

        prev_hum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_HumidityData.this,Activity_All_Humidity.class));

            }
        });
    }

    private void jsonParseHumidity()
    {

        RequestQueue requestQueue= Volley.newRequestQueue(Activity_HumidityData.this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getHumidityDetails(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hide loading
                try {
                    JSONObject jsonObject=new JSONObject(response);


                    //  lastData.setText(jsonObject.getString("value"));
                    String humValue = jsonObject.getString("value");
                    last_hum_data.setValue((Double.parseDouble(humValue)));

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //hide loading
                Toast.makeText(Activity_HumidityData.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-AIO-Key", "aio_btJp99cUWnF74pM7IoFix0oqNYiA");
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}

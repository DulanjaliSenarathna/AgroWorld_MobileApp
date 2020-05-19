package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dulanjali.agroworld.common.Stables;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SoilMoistureActivity extends AppCompatActivity {


    ToggleButton toggleButton;
    SharedPreferences sharedPreferences;
    PopupMenu popupmenu ;
    ImageView allData;
    TextView lastData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_moisture);

        allData = findViewById(R.id.moreData);
        toggleButton = findViewById(R.id.toggleButton);
        lastData = findViewById(R.id.last_data);
        allData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopMenuDisplay();
            }
        });

        sharedPreferences=getSharedPreferences("ststus",MODE_PRIVATE);
        if (sharedPreferences.getString("ststusid","999").equals("1")){
            toggleButton.setChecked(true);
        }else {
            toggleButton.setChecked(false);
        }



        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()){
                    postRequest();
                    toggleButton.setChecked(true);
                }else {
                    postRequestFalse();
                    toggleButton.setChecked(false);
                }

            }
        });

    }

    private void PopMenuDisplay() {

        popupmenu = new PopupMenu(SoilMoistureActivity.this, allData);

        popupmenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupmenu.getMenu());

        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {

                startActivity(new Intent(SoilMoistureActivity.this,AllSoilMoistureDataActivity.class));

                return true;
            }
        });

        popupmenu.show();
    }


    private void postRequest()
    {
        try {

            RequestQueue requestQueue= Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            JSONObject datum = new JSONObject();

            datum.put("value","1");

            JsonObjectRequest jsonOblectReq = new JsonObjectRequest(new Stables().url(), datum, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Toast.makeText(SoilMoistureActivity.this, "Water Pump On", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getSharedPreferences("ststus",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("ststusid","1");
                        editor.commit();

                    }catch(Exception ex){}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("X-AIO-Key", "aio_btJp99cUWnF74pM7IoFix0oqNYiA");
                    params.put("Content-Type", "application/json");
                    return params;
                }
            };

            requestQueue.add(jsonOblectReq);
        }catch(Exception e){}


    }

    private void postRequestFalse(){
        try {

            RequestQueue requestQueue= Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            JSONObject datum = new JSONObject();

            datum.put("value","0");

            JsonObjectRequest jsonOblectReq = new JsonObjectRequest(new Stables().url(), datum, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Toast.makeText(SoilMoistureActivity.this, "Water Pump Off", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getSharedPreferences("ststus",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("ststusid","0");
                        editor.commit();

                    }catch(Exception ex){}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("X-AIO-Key", "aio_btJp99cUWnF74pM7IoFix0oqNYiA");
                    params.put("Content-Type", "application/json");
                    return params;
                }
            };

            requestQueue.add(jsonOblectReq);
        }catch(Exception e){}
    }
}

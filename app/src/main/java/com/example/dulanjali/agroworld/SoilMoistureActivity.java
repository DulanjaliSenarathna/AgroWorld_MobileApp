package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_moisture);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postRequest();
            }
        });

    }



    private void postRequest()
    {
        try {

            RequestQueue requestQueue= Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            JSONObject datum = new JSONObject();
//            JSONObject newobj = jsonBody.getJSONObject("datum");

            datum.put("value","1");

            JsonObjectRequest jsonOblectReq = new JsonObjectRequest(new Stables().url(), datum, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        Toast.makeText(SoilMoistureActivity.this, "Done", Toast.LENGTH_SHORT).show();

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

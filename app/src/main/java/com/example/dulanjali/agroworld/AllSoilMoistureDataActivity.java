package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dulanjali.agroworld.common.Stables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AllSoilMoistureDataActivity extends AppCompatActivity {
    TextView textView;
    JSONObject newobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_soil_moisture_data);
        textView = findViewById(R.id.textView0);

        getRecentData();
    }

    private void getRecentData() {

        RequestQueue requestQueue= Volley.newRequestQueue(AllSoilMoistureDataActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getRecentDetails(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Toast.makeText(AllSoilMoistureDataActivity.this, jsonObject.getString("created_at") +":"+jsonObject.getString("value"), Toast.LENGTH_SHORT).show();
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());

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
        requestQueue.add(jsonArrayRequest);

    }
}

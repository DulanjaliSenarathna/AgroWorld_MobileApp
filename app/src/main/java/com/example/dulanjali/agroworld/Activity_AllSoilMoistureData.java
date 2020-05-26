package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dulanjali.agroworld.common.Stables;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_AllSoilMoistureData extends AppCompatActivity {
    LineChart soil_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_soil_moisture_data);

        soil_data = findViewById(R.id.chart1);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(),"Data set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        soil_data.setData(data);
        soil_data.invalidate();

        getRecentData();
    }

    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0,20));
        dataVals.add(new Entry(1,24));
        dataVals.add(new Entry(2,22));
        dataVals.add(new Entry(3,27));
        dataVals.add(new Entry(4,20));

        return dataVals;

    }

    private void getRecentData() {

        RequestQueue requestQueue= Volley.newRequestQueue(Activity_AllSoilMoistureData.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(new Stables().getRecentDetails(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Toast.makeText(Activity_AllSoilMoistureData.this, jsonObject.getString("created_at") +":"+jsonObject.getString("value"), Toast.LENGTH_SHORT).show();

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

package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class Activity_All_Humidity extends AppCompatActivity {

    LineChart prev_hum_data;
    ArrayList<Entry> dataList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__all__humidity);

        prev_hum_data = findViewById(R.id.chart4);

        try {
            getRecentHumidityData();


        } catch (Exception e) {

        }
    }

    private ArrayList<Entry> dataValues3()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0,20));
        dataVals.add(new Entry(1,24));
        dataVals.add(new Entry(2,22));
        dataVals.add(new Entry(3,27));
        dataVals.add(new Entry(4,20));

        return dataVals;

    }

    private void getRecentHumidityData()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(Activity_All_Humidity.this);
        final ArrayList<Entry> dataVals = new ArrayList<Entry>();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, new Stables().getePrevHumData(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = (JSONArray) response.get("data");
                    for (int i = 0; i < data.length() ; i++) {
                        JSONArray item = data.getJSONArray(i);
                        String x = (String) item.get(0);
                        String y = (String) item.get(1);
                        Float yValue = Float.parseFloat(y);
                        dataVals.add(new Entry(i,yValue));

                    }
                    dataList3 = dataVals;
                    LineDataSet lineDataSet1 = new LineDataSet(dataList3,"Humidity");
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(lineDataSet1);

                    LineData data1 = new LineData(dataSets);
                    prev_hum_data.setData(data1);
                    prev_hum_data.invalidate();
                    System.out.println(dataList3);
                } catch (JSONException e) {
                    e.printStackTrace();
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

package com.example.dulanjali.agroworld.common;

import android.widget.Toast;

public class Stables {
    public static String baseUrl="http://io.adafruit.com/api/v2/dulanjali/feeds/";

    public String url(){
        String url="";
        try {
            url=baseUrl+"water-pump/data";
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }


    public String getSoilDetails(){
        return baseUrl+"soil-moisture/data/last";
    }

    public String getRecentDetails(){
        return baseUrl+"soil-moisture/data?limit=10";
    }
}

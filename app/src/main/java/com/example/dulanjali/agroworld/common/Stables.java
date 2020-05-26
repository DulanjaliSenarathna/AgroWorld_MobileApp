package com.example.dulanjali.agroworld.common;

public class Stables {

    // Base Url of API
    public static String baseUrl="http://io.adafruit.com/api/v2/dulanjali/feeds/";

    // Url of water pump API
    public String url()
    {
        String url="";
        try {
            url=baseUrl+"water-pump/data";
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    // Url of soil moisture data
    public String getSoilDetails()
    {
        return baseUrl+"soil-moisture/data/last";
    }

    // Url of last 10 soil moisture data
    public String getRecentDetails()
    {
        return baseUrl+"soil-moisture/data?limit=10";
    }

    
}

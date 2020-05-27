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

    public String getTemperatureDetails()
    {
        return baseUrl+"temperature/data/last";
    }

    public String getHumidityDetails()
    {
        return baseUrl+"humidity/data/last";
    }

    // Url of last 144 hours soil-moisture data
    public String geteChartData()
    {
        return baseUrl + "soil-moisture/data/chart?hours=144&resolution=30";
    }
    // Url of last 144 hours motion data
    public String geteMotionData()
    {
        return baseUrl + "motion/data/chart?hours=144&resolution=30";
    }
    // Url of last 144 hours temperature data
    public String getePrevTempData()
    {
        return baseUrl + "temperature/data/chart?hours=144&resolution=30";
    }
    // Url of last 144 hours humidity data
    public String getePrevHumData()
    {
        return baseUrl + "humidity/data/chart?hours=144&resolution=30";
    }
}

package com.example.dulanjali.agroworld;

public class SoilMoisture
{
    private String value;
    private String date_time;

    public SoilMoisture() {
    }

    public SoilMoisture(String value, String date_time) {
        this.value = value;
        this.date_time = date_time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}

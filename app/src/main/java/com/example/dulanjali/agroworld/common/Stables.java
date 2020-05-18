package com.example.dulanjali.agroworld.common;

import android.widget.Toast;

public class Stables {

    public String url(){
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        String url="";
        try {
            url="http://io.adafruit.com/api/v2/dulanjali/feeds/water-pump/data";
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;

    }
}

package com.brian.weatherapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
  TextView location, date, dewpoint, humidity, temp, station, elevation, windspeed, cloud;
  Handler handler;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActionBar bar = getSupportActionBar();
    bar.hide();
    setContentView(R.layout.activity_main);
    handler = new Handler();
    location = (TextView) findViewById(R.id.location);
    date = (TextView) findViewById(R.id.date);
    dewpoint = (TextView) findViewById(R.id.dewpoint);
    humidity = (TextView) findViewById(R.id.humidity);
    temp = (TextView) findViewById(R.id.temp);
    station = (TextView) findViewById(R.id.station);
    elevation = (TextView) findViewById(R.id.elevation);
    windspeed = (TextView) findViewById(R.id.windspeed);
    cloud = (TextView) findViewById(R.id.clouds);
    updateWeatherData();

  }
  private void updateWeatherData(){
    Thread rt = new Thread(){
      public void run(){
        final JSONObject json = Fetch.getJSON();
        if(json == null){
          handler.post(new Runnable(){
            public void run(){
              Toast.makeText(MainActivity.this, "Cannot get Data", Toast.LENGTH_LONG).show();
            }
          });
        } else {
          handler.post(new Runnable(){
            public void run(){
              renderWeather(json);
            }
          });
        }
      }
    };
    if (!rt.isAlive()){
      System.out.println("starting thread");
      rt.start();
    }
    if (rt.isAlive()){
      System.out.println("Thread is alive");
    }
  }
  private void renderWeather(JSONObject json){
    try {

      JSONObject details = json.getJSONObject("weatherObservation");
      date.setText(
              details.getString("datetime"));
      temp.setText(
             (details.getString("temperature")) + "℃");
      dewpoint.setText("Dewpoint:   " +
              details.getString("dewPoint"));
      windspeed.setText("Windspeed:   " +
              details.getString("windSpeed"));
      cloud.setText("Clouds:   " +
              details.getString("clouds"));
      humidity.setText("Humidity:   " +
              details.getDouble("humidity"));
      station.setText("Station:   " +
              details.getString("stationName"));
      elevation.setText("Elevation:   " +
              details.getDouble("elevation"));
    }catch(Exception e){
      Log.e("SimpleWeather", "One or more fields not found in the JSON data");
    }
  }


}

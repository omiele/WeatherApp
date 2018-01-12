package com.brian.weatherapp;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by omachi on 10/11/16.
 */
public class Fetch {
  private static final String GEONAMES_ENDPOINT =
          "http://api.geonames.org/findNearByWeatherJSON?lat=-1.1648729&lng=36.9574167&username=omachi";

  public static JSONObject getJSON(){
    try {
      URL url = new URL(String.format(GEONAMES_ENDPOINT));
      HttpURLConnection connection =
              (HttpURLConnection)url.openConnection();

      BufferedReader reader = new BufferedReader(
              new InputStreamReader(connection.getInputStream()));

      StringBuffer json = new StringBuffer(1024);
      String tmp="";
      while((tmp=reader.readLine())!=null)
        json.append(tmp).append("\n");
      reader.close();

      JSONObject data = new JSONObject(json.toString());

      // This value will be 404 if the request was not
      // successful


      return data;
    }catch(Exception e){
      System.out.println("System problem:" +e);
      return null;
    }
    }
  }


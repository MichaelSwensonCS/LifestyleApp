package com.example.lifestyleapp;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Repository {

    private static Repository instance;
    private final MutableLiveData<Weather> weatherData = new MutableLiveData<>();
    private WeatherDao mWeatherDao;
    private final Application application;


    private static final String url = "https://api.openweathermap.org/data/2.5/weather";
    private static final String appid = "89f3f38e5244773c033094b3a922b58a";
    private static DecimalFormat df = new DecimalFormat("#.##");

    private static String description;
    private static double temp;
    private static double feelsLike;
    private static float pressure;
    private static int humidity;
    private static String wind;
    private static String clouds;
    private static String countryName;
    private static String cityName;

    private Repository(Application application){
        locDB db = locDB.getDatabase(application);
        mWeatherDao = db.weatherDao();
        if(cityName !=null)
            getWeatherDetails(cityName, application);
            this.application = application;
    }

    public static synchronized Repository getInstance(Application application){
        if(instance==null){
            instance = new Repository(application);
        }
        return instance;
    }



    public static Weather getWeatherDetails(String city, Application application) {
        String tempUrl = "";
        if(city.equals("")){
            throw new IllegalArgumentException("City was blank! Can't Find Weather");
        }else{
            tempUrl = url + "?q=" + city + "&appid=" + appid;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        temp = jsonObjectMain.getDouble("temp") - 273.15;
                        feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        pressure = jsonObjectMain.getInt("pressure");
                        humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        countryName = jsonObjectSys.getString("country");
                        cityName = jsonResponse.getString("name");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(application.getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(application.getApplicationContext());
            requestQueue.add(stringRequest);

        }
        return new WeatherBuilder()
                .setCity(cityName)
                .setCountry(countryName)
                .setClouds(clouds)
                .setDescription(description)
                .setHumidity(humidity)
                .setFeelsLike(feelsLike)
                .setTemp(temp)
                .setWind(wind)
                .setPressure(pressure)
                .createWeather();
    }
    public void setLocation(String location){
        cityName = location;
        insert();
    }

    private void insert(){
        if(cityName!=null && weatherData.toString() !=null) {
            WeatherTable weatherTable = new WeatherTableBuilder().setCity(cityName).setWeather(weatherData.toString()).createWeatherTable();
            locDB.databaseExecutor.execute(() -> {
                mWeatherDao.insert(weatherTable);
            });
        }
    }

    public MutableLiveData<Weather> getData() {
        return weatherData;
    }

}

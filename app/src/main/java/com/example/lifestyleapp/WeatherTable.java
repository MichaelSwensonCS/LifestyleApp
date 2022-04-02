package com.example.lifestyleapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_table")
public class WeatherTable {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "location")
    private String city = "";


    @NonNull
    @ColumnInfo(name = "weatherdata")
    private String weatherJson;

    public WeatherTable(@NonNull String city, @NonNull String weatherJson){
        this.city = city;
        this.weatherJson = weatherJson;
    }

    public void setLocation(String location){
        this.city = city;
    }

    public void setWeatherJson(String weatherdata){
        this.weatherJson = weatherdata;
    }

    public String getLocation(){
        return city;
    }

    public String getWeatherJson(){
        return weatherJson;
    }

    @NonNull
    public String getCity() {
        return city;
    }
}
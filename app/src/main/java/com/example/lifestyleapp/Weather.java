package com.example.lifestyleapp;

public class Weather {
    public void setDEFAULT_VALUE(double DEFAULT_VALUE) {
        this.DEFAULT_VALUE = DEFAULT_VALUE;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public void setClouds(String clouds) {
        Clouds = clouds;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private double DEFAULT_VALUE = Double.MAX_VALUE;
    private String description;
    private double temp;
    private double feelsLike;
    private float pressure;
    private int humidity;
    private String wind;
    private String Clouds;
    private String city;
    private String country;
    private String clouds;


    public Weather(String description, double temp, double feelsLike, float pressure, int humidity, String wind, String clouds, String city, String country) {
        this.description = description;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind = wind;
        Clouds = clouds;
        this.city = city;
        this.country = country;
    }
    public Weather() {
        this.description = "";
        this.temp = DEFAULT_VALUE;
        this.feelsLike = DEFAULT_VALUE;
        this.pressure = Float.MAX_VALUE;
        this.humidity = Integer.MAX_VALUE;
        this.wind = "";
        Clouds = "";
        this.city = "";
        this.country = "";
    }

    @Override
    public String toString() {
        return city + ","
                + country + ","
                + clouds + ","
                +  description + ","
                + feelsLike + ","
                + humidity + ","
                + pressure + ","
                + temp + ","
                + wind;
    }

}

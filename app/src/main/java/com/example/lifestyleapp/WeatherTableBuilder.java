package com.example.lifestyleapp;

public class WeatherTableBuilder {
    private String city;
    private String weather;

    public WeatherTableBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public WeatherTableBuilder setWeather(String weather) {
        this.weather = weather;
        return this;
    }

    public WeatherTable createWeatherTable() {
        return new WeatherTable(city, weather);
    }
}
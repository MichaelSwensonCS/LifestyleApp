package com.example.lifestyleapp;

public class WeatherBuilder {
    private String description;
    private double temp;
    private double feelsLike;
    private float pressure;
    private int humidity;
    private String wind;
    private String clouds;
    private String city;
    private String country;

    public WeatherBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public WeatherBuilder setTemp(double temp) {
        this.temp = temp;
        return this;
    }

    public WeatherBuilder setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
        return this;
    }

    public WeatherBuilder setPressure(float pressure) {
        this.pressure = pressure;
        return this;
    }

    public WeatherBuilder setHumidity(int humidity) {
        this.humidity = humidity;
        return this;
    }

    public WeatherBuilder setWind(String wind) {
        this.wind = wind;
        return this;
    }

    public WeatherBuilder setClouds(String clouds) {
        this.clouds = clouds;
        return this;
    }

    public WeatherBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public WeatherBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public Weather createWeather() {
        return new Weather(description, temp, feelsLike, pressure, humidity, wind, clouds, city, country);
    }
}
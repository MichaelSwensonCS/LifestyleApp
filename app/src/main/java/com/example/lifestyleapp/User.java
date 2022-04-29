package com.example.lifestyleapp;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String firstname;
    public String lastname;
    public int age = -1;
    public int weight = -1;
    public String country;
    public String city;
    public int heightinches = -1;
    public int heightfeet = -1;
    public String gender;
    public int stepcount = 1;

    public String weather;

    // TODO: Serialize image into sql database
    @Ignore
    public Bitmap photo;

    public void copy(User user) {
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.age = user.age;
        this.weight = user.weight;
        this.country = user.country;
        this.city = user.city;
        this.heightinches = user.heightinches;
        this.heightfeet = user.heightfeet;
        this.gender = user.gender;
        this.photo = user.photo;
        this.stepcount = user.stepcount;
    }

    public User clone() {
        User newUser = new User();
        newUser.copy(this);
        return newUser;
    }

    /*
    Use this method to convert a User object into a Json string.
     */
    public String ToJson()
    {
        String json = new Gson().toJson(this);
        return json;
    }

    /*
    Use this method to convert a Json string into a User object.
     */
    public static User FromJson(String json)
    {
        User user = new Gson().fromJson(json, User.class);
        return user;
    }
}

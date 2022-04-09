package com.example.lifestyleapp;

import android.graphics.Bitmap;

import com.google.gson.Gson;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstname;
    @ColumnInfo(name = "last_name")
    public String lastname;
    @ColumnInfo(name = "age")
    public int age = -1;
    @ColumnInfo(name = "weight")
    public int weight = -1;
    @ColumnInfo(name = "country")
    public String country;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "height_inches")
    public int heightinches = -1;
    @ColumnInfo(name = "height_feet")
    public int heightfeet = -1;
    @ColumnInfo(name = "gender")
    public String gender;

    // TODO: Serialize image
    @ColumnInfo(name = "photo")
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

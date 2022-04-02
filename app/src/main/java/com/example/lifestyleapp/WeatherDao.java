package com.example.lifestyleapp;
import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherTable weatherTable);

    @Query("DELETE FROM weather_table")
    void deleteAll();

    @Query("SELECT * from weather_table ORDER BY weatherdata ASC")
    LiveData<List<WeatherTable>> getAll();

//    @Query("SELECT * FROM weather_table where weather_table.location == :city")
//    LiveData<Weather> findByCity(String city);

}

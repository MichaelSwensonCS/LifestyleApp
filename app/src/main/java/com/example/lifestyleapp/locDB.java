package com.example.lifestyleapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



@Database(entities = {WeatherTable.class}, version = 1, exportSchema = false)
public abstract class locDB extends RoomDatabase {
    private static volatile locDB mInstance;
    public abstract WeatherDao weatherDao();
    static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(4);

    static synchronized locDB getDatabase(final Context context){
        if(mInstance==null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    locDB.class, "weather.db").addCallback(sRoomDatabaseCallback).build();
        }
        return mInstance;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseExecutor.execute(()->{
                WeatherDao dao = mInstance.weatherDao();
                dao.deleteAll();
                WeatherTable weatherTable = new WeatherTableBuilder().setCity("dummy_loc").setWeather("dummy_data").createWeatherTable();
                dao.insert(weatherTable);
            });
        }
    };

    private static RoomDatabase.Callback sRoomDatabaseCallback2 = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbTask(mInstance).execute();
        }
    };
    private static class PopulateDbTask{
        private final WeatherDao mDao;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        PopulateDbTask(locDB db){
            mDao = db.weatherDao();
        }

        public void execute(){
            executorService.execute(new Runnable(){
                @Override
                public void run(){
                    mDao.deleteAll();
                    WeatherTable weatherTable = new WeatherTableBuilder().setCity("dummy_loc").setWeather("dummy_data").createWeatherTable();
                    mDao.insert(weatherTable);
                }
            });
        }
    }

}
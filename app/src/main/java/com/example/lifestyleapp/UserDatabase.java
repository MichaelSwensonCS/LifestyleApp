package com.example.lifestyleapp;

import android.content.Context;
import android.os.AsyncTask;
import android.service.autofill.UserData;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase{

    private static UserDatabase instance;

    public abstract UserDAO userDAO();

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db) {
          super.onCreate(db);
          new PopulateDBAsyncTask(instance).execute();
      }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDAO userDAO;

        private PopulateDBAsyncTask(UserDatabase db) {
            userDAO = db.userDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!userDAO.getAllUsers().getValue().isEmpty())
                return null;
            User user = new User();
            user.firstname = "New";
            user.lastname = "User";
            userDAO.insert(user);
            return null;
        }
    }
 }

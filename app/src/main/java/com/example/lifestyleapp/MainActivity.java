package com.example.lifestyleapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.lifestyleapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Configuration configuration = this.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        int smallestScreenWidthDp = configuration.smallestScreenWidthDp;

        if(smallestScreenWidthDp > 600){
            NavigationView navigationView = findViewById(R.id.navigation_menu_big);
            NavController navController = Navigation.findNavController(this,  R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navigationView, navController);
        }else{
            BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_menu);
            NavController navController = Navigation.findNavController(this,  R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        UserViewModel model = new ViewModelProvider(this).get(UserViewModel.class);

        /*ProfileFragment profilePopup = new ProfileFragment();
        profilePopup.setCancelable(false);
        profilePopup.show(getSupportFragmentManager(), null);*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadFile();
    }

    private void uploadFile() {

        if(isExternalStorageWritable()) {
            //UserDatabase.getInstance(this.getBaseContext()).close();

            File db2 = getDatabasePath("user_database");
            //UserDatabase db = UserDatabase.getInstance(this.getApplication());
            //UserDAO userDao;
            //userDao = db.userDAO();
           // userDao.checkpoint(new SimpleSQLiteQuery("pragma wal_checkpoint(full)"));
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(db2));
                writer.close();
            } catch (Exception exception) {
                Log.e("MyAmplifyApp", "Upload failed", exception);
            }

            Amplify.Storage.uploadFile(
                    "LifeStyleAppBak",
                    db2,
                    result -> Log.i("LifeStyleApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("LifeStyleApp", "Upload failed", storageFailure)
            );
        }
    }
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
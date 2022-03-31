package com.example.lifestyleapp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.lifestyleapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        ProfileFragment profilePopup = new ProfileFragment();
        profilePopup.setCancelable(false);
        profilePopup.show(getSupportFragmentManager(), null);
    }
}
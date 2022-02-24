package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.lifestyleapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_menu);
        NavController navController = Navigation.findNavController(this,  R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        ProfileFragment profilePopup = new ProfileFragment();
        profilePopup.setCancelable(false);
        profilePopup.show(getSupportFragmentManager(), null);
    }
}
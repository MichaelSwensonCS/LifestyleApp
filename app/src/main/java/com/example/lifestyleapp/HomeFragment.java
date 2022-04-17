package com.example.lifestyleapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lifestyleapp.databinding.FragmentHomePageBinding;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private static FragmentHomePageBinding binding;
    private UserViewModel model;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /*public static void updateInfo() {
        User user = ProfileFragment.user;
        if(binding != null && user != null)
            binding.tvWelcome.setText("Welcome " + user.firstname + " " + user.lastname);
    }*/

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        final Observer<List<User>> usersObserver = new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                update(model.getUsers().getValue().get(model.getUser().getValue()));
            }
        };
        model.getUsers().observe(getViewLifecycleOwner(), usersObserver);

        final Observer<Integer> userObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer user) {
                if (model.getUsers().getValue() != null && !model.getUsers().getValue().isEmpty())
                    update(model.getUsers().getValue().get(user));
            }
        };
        model.getUser().observe(getViewLifecycleOwner(), userObserver);

        binding.btnFindHike.setOnClickListener(this);
        binding.btnCalcBmi.setOnClickListener(this);
        binding.btnWeather.setOnClickListener(this);
        binding.btnFitGoals.setOnClickListener(this);
    }

    public static void update(User user) {
        if (binding != null) {
            String welcome = String.format("Welcome %s %s", user.firstname, user.lastname);
            binding.tvWelcome.setText(welcome);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            //TODO: change this to a popup or something
            case R.id.btn_CalcBmi:
                Toast toast;
                User user = model.getUsers().getValue().get(model.getUser().getValue());
                // BMI = 703 × pounds/(inches)^2
                if (user == null || user.heightinches < 0 || user.heightfeet < 0 || user.weight < 0)
                    toast = Toast.makeText(getContext(), "Profile info is incomplete.", Toast.LENGTH_LONG);
                else
                    toast = Toast.makeText(getContext(), "BMI: " +
                            703 * user.weight/Math.pow(user.heightfeet*12 + user.heightinches,2),  Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.btn_FitGoals:
                NavHostFragment.findNavController((HomeFragment.this)).navigate(R.id.action_homeFragment_to_fitnessFragment);
                break;
            case R.id.btn_Weather:
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_weatherDisplay);
                break;
            case R.id.btn_FindHike:
                findHikes();
                break;
        }
    }

    private void findHikes() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Hikes%20Near%20Me");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }
}

package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lifestyleapp.databinding.FragmentFitnessPageBinding;

public class FitnessFragment extends Fragment implements View.OnClickListener {

    private static FragmentFitnessPageBinding binding;
    private static double bmr;

    private UserViewModel model;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFitnessPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public static void updateInfo(User user) {
        bmr = -1;
        if (binding == null)
            return;
        if (user == null || user.age < 0 || user.heightinches < 0 || user.heightfeet < 0 || user.weight < 0)
            binding.estimatedBMR.setText("Estimated BMR: Profile Incomplete");
        else {
            // Using the Harris-Benedict formula
            // Adult male: 66 + (6.3 x body weight in lbs.) + (12.9 x height in inches) - (6.8 x age in years) = BMR
            // Adult female: 655 + (4.3 x weight in lbs.) + (4.7 x height in inches) - (4.7 x age in years) = BMR
            // Sedentary is 1.2, active is between moderately and very active, 1.55 and 1.725
            if (user.gender.equals("female"))
                bmr = 655 + (4.3 * user.weight) + 4.7 * (12 * user.heightfeet + user.heightinches) - (4.7 * user.age);
            else
                bmr = 66 + (6.3 * user.weight) + 12.9 * (12 * user.heightfeet + user.heightinches) - (6.8 * user.age);
            binding.estimatedBMR.setText("Estimated BMR: " + bmr);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        final Observer<Integer> userObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer user) {
                if(model.getUsers().getValue() != null){
                    updateInfo(model.getUsers().getValue().get(user));
                }
            }
        };
        model.getUser().observe(getViewLifecycleOwner(), userObserver);

        binding.btnCalcBMR.setOnClickListener(this);
        binding.radioCurrentLifestyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(model.getUsers().getValue() != null) {
                    updateInfo(model.getUsers().getValue().get(model.getUser().getValue()));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_CalcBMR:
                //TODO: change this to a popup
                if (bmr < 0) {
                    Toast.makeText(getContext(), "Profile info is incomplete", Toast.LENGTH_SHORT).show();
                    break;
                }

                double lifestyle = binding.radioSedentary.isChecked() ? 1.2 : 1.6;
                if (binding.radioMaintainWeight.isChecked()) {
                    Toast.makeText(getContext(), "Calories: " + bmr * lifestyle, Toast.LENGTH_LONG).show();
                    break;
                }

                double userBMR = -1;
                try {
                    userBMR = Integer.parseInt(binding.userBMR.getText().toString());
                    if (userBMR < 0) throw new NumberFormatException();
                } catch (final NumberFormatException e) {
                    Toast.makeText(getContext(), "Desired change of pounds/week is invalid", Toast.LENGTH_SHORT).show();
                    break;
                }

                User user = model.getUsers().getValue().get(model.getUser().getValue());
                // 3500 calories per pound of fat
                // Approximately 500 calories per pound per day
                if (binding.radioLoseWeight.isChecked()) {
                    double newBMR = bmr * lifestyle - 500 * userBMR;
                    String text = "Calories: " + newBMR;
                    if (user.gender.equals("female")) {
                        if (newBMR < 1000)
                            text += "\n Warning: < 1000 calories recommended for women";
                    }
                    else if (newBMR < 1200)
                        text += "\n Warning: < 1200 calories recommended for men";
                    Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
                } else if (binding.radioGainWeight.isChecked()) {
                    double newBMR = bmr * lifestyle + 500 * userBMR;
                    Toast.makeText(getContext(), "Calories: " + newBMR, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
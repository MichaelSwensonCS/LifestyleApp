package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lifestyleapp.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment implements View.OnClickListener {
    Button home;
    Button profile;
    Button submit;

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        home = (Button) getActivity().findViewById(R.id.btn_home);
        home.setOnClickListener(this);

        profile = (Button) getActivity().findViewById(R.id.btn_Profile);
        profile.setOnClickListener(this);



    }
        @Override
        public void onClick(View view) {
            switch(view.getId()){

                case R.id.btn_home:
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_fitness_to_home);
                    break;
                case R.id.btn_FitReg:
                    break;
                case R.id.btn_Profile:
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_FitnessReg_to_Profile);
                case R.id.btn_CalcBMR:
                    //TODO CALC BMR
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
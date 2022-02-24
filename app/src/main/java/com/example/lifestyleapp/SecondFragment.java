package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lifestyleapp.databinding.FragmentFitnessPageBinding;
import com.example.lifestyleapp.databinding.FragmentProfilePageBinding;

public class SecondFragment extends Fragment implements View.OnClickListener {
    Button home;
    Button profile;
    Button submit;

    private FragmentFitnessPageBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFitnessPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        home = (Button) getActivity().findViewById(R.id.btn_homeFitness);
        home.setOnClickListener(this);

        profile = (Button) getActivity().findViewById(R.id.btn_ProfileFitness);
        profile.setOnClickListener(this);

    }
        @Override
        public void onClick(View view) {
            switch(view.getId()){

                case R.id.btn_homeFitness:
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_fitness_to_home);
                    break;

                case R.id.btn_ProfileFitness:
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_FitnessReg_to_Profile);
                    break;
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
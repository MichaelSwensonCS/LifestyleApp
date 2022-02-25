package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lifestyleapp.databinding.FragmentFitnessPageBinding;

public class FitnessFragment extends Fragment implements View.OnClickListener {

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

    }
        @Override
        public void onClick(View view) {
            switch(view.getId()){

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
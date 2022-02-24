package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class NavigationFragment extends Fragment implements View.OnClickListener{
    @Override
    public void onClick(View view) {

    }

    /*
    private FragmentNavigationBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnPrevious.setOnClickListener(this);
        binding.btnHome.setOnClickListener(this);
        binding.btnFitness.setOnClickListener(this);
        binding.btnProfile.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        NavHostFragment.findNavController(SecondFragment.this)
            .navigate(R.id.action_SecondFragment_to_FirstFragment);
        switch (view.getId()) {
            case R.id.btn_home:
                binding.contentMain.setDisplayedChild(0);
                break;
            case R.id.btn_profile:
                binding.contentMain.setDisplayedChild(1);
                break;
        }
    } */
}
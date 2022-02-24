package com.example.lifestyleapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFrag extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button toWeather;
    Button toFitness;
    Button toProfile;
    Button toFitGoals;
    // TODO: Rename and change types of parameters


    public HomeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFrag newInstance(String param1, String param2) {
        HomeFrag fragment = new HomeFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toWeather = (Button) getActivity().findViewById(R.id.btn_Weather);
        toWeather.setOnClickListener(this);

        toFitGoals = (Button) getActivity().findViewById(R.id.btn_FitGoals);
        toFitGoals.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btn_Weather:
                //NavHostFragment.findNavController(HomeFrag.this).navigate(R.id.action_home_to_weather);
                break;
            case R.id.btn_FitGoals:
                //NavHostFragment.findNavController(HomeFrag.this).navigate(R.id.action_HomeFrag_to_FitnessReg);
                break;
            default:
                break;
        }
    }
}
package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lifestyleapp.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //This was needed to populate the dropdown menu(Spinner age)
        List age = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        ComboBox ageSpinner = (ComboBox) getActivity().findViewById(R.id.ageSpinner);
        ageSpinner.setAdapter(spinnerArrayAdapter);
        //

        //City autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                getActivity().findViewById(R.id.cityAuto);
        textView.setAdapter(adapter);

        //Country
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, COUNTRIES);
        AutoCompleteTextView textViewCountry = (AutoCompleteTextView)
                getActivity().findViewById(R.id.countryAuto);
        textView.setAdapter(adapter);

        //Height
        List feetList = new ArrayList<Integer>();
        for(int i = 2; i <= 7; i++){
            feetList.add(Integer.toString((i)));
        }
        ArrayAdapter<Integer> feetArrayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, feetList);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        ComboBox feetSpinner = (ComboBox) getActivity().findViewById(R.id.feetSpinner);
        feetSpinner.setAdapter(feetArrayAdapter);

        List inchList = new ArrayList<Integer>();
        for(int i = 0; i <= 11; i++){
            inchList.add(Integer.toString((i)));
        }
        ArrayAdapter<Integer> inchArrayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, inchList);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        ComboBox inchSpinner = (ComboBox) getActivity().findViewById(R.id.inchSpinner);
        inchSpinner.setAdapter(inchArrayAdapter);
        //End height




        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();

                // Check which radio button was clicked
                switch(view.getId()) {
                    case R.id.maleRadioBtn:
                        if (checked)
                            // Pirates are the best
                            break;
                    case R.id.femaleRadioBtn:
                        if (checked)
                            // Ninjas rule
                            break;
                }
            }
        });
    }
/*
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    break;
        }


    } */
    private static final String[] COUNTRIES = new String[] {
            "United States","Canada","Mexico"
    };

    private static final String[] CITIES = new String[] {
            "Salt Lake City","West Valley City","Provo", "Orem", "Ogden", "Park City"
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
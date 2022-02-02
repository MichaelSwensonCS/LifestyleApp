package com.example.lifestyleapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lifestyleapp.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements View.OnClickListener{

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

        AutoCompleteTextView ageSpinner = (AutoCompleteTextView) getActivity().findViewById(R.id.ageDropDown);
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

        AutoCompleteTextView feetSpinner = (AutoCompleteTextView) getActivity().findViewById(R.id.feetAuto);
        feetSpinner.setAdapter(feetArrayAdapter);

        List inchList = new ArrayList<Integer>();
        for(int i = 0; i <= 11; i++){
            inchList.add(Integer.toString((i)));
        }
        ArrayAdapter<Integer> inchArrayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, inchList);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        AutoCompleteTextView inchSpinner = (AutoCompleteTextView) getActivity().findViewById(R.id.inchesAuto);
        inchSpinner.setAdapter(inchArrayAdapter);
        //End height

        Context context = getContext();
        CharSequence text = "Female Selected";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text,duration);

        //AppCompatButton button = (AppCompatButton) getActivity().findViewById(R.id.uploadPicture);
        //button.setOnClickListener(this);

        RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioSexGroup);
        binding.radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioFemale:
                        toast.show();
                        break;
                    case R.id.radioMale:
                    default:
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

    @Override
    public void onClick(View view) {
        Context context = getContext();
        CharSequence text = view.getId() + "";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text,duration);

        switch(view.getId()){
            case R.id.radioFemale:
                toast.show();
                break;
            case R.id.radioMale:
                toast.show();
                break;
            case R.id.submitBtn:
                toast.show();
                break;
            case R.id.uploadPicture:
                toast.show();
        }
    }
}
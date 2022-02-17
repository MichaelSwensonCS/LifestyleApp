package com.example.lifestyleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lifestyleapp.databinding.FragmentFirstBinding;
import com.example.lifestyleapp.databinding.FragmentProfilePageBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private static final String[] COUNTRIES = new String[] {
            "United States","Canada","Mexico"
    };

    private static final String[] CITIES = new String[] {
            "Salt Lake City","West Valley City","Provo", "Orem", "Ogden", "Park City"
    };

    private FragmentFirstBinding binding;
    //TODO Move weight up near age to fill empty gap

    Bitmap mThumbnailImage;

    //AppCompatButton submitButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Intent mDisplayIntent;

    private TextInputEditText name_first;
    private TextInputEditText name_last;
    private AutoCompleteTextView age;
    private TextInputEditText weight;
    private AutoCompleteTextView city;
    private AutoCompleteTextView country;
    private AutoCompleteTextView height_feet;
    private AutoCompleteTextView height_inch;

    private static User user;

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

        boolean init = false;
        // Create user
        if (user == null) {
            user = new User();
            init = true;
            // The radio buttons are on male by default
            user.gender = "male";
        }

        AppCompatButton submitButton =(AppCompatButton) getActivity().findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(this);

        //This might be a problem
        mDisplayIntent = new Intent(getActivity(),view.getClass());

        // Name
        name_first = getActivity().findViewById(R.id.userFirstName);
        name_first.setText(init ? "" : user.firstname);
        name_last = getActivity().findViewById(R.id.userLastName);
        name_last.setText(init ? "" : user.lastname);

        // Age
        List ages = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            ages.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> agesAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, ages);
        agesAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        age = getActivity().findViewById(R.id.ageDropDown);
        age.setText(init || user.age < 0 ? "" : "" + user.age);
        age.setAdapter(agesAdapter);

        // Weight
        weight = getActivity().findViewById(R.id.userWeight);
        weight.setText(init || user.weight < 0 ? "" : "" + user.weight);

        //City autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, CITIES);
        city = getActivity().findViewById(R.id.cityAuto);
        city.setText(init ? "" : user.city);
        city.setAdapter(adapter);

        // Country
        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, COUNTRIES);
        country = getActivity().findViewById(R.id.countryAuto);
        country.setText(init ? "" : user.country);
        country.setAdapter(countriesAdapter);

        // Height
        List heights_feet = new ArrayList<Integer>();
        for(int i = 2; i <= 7; i++){
            heights_feet.add(Integer.toString((i)));
        }
        ArrayAdapter<Integer> feetAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, heights_feet);
        feetAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        height_feet = getActivity().findViewById(R.id.feetAuto);
        height_feet.setText(init || user.heightfeet < 0 ? "" : "" + user.heightfeet);
        height_feet.setAdapter(feetAdapter);

        List heights_inch = new ArrayList<Integer>();
        for(int i = 0; i <= 11; i++){
            heights_inch.add(Integer.toString((i)));
        }
        ArrayAdapter<Integer> inchAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, heights_inch);
        inchAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        height_inch = getActivity().findViewById(R.id.inchesAuto);
        height_inch.setText(init || user.heightinches < 0 ? "" : "" + user.heightinches);
        height_inch.setAdapter(inchAdapter);

        // Sex
        RadioGroup radioGroup = getActivity().findViewById(R.id.radioSexGroup);
        binding.radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioFemale:
                        user.gender = "female";
                        break;
                    case R.id.radioMale:
                        user.gender = "male";
                        break;
                }
            }
        });

        // Bind click listener to buttons
        AppCompatButton button = getActivity().findViewById(R.id.uploadPicture);
        button.setOnClickListener(this);

        button = getActivity().findViewById(R.id.submitBtn);
        button.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showToast(String msg, int duration) {
        Toast toast = Toast.makeText(getContext(), msg, duration);
        toast.show();
    }

    private boolean saveInfo(boolean validate) {
        // Pull name
        if (!validate || !name_first.getText().toString().isEmpty()) user.firstname = name_first.getText().toString();
        else {
            showToast("First name cannot be empty", Toast.LENGTH_SHORT);
            return false;
        }
        if (!validate || !name_last.getText().toString().isEmpty()) user.lastname = name_last.getText().toString();
        else {
            showToast("Last name cannot be empty", Toast.LENGTH_SHORT);
            return false;
        }

        // Parse age
        if (age.getText().toString().isEmpty()) user.age = -1;
        else try {
            int years = Integer.parseInt(age.getText().toString());
            if (years < 0) throw new NumberFormatException();
            user.age = years;
        }
        catch (final NumberFormatException e) {
            if (!validate) user.age = -1;
            else {
                showToast("Invalid age", Toast.LENGTH_SHORT);
                return false;
            }
        }

        // Parse weight
        if (weight.getText().toString().isEmpty()) user.weight = -1;
        else try {
            int pounds = Integer.parseInt(weight.getText().toString());
            if (pounds < 0) throw new NumberFormatException();
            user.weight = pounds;
        }
        catch (final NumberFormatException e) {
            if (!validate) user.weight = -1;
            else {
                showToast("Invalid weight", Toast.LENGTH_SHORT);
                return false;
            }
        }

        // Pull city and country
        user.country = country.getText().toString();
        user.city = city.getText().toString();

        // Parse height
        if (height_feet.getText().toString().isEmpty()) user.heightfeet = -1;
        else try {
            int height = Integer.parseInt(height_feet.getText().toString());
            if (height < 0) throw new NumberFormatException();
            user.heightfeet = height;
        }
        catch (final NumberFormatException e) {
            if (!validate) user.heightfeet = -1;
            else {
                showToast("Invalid height", Toast.LENGTH_SHORT);
                return false;
            }
        }

        if (height_inch.getText().toString().isEmpty()) user.heightinches = -1;
        else try {
            int height = Integer.parseInt(height_inch.getText().toString());
            if (height < 0) throw new NumberFormatException();
            user.heightinches = height;
        }
        catch (final NumberFormatException e) {
            if (!validate) user.heightinches = -1;
            else {
                showToast("Invalid height", Toast.LENGTH_SHORT);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.submitBtn:
                // BMI = 703 × pounds/(inches)^2
                if (saveInfo(true)){
                    // switch to another fragment
                    NavHostFragment.findNavController(ProfileFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                }
                break;

            case R.id.uploadPicture:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;

            default:
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){

            //Get the bitmap
            Bundle extras = data.getExtras();
            mThumbnailImage = (Bitmap) extras.get("data");

            //Open a file and write to it
            if(isExternalStorageWritable()){
                String filePathString = saveImage(mThumbnailImage);
                mDisplayIntent.putExtra("imagePath",filePathString);
                ImageView profile = (ImageView) getActivity().findViewById(R.id.imageView);
                profile.setImageBitmap(mThumbnailImage);
                user.photo = mThumbnailImage;
            }
            else{
                Toast.makeText(getActivity(),"External storage not writable.",Toast.LENGTH_SHORT).show();
            }

        }
    }

    String currentPhotoPath;
    private String saveImage(Bitmap finalBitmap) {
        String APP_TAG = "LifestyleApp";
        String root = Environment.getExternalStorageDirectory().toString();
        //Had to change this
        File myDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Thumbnail_"+ timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getActivity(),"file saved!",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
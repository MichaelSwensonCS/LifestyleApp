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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lifestyleapp.databinding.FragmentInitialProfileBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileFragment extends DialogFragment implements View.OnClickListener{

    private static final String[] COUNTRIES = new String[] {
            "United States","Canada","Mexico"
    };

    private static final String[] CITIES = new String[] {
            "Salt Lake City","West Valley City","Provo", "Orem", "Ogden", "Park City"
    };

    private FragmentInitialProfileBinding binding;
    private UsersViewModel model;

    private static User user;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentInitialProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void init(boolean init) {

        // Name
        binding.userFirstName.setText(init ? "" : user.firstname);
        binding.userLastName.setText(init ? "" : user.lastname);

        // Age
        List ages = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            ages.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> agesAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, ages);
        agesAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        binding.ageDropDown.setText(init || user.age < 0 ? "" : "" + user.age);
        binding.ageDropDown.setAdapter(agesAdapter);

        // Weight
        binding.userWeight.setText(init || user.weight < 0 ? "" : "" + user.weight);

        //City autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, CITIES);
        binding.cityAuto.setText(init ? "" : user.city);
        binding.cityAuto.setAdapter(adapter);

        // Country
        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, COUNTRIES);
        binding.countryAuto.setText(init ? "" : user.country);
        binding.countryAuto.setAdapter(countriesAdapter);

        // Height
        List heights_feet = new ArrayList<Integer>();
        for(int i = 2; i <= 7; i++){
            heights_feet.add(Integer.toString((i)));
        }
        ArrayAdapter<Integer> feetAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, heights_feet);
        feetAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        binding.feetAuto.setText(init || user.heightfeet < 0 ? "" : "" + user.heightfeet);
        binding.feetAuto.setAdapter(feetAdapter);

        List heights_inch = new ArrayList<Integer>();
        for(int i = 0; i <= 11; i++){
            heights_inch.add(Integer.toString((i)));
        }
        ArrayAdapter<Integer> inchAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, heights_inch);
        inchAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );

        binding.inchesAuto.setText(init || user.heightinches < 0 ? "" : "" + user.heightinches);
        binding.inchesAuto.setAdapter(inchAdapter);

        // Sex
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

        if(binding.imageUser.getDrawable() == null && user.photo != null){
            binding.imageUser.setImageBitmap(user.photo);
        }
        // Bind click listener to buttons
        binding.uploadPicture.setOnClickListener(this);
        binding.submitBtn.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);

        boolean init = user == null;
        user = model.getUser().getValue();
        //user = model.getUser().getValue().clone();

        // The radio buttons are on male by default
        if (init)
            user.gender = "male";
        init(init);

        mDisplayIntent = new Intent(getActivity(), view.getClass());
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder db = new AlertDialog.Builder(getActivity());
        db.setView(getLayoutInflater().inflate(R.layout.fragment_initial_profile, null));
        AlertDialog ad = db.create();
        ad.setCancelable(false);
        ad.setCanceledOnTouchOutside(false);
        ad.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return ad;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean saveInfo(boolean validate) {
        // Pull name
        if (!validate || !binding.userFirstName.getText().toString().isEmpty()) user.firstname = binding.userFirstName.getText().toString();
        else {
            Toast.makeText(getContext(),"First name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validate || !binding.userLastName.getText().toString().isEmpty()) user.lastname = binding.userLastName.getText().toString();
        else {
            Toast.makeText(getContext(),"Last name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Parse age
        if (binding.ageDropDown.getText().toString().isEmpty()) user.age = -1;
        else try {
            int years = Integer.parseInt(binding.ageDropDown.getText().toString());
            if (years < 0) throw new NumberFormatException();
            user.age = years;
        }
        catch (final NumberFormatException e) {
            if (!validate) user.age = -1;
            else {
                Toast.makeText(getContext(),"Invalid age", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        // Parse weight
        if (binding.userWeight.getText().toString().isEmpty()) user.weight = -1;
        else try {
            int pounds = Integer.parseInt(binding.userWeight.getText().toString());
            if (pounds < 0) throw new NumberFormatException();
            user.weight = pounds;
        }
        catch (final NumberFormatException e) {
            if (!validate) user.weight = -1;
            else {
                Toast.makeText(getContext(),"Invalid weight", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(user.photo != null){
            binding.imageUser.setImageBitmap(user.photo);
        }

        // Pull city and country
        user.country = binding.countryAuto.getText().toString();
        user.city = binding.cityAuto.getText().toString();

        // Parse height
        if (binding.feetAuto.getText().toString().isEmpty()) user.heightfeet = -1;
        else try {
            int height = Integer.parseInt(binding.feetAuto.getText().toString());
            if (height < 0) throw new NumberFormatException();
            user.heightfeet = height;
        }
        catch (final NumberFormatException e) {
            if (!validate) user.heightfeet = -1;
            else {
                Toast.makeText(getContext(),"Invalid height", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (binding.inchesAuto.getText().toString().isEmpty()) user.heightinches = -1;
        else try {
            int height = Integer.parseInt(binding.inchesAuto.getText().toString());
            if (height < 0) throw new NumberFormatException();
            user.heightinches = height;
        }
        catch (final NumberFormatException e) {
            if (!validate) user.heightinches = -1;
            else {
                Toast.makeText(getContext(),"Invalid height", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.submitBtn:
                if (saveInfo(true)){
                    // Extremely lazy fix
                    HomeFragment.update(user);
                    // model.updateCurrentUser(user);
                    Toast.makeText(getContext(), "Info saved", Toast.LENGTH_SHORT).show();
                    if (getDialog() != null)
                        getDialog().dismiss();
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

    // TODO: save photo somewhere temporary and only override user when submit button is hit
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Intent mDisplayIntent;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){

            //Get the bitmap
            Bundle extras = data.getExtras();
            mThumbnailImage = (Bitmap) extras.get("data");
            user.photo = (Bitmap) extras.get("data");
            //Open a file and write to it
            if(isExternalStorageWritable()){
                String filePathString = saveImage(mThumbnailImage);
                mDisplayIntent.putExtra("imagePath",filePathString);
                ImageView HomeProfile = (ImageView) getActivity().findViewById(R.id.imageView);
                HomeProfile.setImageBitmap(mThumbnailImage);
                binding.imageUser.setImageBitmap(mThumbnailImage);
                user.photo = mThumbnailImage;
            }
            else{
                Toast.makeText(getActivity(),"External storage not writable.",Toast.LENGTH_SHORT).show();
            }

        }
    }

    Bitmap mThumbnailImage;
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
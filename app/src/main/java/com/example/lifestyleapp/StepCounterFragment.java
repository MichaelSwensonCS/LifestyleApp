package com.example.lifestyleapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lifestyleapp.databinding.FragmentStepCounter2Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepCounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepCounterFragment extends Fragment{

    private static FragmentStepCounter2Binding binding;

    // TODO: Rename and change types of parameters
    private SensorManager mSensorManager;
    private TextView mTvCurrentSteps;
    private Sensor mStepCounter;

    private TextView mTvRecordedSteps;
    private UserViewModel model;
    private static User user;
    public StepCounterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepCounterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepCounterFragment newInstance(String param1, String param2) {
        StepCounterFragment fragment = new StepCounterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        binding = FragmentStepCounter2Binding.inflate(inflater, container, false);

        mTvCurrentSteps = binding.tvYellowCircleSteps;
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        mTvRecordedSteps = binding.tvNumberSteps;

        model = new ViewModelProvider(this).get(UserViewModel.class);
        if(model.getUsers().getValue() != null){
            user = model.getUsers().getValue().get(model.getUser().getValue());
            user.stepcount++;
            model.update(user);
            // displays previously recorded step count
            mTvRecordedSteps.setText(user.stepcount);
        }
        return binding.getRoot();
    }

    private SensorEventListener mListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            mTvCurrentSteps.setText("" + String.valueOf(sensorEvent.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(mStepCounter!=null){
            mSensorManager.registerListener(mListener,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mStepCounter!=null){
            mSensorManager.unregisterListener(mListener);
        }
    }

}
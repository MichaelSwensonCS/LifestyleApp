package com.example.lifestyleapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;

// imports copied from example41
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

//  **TODO - implement start/stop step counter with gestures
public class StepCounterActivity extends AppCompatActivity {

    // majority of the step counting code was copied from example41
    private SensorManager mSensorManager;
    private TextView mTvCurrentSteps;
    private Sensor mStepCounter;

    private TextView mTvRecordedSteps;
    private UserViewModel model;
    private static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_step_counter);

        // shows current step count
        mTvCurrentSteps = (TextView) findViewById(R.id.tv_yellow_circle_steps);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        mTvRecordedSteps = (TextView) findViewById(R.id.tv_NumberSteps);

        model = new ViewModelProvider(this).get(UserViewModel.class);
        user = model.getUsers().getValue().get(model.getUser().getValue());
        user.stepcount++;
        model.update(user);

        // displays previously recorded step count
        mTvRecordedSteps.setText(user.stepcount);
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
    protected void onResume() {
        super.onResume();
        if(mStepCounter!=null){
            mSensorManager.registerListener(mListener,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mStepCounter!=null){
            mSensorManager.unregisterListener(mListener);
        }
    }

    public void backButton(View view) {
        switch(view.getId()) {
            case R.id.btn_back:
                Navigation.findNavController(StepCounterActivity.this, R.id.stepCounterActivity).navigate(R.id.action_stepCounterActivity_to_homeFragment);
                break;
        }
    }
}
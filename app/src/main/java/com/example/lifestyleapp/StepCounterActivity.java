package com.example.lifestyleapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

public class StepCounterActivity extends AppCompatActivity {

    // much of the step counting code was copied from example41
    private SensorManager mSensorManager;
    private TextView mTvData;
    private Sensor mStepCounter;

    // private TextView mPriorStepCount;  // **TODO - implement recorded steps
    //  **TODO - implement start/stop step counter with gestures

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_step_counter);

        mTvData = (TextView) findViewById(R.id.tv_yellow_circle_steps);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    public void backButton (View view)
    {
        switch(view.getId()) {
            case R.id.btn_back:
                Navigation.findNavController(StepCounterActivity.this, R.id.stepCounterActivity).navigate(R.id.action_stepCounterActivity_to_homeFragment);
                break;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            mTvData.setText("" + String.valueOf(sensorEvent.values[0]));
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
}
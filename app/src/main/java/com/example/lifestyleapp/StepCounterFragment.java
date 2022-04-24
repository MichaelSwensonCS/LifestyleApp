package com.example.lifestyleapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

// imports copied from example41
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class StepCounterFragment extends AppCompatActivity {

    // much of the following code was copied from example41
    private SensorManager mSensorManager;
    private TextView mTvData;
    private Sensor mStepCounter;

    // my added buttons
    private Button mStartButton;
    private Button mStopButton;
    //private TextView mPriorStepCount;  // **TODO - implement this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_step_counter);

        mTvData = (TextView) findViewById(R.id.tv_yellow_circle_steps);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        mStartButton = (Button) findViewById(R.id.btn_Start);
        mStopButton = (Button) findViewById(R.id.btn_Stop);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // **TODO - start step counter
                mStartButton.setEnabled(false);
                mStopButton.setEnabled(true);
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // **TODO - stop step counter
                mStopButton.setEnabled(false);
                mStartButton.setEnabled(true);
                // **TODO - record and display prior step count amount
            }
        });
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
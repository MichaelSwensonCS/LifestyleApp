package com.example.lifestyleapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifestyleapp.databinding.FragmentStepCounter2Binding;


public class StepCounterFragment extends Fragment {

    private static FragmentStepCounter2Binding binding;

    // TODO: Rename and change types of parameters
    private SensorManager mSensorManager;
    private TextView mTvCurrentSteps;
    private Sensor mStepCounter;

    private TextView mTvRecordedSteps;
    private UserViewModel model;
    private static User user;
    private GestureDetectorCompat mDetector;

    public StepCounterFragment() {
        // Required empty public constructor
    }

    private SensorEventListener mListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            mTvCurrentSteps.setText("" + String.valueOf(sensorEvent.values[0]));
            incrementSteps();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStepCounter2Binding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.fragment_step_counter2, null, false);

        mTvRecordedSteps = binding.tvNumberSteps;
        model = new ViewModelProvider(this).get(UserViewModel.class);

        mTvCurrentSteps = binding.tvYellowCircleSteps;
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getContext(), "down", duration);

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {
                    final String DEBUG_APP = "Gestures";
                    @Override
                    public boolean onDown(MotionEvent e) {
                        Log.d(DEBUG_APP, "On Down");
                        toast.show();
                        return true;
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        toast.show();
                        return super.onSingleTapUp(e);
                    }

                    //The gesture that will start our step counter
                    @Override
                    public void onLongPress(MotionEvent event) {
                        Log.d(DEBUG_APP, "On Long");
                        toast.show();

                        if(mStepCounter!=null) {
                            mSensorManager.registerListener(mListener, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
                        }
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        toast.show();

                        return super.onScroll(e1, e2, distanceX, distanceY);
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        toast.show();

                        return super.onFling(e1, e2, velocityX, velocityY);
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {
                        toast.show();

                        super.onShowPress(e);
                    }

                    //The gesture that will stop our step counter
                    @Override
                    public boolean onDoubleTap(MotionEvent event) {
                        toast.show();
                        Log.d(DEBUG_APP, "On Double Tap");
                        if(mStepCounter!=null){
                            mSensorManager.unregisterListener(mListener);
                        }
                        return true;
                    }

                    @Override
                    public boolean onDoubleTapEvent(MotionEvent e) {
                        toast.show();

                        return super.onDoubleTapEvent(e);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        toast.show();

                        return super.onSingleTapConfirmed(e);
                    }

                    @Override
                    public boolean onContextClick(MotionEvent e) {
                        toast.show();

                        return super.onContextClick(e);
                    }
                });

        //https://stackoverflow.com/questions/11421368/android-fragment-oncreateview-with-gestures/11421565#11421565
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toast.show();

                // return gesture.onTouchEvent(event);

                gesture.onTouchEvent(event);
                return true; // <-- this line made the difference
            }
        });

        return binding.getRoot();
    }

    private void incrementSteps() {
        if (model.getUsers().getValue() != null) {
            user = model.getUsers().getValue().get(model.getUser().getValue());
            user.stepcount++;
            model.update(user);
            mTvRecordedSteps.setText(user.stepcount);
        }
    }


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
package com.example.axa180116_asg5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;


/*
  Written by Aashritha Ananthula for CS6326.001, assignment 4, starting April 22, 2023.
  NetID: axa180116
  Description: This activity contains the actual game play for the user. The balloons are drawn, the
  top part of the screen contains metrics such as current score and timer. This activity also makes
  use of the temperature sensor to detect changes in temperature. If temp is greater than 33 degrees C,
  the balloons are changed to a darker color to indicate being burned.
 */
public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private MyView custView;

    private TextView mTextViewTimer;

    private TextView mTextViewColor;
    private TextView mTextViewScore;
    private TextView mTextViewShape;

    private SensorManager sensorManager;

    private SensorEvent sevent;
    private Sensor tempSensor;
    private boolean isTempSensorAvailable = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        custView = findViewById(R.id.custView);
        mTextViewColor = findViewById(R.id.editInstructionColor);
        mTextViewShape = findViewById(R.id.editInstructionShape);
        mTextViewScore = findViewById(R.id.scoreNum);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE) ;
        //custView.getLayoutParams().height = getWindowManager().getDefaultDisplay().getHeight()/3;
        //custView.getLayoutParams().width = getWindowManager().getDefaultDisplay().getWidth();

        // sensor type setup to temperature sensor
        // if sensor is not supported on the phone, don't try to detect it
        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTempSensorAvailable = true;
        }
        else{
            System.out.println("Temperature sensor is not available on your device!");
            isTempSensorAvailable = false;
        }

        // get random color and shape from the main screen
        Bundle extras = getIntent().getExtras();
        int[] colorAndShape = new int[2];
        if(extras != null){
            int[] temp = extras.getIntArray("key");
            colorAndShape[0] = temp[0];
            colorAndShape[1] = temp[1];
            mTextViewShape.setTextColor(colorAndShape[0]);
            mTextViewColor.setTextColor(colorAndShape[0]);

            // change text depending on color
            switch (colorAndShape[0]){
                case -65536:
                    mTextViewColor.setText("RED");
                    break;
                case -16384:
                    mTextViewColor.setText("ORANGE");
                    break;
                case -1024:
                    mTextViewColor.setText("YELLOW");
                    break;
                case -10027264:
                    mTextViewColor.setText("GREEN");
                    break;
                case -16711681:
                    mTextViewColor.setText("BLUE");
                    break;
                case -65281:
                    mTextViewColor.setText("PURPLE");
                    break;
                case -1:
                    mTextViewColor.setText("WHITE");
                    break;
            }

            // change text depending on shape
            if(colorAndShape[1] == 0){
                mTextViewShape.setText(" CIRCLES");
            }
            else{
                mTextViewShape.setText(" SQUARES");
            }

        }
        // timer
        mTextViewTimer = findViewById(R.id.timer);
        File path = getApplicationContext().getFilesDir();

        // initialize some UI related values that are needed in my custom view
        custView.initVals(mTextViewTimer, mTextViewScore, colorAndShape, getSupportFragmentManager(),
                path);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        sevent = sensorEvent;
        // if temp is "HOT", change color of the balloons to hot balloons
        if(sensorEvent.values[0] > 33){

            custView.hotBalloons();
        }
        // if temp is back to below 33 "NORMAL", reset the colors to original colors
        else {
            custView.resetBalloons();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTempSensorAvailable){
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isTempSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}
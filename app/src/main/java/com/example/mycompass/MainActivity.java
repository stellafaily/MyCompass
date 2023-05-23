package com.example.mycompass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

class CompassView extends View {

    float azimuth = 0;
    float pitch = 0;
    float roll = 0;

    public CompassView(Context context) {
        super(context);
    }
}
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    CompassView compass;
    SensorManager sm;
    Sensor orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        compass = new CompassView(this);
        setContentView(compass);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        orientation = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,orientation,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
package com.example.mycompass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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


    public void setAZimth(float azimuth) {
        this.azimuth = azimuth;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;

    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
    public CompassView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.save();

        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("방향센서값: ", 100, 600, paint);
        canvas.drawText("방위각: " + azimuth, 100 ,675, paint);
        canvas.drawText("피치: " + pitch, 100, 750, paint);
        canvas.drawText("롤:" + roll , 100, 825, paint);

        paint.setColor(Color.YELLOW);
        canvas.rotate(-azimuth, 250, 250);
        canvas.drawCircle(250, 250, 200, paint);

        paint.setColor(Color.BLACK); //나침반 바늘색상 설정
        canvas.drawText("N", 235, 80, paint);
        canvas.drawText("S", 235, 460, paint);
        canvas.drawRect(240, 80, 260, 420, paint);
        canvas.restore();
    }
}
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    CompassView compass;
    private SensorManager sm;
    private Sensor orientation;

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
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            compass.setAZimth(sensorEvent.values[0]);
            compass.setPitch(sensorEvent.values[1]);
            compass.setRoll(sensorEvent.values[2]);
            compass.invalidate();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
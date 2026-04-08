package com.example.q3_sensorsapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
 * MainActivity:
 * Displays data from Accelerometer, Light, and Proximity sensors
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    Sensor accelerometer;
    Sensor lightSensor;
    Sensor proximitySensor;

    TextView accelText, lightText, proximityText;

    int counter = 0;   // 🔥 Counter to show continuous updates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        accelText = findViewById(R.id.accelText);
        lightText = findViewById(R.id.lightText);
        proximityText = findViewById(R.id.proximityText);

        // Get Sensor Manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Initialize sensors
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register listeners
        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (lightSensor != null)
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        if (proximitySensor != null)
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister listeners to save battery
        sensorManager.unregisterListener(this);
    }

    /*
     * Called when sensor values change
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        counter++;  // 🔥 increments every time sensor updates
        long time = System.currentTimeMillis();

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelText.setText("Accelerometer:\nX: " + x +
                    "\nY: " + y +
                    "\nZ: " + z +
                    "\nUpdates: " + counter +
                    "\nTime: " + time);
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light = event.values[0];

            lightText.setText("Light Sensor:\n" + light +
                    " lx\nUpdates: " + counter);
        }

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];

            proximityText.setText("Proximity:\n" + distance +
                    "\nUpdates: " + counter);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}
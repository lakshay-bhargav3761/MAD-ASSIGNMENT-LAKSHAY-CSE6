package com.example.q3_sensorsapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
 * MainActivity:
 * This activity reads data from Accelerometer, Light, and Proximity sensors
 * and displays them using TextViews and ProgressBars.
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Sensor manager to access device sensors
    SensorManager sensorManager;

    // Sensor objects
    Sensor accelerometer;
    Sensor lightSensor;
    Sensor proximitySensor;

    // UI elements to display values
    TextView accelText, lightText, proximityText;

    // Progress bars to visualize sensor data
    ProgressBar accelBar, lightBar, proximityBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI components with XML
        accelText = findViewById(R.id.accelText);
        lightText = findViewById(R.id.lightText);
        proximityText = findViewById(R.id.proximityText);

        accelBar = findViewById(R.id.accelBar);
        lightBar = findViewById(R.id.lightBar);
        proximityBar = findViewById(R.id.proximityBar);

        // Initialize Sensor Manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get default sensors from device
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    /*
     * onResume:
     * Registers sensor listeners when app becomes active
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Register accelerometer listener
        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);

        // Register light sensor listener
        if (lightSensor != null)
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_UI);

        // Register proximity sensor listener
        if (proximitySensor != null)
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
    }

    /*
     * onPause:
     * Unregisters listeners to save battery when app is not visible
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /*
     * onSensorChanged:
     * Called whenever sensor data changes
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        // Accelerometer data (X, Y, Z axes)
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calculate magnitude of acceleration vector
            float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

            // Display values
            accelText.setText(String.format("X: %.2f\nY: %.2f\nZ: %.2f", x, y, z));

            // Update progress bar
            accelBar.setProgress((int) magnitude);
        }

        // Light sensor data (in lux)
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            float light = event.values[0];

            // Display light intensity
            lightText.setText(light + " lx");

            // Limit value to progress bar max (1000)
            int value = (int) light;
            if (value > 1000) value = 1000;

            lightBar.setProgress(value);
        }

        // Proximity sensor data (distance in cm)
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            float distance = event.values[0];

            // Check if object is near or far
            if (distance < proximitySensor.getMaximumRange()) {
                proximityText.setText("Near");
            } else {
                proximityText.setText("Far");
            }

            // Update progress bar
            proximityBar.setProgress((int) distance);
        }
    }

    /*
     * onAccuracyChanged:
     * Not used in this application
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
package com.example.q1_currencyconverter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.SwitchCompat; // Changed to SwitchCompat for better compatibility
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    // Using SwitchCompat matches what Android Studio generates by default
    SwitchCompat themeSwitch;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // UI binding - Ensure IDs match your XML exactly
        themeSwitch = findViewById(R.id.themeSwitch);
        backBtn = findViewById(R.id.backBtn);

        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);

        themeSwitch.setChecked(isDarkMode);

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("darkMode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            // recreate() is necessary to refresh the UI with new colors
            recreate();
        });

        backBtn.setOnClickListener(v -> {
            finish(); // Returns to MainActivity
        });
    }
}
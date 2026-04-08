package com.example.q1_currencyconverter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat themeSwitch;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 🔥 APPLY THEME BEFORE super.onCreate()
        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // UI binding
        themeSwitch = findViewById(R.id.themeSwitch);
        backBtn = findViewById(R.id.backBtn);

        // Set switch state based on saved preference
        themeSwitch.setChecked(isDarkMode);

        // Toggle theme
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            // Save preference
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("darkMode", isChecked);
            editor.apply();

            // Apply theme change
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Refresh activity to apply theme
            recreate();
        });

        // Back button → go to MainActivity
        backBtn.setOnClickListener(v -> finish());
    }
}
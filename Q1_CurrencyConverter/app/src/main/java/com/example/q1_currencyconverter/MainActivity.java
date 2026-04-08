package com.example.q1_currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/*
 * MainActivity:
 * Handles currency conversion using updated rates + persistent theme loading
 */
public class MainActivity extends AppCompatActivity {

    EditText amountInput;
    Spinner fromCurrency, toCurrency;
    Button convertBtn, settingsBtn;
    TextView resultText;

    String[] currencies = {"INR", "USD", "EUR", "JPY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 1. APPLY THEME BEFORE super.onCreate()
        // This prevents the screen from "flickering" between light/dark on startup
        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. UI binding
        amountInput = findViewById(R.id.amountInput);
        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        convertBtn = findViewById(R.id.convertBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        resultText = findViewById(R.id.resultText);

        // 3. Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                currencies
        );

// Set dropdown layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);

        // 4. Convert button listener
        convertBtn.setOnClickListener(v -> convertCurrency());

        // 5. Settings button listener (Opens the theme toggle page)
        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void convertCurrency() {
        String input = amountInput.getText().toString();

        if (input.isEmpty()) {
            resultText.setText("Please enter an amount");
            return;
        }

        double amount = Double.parseDouble(input);

        String from = fromCurrency.getSelectedItem().toString();
        String to = toCurrency.getSelectedItem().toString();

        // Conversion logic: Convert input to a base (INR), then convert that base to target
        double amountInINR = convertToINR(amount, from);
        double finalAmount = convertFromINR(amountInINR, to);

        // Using %.4f because USD and EUR rates are small decimals
        resultText.setText(String.format("Converted Amount: %.4f %s", finalAmount, to));
    }

    /**
     * Converts any currency TO Indian Rupee (INR)
     * Based on: $1 = 0.0107 INR, €1 = 0.0093 INR, ¥1 = 1.7 INR
     */
    private double convertToINR(double amount, String currency) {
        switch (currency) {
            case "USD":
                return amount / 0.0107; // If 1 INR = 0.0107 USD, then USD / 0.0107 = INR
            case "EUR":
                return amount / 0.0093;
            case "JPY":
                return amount / 1.7;
            default:
                return amount; // It's already INR
        }
    }

    /**
     * Converts Indian Rupee (INR) TO the target currency
     */
    private double convertFromINR(double amount, String currency) {
        switch (currency) {
            case "USD":
                return amount * 0.0107;
            case "EUR":
                return amount * 0.0093;
            case "JPY":
                return amount * 1.7;
            default:
                return amount; // It's already INR
        }
    }
}
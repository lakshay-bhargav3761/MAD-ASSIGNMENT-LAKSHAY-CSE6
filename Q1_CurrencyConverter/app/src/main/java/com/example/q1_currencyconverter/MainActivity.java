package com.example.q1_currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/*
 * MainActivity:
 * Handles currency conversion + theme safely
 */
public class MainActivity extends AppCompatActivity {

    EditText amountInput;
    Spinner fromCurrency, toCurrency;
    Button convertBtn, settingsBtn;
    TextView resultText;

    String[] currencies = {"INR", "USD", "EUR", "JPY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ✅ APPLY THEME BEFORE super.onCreate()
        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI binding
        amountInput = findViewById(R.id.amountInput);
        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        convertBtn = findViewById(R.id.convertBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        resultText = findViewById(R.id.resultText);

        // Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                currencies
        );

        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);

        // Convert button
        convertBtn.setOnClickListener(v -> convertCurrency());

        // Settings button
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

        double amountInINR = convertToINR(amount, from);
        double finalAmount = convertFromINR(amountInINR, to);

        resultText.setText(String.format("Converted Amount: %.2f %s", finalAmount, to));
    }

    private double convertToINR(double amount, String currency) {
        switch (currency) {
            case "USD": return amount * 83;
            case "EUR": return amount * 90;
            case "JPY": return amount * 0.55;
            default: return amount;
        }
    }

    private double convertFromINR(double amount, String currency) {
        switch (currency) {
            case "USD": return amount / 83;
            case "EUR": return amount / 90;
            case "JPY": return amount / 0.55;
            default: return amount;
        }
    }
}
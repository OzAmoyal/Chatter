package com.example.ass4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "Light"); // Default theme is 'Light'
        if (theme.equals("Dark")) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }
        setContentView(R.layout.activity_settings);
        // Inside onCreate method of other activities
        EditText etServerAddress = findViewById(R.id.etServerAddress);
        etServerAddress.setText("http://");
        Spinner spinnerTheme = findViewById(R.id.spinnerTheme);
        spinnerTheme.setSelection(0);
        Button btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            String serverAddress = etServerAddress.getText().toString();
            String newTheme = spinnerTheme.getSelectedItem().toString();
            if (serverAddress.isEmpty()) {
                etServerAddress.setError("Please enter a server address");
                return;
            }
            if(newTheme.equals("Dark")){
                SharedPreferences newSharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("theme", newTheme); // 'theme' is the selected theme ('Light' or 'Dark')
                editor.apply();
            }
            else{
                SharedPreferences newSharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("theme", newTheme); // 'theme' is the selected theme ('Light' or 'Dark')
                editor.apply();
            }
            Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
            startActivity(intent);
            // Save the settings
            // ..
        });


    }
}

package com.example.ass4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.BoringLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ass4.api.LoginAPI;
import com.example.ass4.api.UserAPI;

import java.util.concurrent.CountDownLatch;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "Light"); // Default theme is 'Light'
        if (theme.equals("Dark")) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }
        setContentView(R.layout.activity_login);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            boolean isValid = true;
            if (username.isEmpty()) {
                etUsername.setError("Please enter a username");
                etUsername.requestFocus();
                isValid = false;
            }
            if (password.isEmpty()) {
                etPassword.setError("Please enter a password");
                etPassword.requestFocus();
                isValid = false;
            }
            if (isValid) {
                LoginAPI api = new LoginAPI();
                Boolean loginStatus = api.getToken(username, password);

                if (loginStatus) {
                    CountDownLatch tokenLatch = new CountDownLatch(1);
                    CountDownLatch userLatch = new CountDownLatch(1);

                    runOnUiThread(() -> {
                        UserAPI api2 = new UserAPI();
                        api2.getUserDetails(username);
                        userLatch.countDown();
                    });

                    new Thread(() -> {
                        try {
                            tokenLatch.await(); // Wait until the token is set
                            userLatch.await(); // Wait until the user details are set
                            Intent intent = new Intent(Login.this, ContactsActivity.class);
                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    runOnUiThread(() -> {
                        // Wait until the token is set
                        while (!MyApplication.isTokenSet()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        tokenLatch.countDown();
                    });
                } else {
                    etUsername.setError("Invalid username or password");
                    etPassword.setText("");
                    etPassword.setError("Invalid username or password");
                    etPassword.requestFocus();
                }
            }
        });
    }
}
package com.example.ass4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.BoringLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ass4.api.LoginAPI;
import com.example.ass4.api.UserAPI;

import java.util.concurrent.CompletableFuture;
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
                GetTokenTask getTokenTask = new GetTokenTask(username, password);
                getTokenTask.execute();
            } else {
                etUsername.setError("Invalid username or password");
                etPassword.setText("");
                etPassword.setError("Invalid username or password");
                etPassword.requestFocus();
            }

        });
    }
    private class GetTokenTask extends AsyncTask<Void, Void, Boolean> {
        private String username;
        private String password;

        public GetTokenTask(String username, String password) {
            this.username = username;
            this.password = password;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            LoginAPI loginAPI = new LoginAPI();
            Boolean isTokenSet = loginAPI.getToken(username, password);
            return isTokenSet;
        }

        @Override
        protected void onPostExecute(Boolean isTokenSet) {
            if (isTokenSet) {
                GetUserTask getUserTask = new GetUserTask(username);
                getUserTask.execute();
            } else {
                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class GetUserTask extends AsyncTask<Void, Void, Boolean>{
    private String username;
    public GetUserTask(String username) {
        this.username = username;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        UserAPI userAPI = new UserAPI();
        Boolean isUserSet = userAPI.getUserDetails(username);
        return isUserSet;
    }


        @Override
    protected void onPostExecute(Boolean isUserSet) {
        if (isUserSet) {
            Intent intent = new Intent(Login.this, ContactsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

        }
    }
}
}
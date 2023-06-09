package com.example.ass4;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class SignUp extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_PERMISSION = 2;

    private ImageView profilePic;
    private EditText etUsername, etDisplayName, etPassword, etConfirmPassword;

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
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        profilePic = findViewById(R.id.profilepic);
        etUsername = findViewById(R.id.etUsername);
        etDisplayName = findViewById(R.id.etDisplayName);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        // Set click listener on the upload picture button
        Button btnUploadPicture = findViewById(R.id.btnUploadPicture);
        btnUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndPickImage();
            }
        });

        // Set click listener on the sign-up button
        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform validation checks
                if (validateFields()) {
                    // Continue with sign-up process
                    // ...
                }
            }
        });
        }

private boolean validateFields() {
    String username = etUsername.getText().toString().trim();
    String displayName = etDisplayName.getText().toString().trim();
    String password = etPassword.getText().toString();
    String confirmPassword = etConfirmPassword.getText().toString();

    boolean isValid = true;

    if (username.isEmpty()) {
        etUsername.setError("Please enter a username");
        etUsername.requestFocus();
        isValid = false;
    }

    if (displayName.isEmpty()) {
        etDisplayName.setError("Please enter a display name");
        etDisplayName.requestFocus();
        isValid = false;
    }

    if (password.isEmpty()) {
        etPassword.setError("Please enter a password");
        etPassword.requestFocus();
        isValid = false;
    } else if (password.length() < 8) {
        etPassword.setError("Password should be at least 8 characters long");
        etPassword.requestFocus();
        isValid = false;
    } else if (!password.matches(".*\\d.*")) {
        etPassword.setError("Password should contain at least one number");
        etPassword.requestFocus();
        isValid = false;
    } else if (!password.matches(".*[a-zA-Z].*")) {
        etPassword.setError("Password should contain at least one character");
        etPassword.requestFocus();
        isValid = false;
    }

    if (confirmPassword.isEmpty()) {
        etConfirmPassword.setError("Please confirm your password");
        etConfirmPassword.requestFocus();
        isValid = false;
    } else if (!password.equals(confirmPassword)) {
        etConfirmPassword.setError("Passwords do not match");
        etConfirmPassword.requestFocus();
        isValid = false;
    }

    return isValid;
}



    private void checkPermissionAndPickImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
        } else {
            // Permission is already granted, start image picker
            startImagePicker();
        }
    }

    private void startImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start image picker
                startImagePicker();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                // Load the selected image into the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("SignUp", "Failed to load image");
            }
        }
    }
}

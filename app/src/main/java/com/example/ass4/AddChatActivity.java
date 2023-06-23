package com.example.ass4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ass4.api.ChatsAPI;
import com.example.ass4.api.CreateChatCallback;

public class AddChatActivity extends AppCompatActivity {

    private EditText etUserName;
    private Button btnAddChat;

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
        setContentView(R.layout.activity_add_chat);

        etUserName = findViewById(R.id.etUserName);
        btnAddChat = findViewById(R.id.btnAddChat);

        btnAddChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString().trim();
                if (!userName.isEmpty()) {
                    // Add chat logic here
                    //Toast.makeText(AddChatActivity.this, "Chat added with user: " + userName, Toast.LENGTH_SHORT).show();
                    //define onResume.
                    ChatsAPI chatsAPI = new ChatsAPI();chatsAPI.createNewChat(userName, new CreateChatCallback() {
                        @Override
                        public void onSuccess(String chatId) {
                            // Handle successful response
                            Toast.makeText(AddChatActivity.this, "Chat added with user: " + userName, Toast.LENGTH_SHORT).show();
                            etUserName.setText(""); // Clear the input field
                            finish(); // Close the activity
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            // Handle failure
                            Toast.makeText(AddChatActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                    /*
                    if(chatID.equals("User not found")){
                        Toast.makeText(AddChatActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        Toast.makeText(AddChatActivity.this, "Chat added with user: " + userName, Toast.LENGTH_SHORT).show();
                        etUserName.setText(""); // Clear the input field
                        finish(); // Close the activity
                    }*/
                } else {
                    Toast.makeText(AddChatActivity.this, "Please enter a user name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

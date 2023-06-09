package com.example.ass4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    ImageView profilePictureView;
    TextView userNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inside onCreate method of other activities
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "Light"); // Default theme is 'Light'
        if (theme.equals("Dark")) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }
        setContentView(R.layout.activity_chat);

        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);

        Intent activityIntent = getIntent();
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello", "12:00", true));
        messages.add(new Message("Hi", "12:01", false));
        messages.add(new Message("How are you?", "12:02", true));
        messages.add(new Message("I'm fine", "12:03", false));
        messages.add(new Message("What about you?", "12:04", false));
        messages.add(new Message("I'm fine too", "12:05", true));
        messages.add(new Message("Good to hear that", "12:06", false));
        messages.add(new Message("Bye", "12:07", true));
        messages.add(new Message("Bye!!!", "12:08", false));
        ListView lvMessages = findViewById(R.id.lvMessages);
        MessageAdapter messageAdapter = new MessageAdapter(this, messages);
        lvMessages.setAdapter(messageAdapter);
        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.blue);

            profilePictureView.setImageResource(profilePicture);
            userNameView.setText(userName);
        }
    }
}
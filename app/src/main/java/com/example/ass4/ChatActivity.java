package com.example.ass4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ass4.adapters.MessageAdapter;
import com.example.ass4.entities.Message;

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

        ListView lvMessages = findViewById(R.id.lvMessages);
        MessageAdapter messageAdapter = new MessageAdapter(this, messages);
        lvMessages.setAdapter(messageAdapter);
        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            byte[] profilePictureByteArray = getIntent().getByteArrayExtra("profilePicture");
            Bitmap profilePictureBitmap = BitmapFactory.decodeByteArray(profilePictureByteArray, 0, profilePictureByteArray.length);

            profilePictureView.setImageBitmap(profilePictureBitmap);
            userNameView.setText(userName);
        }
    }
}
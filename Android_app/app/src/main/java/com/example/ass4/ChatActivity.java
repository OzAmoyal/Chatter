package com.example.ass4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.ass4.adapters.MessageAdapter;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.Message;
import com.example.ass4.viewModels.ChatViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    ImageView profilePictureView;
    TextView userNameView;
    ChatViewModel chatViewModel;

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
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        String chatId = getIntent().getStringExtra("chatId");
        LiveData<Chat> chatLiveData = chatViewModel.getChatByID(chatId);
        ListView lvMessages = findViewById(R.id.lvMessages);

        chatLiveData.observe(this, chat -> {
            if (chat != null) {
               List<Message> messageList= chat.getMessages();
                MessageAdapter messageAdapter = new MessageAdapter(this, messageList);
                lvMessages.setAdapter(messageAdapter);

            }
        });

        Intent activityIntent = getIntent();
        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            byte[] profilePictureByteArray = getIntent().getByteArrayExtra("profilePicture");
            Bitmap profilePictureBitmap = BitmapFactory.decodeByteArray(profilePictureByteArray, 0, profilePictureByteArray.length);
            Glide.with(this)
                    .load(profilePictureBitmap)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(profilePictureView);
            userNameView.setText(userName);
        }
        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> {
            String message = ((TextView) findViewById(R.id.etMessage)).getText().toString();
            if(message.isEmpty())
                return;
            chatViewModel.sendMessage(chatId, message);
            ((TextView) findViewById(R.id.etMessage)).setText("");
        });

    }
}
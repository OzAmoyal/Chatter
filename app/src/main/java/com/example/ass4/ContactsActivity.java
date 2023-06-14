package com.example.ass4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.example.ass4.adapters.ChatsAdapter;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.Message;
import com.example.ass4.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_contacts);


        ImageButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);

                startActivity(intent);
            }
        });
        ImageButton btnNewChat= findViewById(R.id.btnNewChat);
        btnNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddChatActivity.class);

                startActivity(intent);
            }
        });


        RecyclerView lstChats = findViewById(R.id.recycler_view);
        final ChatsAdapter adapter  = new ChatsAdapter(this);
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        List<Chat> chats = new ArrayList<>();

        Message message1 = new Message("1", "Hello!", new Date(), true);
        Message message2 = new Message("2", "How are you?", new Date(), false);
        Date currentTime = new Date();
        Message message3 = new Message("3", "Good morning!", currentTime, true);
        Message message4 = new Message("4", "I'm fine, thank you!", new Date(), false);
        Message message5 = new Message("5", "Have a great day!", new Date(), true);

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);
        messages.add(message5);

        User otherUser = new User("john_doe", R.drawable.modric, "John Doe");
        User otherUser2 = new User("user2", R.drawable.neymar, "neymar");
        Message lastMessage = message5;
        String id1= "1";
        String id2= "2";

// Create eight instances of Chat with the same messages
        Chat chat1 = new Chat(id1, messages, otherUser, lastMessage);
        Chat chat2 = new Chat(id2, messages, otherUser2, lastMessage);


// Add the eight instances to the chats list
        chats.add(chat1);
        chats.add(chat2);


        adapter.setChats(chats);

/*
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < profilePictures.length; i++) {
            User aUser = new User(
                    userNames[i], profilePictures[i],
                    lastMassages[i], times[i]
            );

            users.add(aUser);
        }


        adapter = new ChatsAdapter(getApplicationContext(), users);

        recyclerView.setAdapter(adapter);
        recyclerView.setClickable(true);

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                intent.putExtra("userName", userNames[i]);
                intent.putExtra("profilePicture", profilePictures[i]);
                intent.putExtra("lastMassage", lastMassages[i]);
                intent.putExtra("time", times[i]);

                startActivity(intent);
            }
        });
        */

    }
}
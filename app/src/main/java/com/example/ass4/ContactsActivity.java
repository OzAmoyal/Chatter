package com.example.ass4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    final private int[] profilePictures = {
            R.drawable.kroos, R.drawable.neuer, R.drawable.ramos,
            R.drawable.modric, R.drawable.muller,
            R.drawable.mbappe, R.drawable.neymar, R.drawable.eran2
    };

    final private String[] userNames = {
            "Toni Kroos", "Manuel Neuer", "Sergio Ramos", "Luka Modrić ", "Thomas Müller",
            "Kylian Mbappe", "Neymar Jr", "Eran Levy",
    };

    final private String[] lastMassages = {
            "Hi, how are you?", "24K Magic", "Missing Madrid :(", "Wanna hear a joke?", "Yo!",
            "Well....", "Did you see the latest John Wick?",
            "I'm the best!"
    };

    final private String[] times = {
            "12:00", "00:30", "03:23", "08:59", "12:23", "22:54", "11:47", "10:04",
    };

    ListView listView;
    UserAdapter adapter;

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
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < profilePictures.length; i++) {
            User aUser = new User(
                    userNames[i], profilePictures[i],
                    lastMassages[i], times[i]
            );

            users.add(aUser);
        }

        listView = findViewById(R.id.list_view);
        adapter = new UserAdapter(getApplicationContext(), users);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    }
}
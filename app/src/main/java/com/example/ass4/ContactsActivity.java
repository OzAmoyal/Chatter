package com.example.ass4;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ass4.adapters.ChatsAdapter;
import com.example.ass4.api.ChatsAPI;
import com.example.ass4.api.ResponseGetChatsAPI;
import com.example.ass4.databinding.ActivityContactsBinding;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.Message;
import com.example.ass4.entities.User;
import com.example.ass4.repositories.ChatsRepository;
import com.example.ass4.viewModels.ChatViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private List<Chat> dbChats;
    List<Chat> chats;
    private ActivityContactsBinding binding;
    private ChatDB db;
    ChatDao dao;
    ChatsAdapter adapter;
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
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chats= new ArrayList<>();
        db = Room.databaseBuilder(getApplicationContext(), ChatDB.class, "ChatDB")
                .allowMainThreadQueries().build();
        dao = db.chatDao();
        chatViewModel = new ChatViewModel(dao);
        adapter = new ChatsAdapter(this);
        // addChatsToLocalDB();
        //loadChats();
        RecyclerView lstChats = binding.rvUserList;
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        try {
            chatViewModel.getChats().observe(this, chats -> {
                adapter.setChats(chats);
                adapter.notifyDataSetChanged();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //adapter.setChats(chatViewModel.getChats().getValue());

        //adapter.notifyDataSetChanged();

//        ChatsAPI chatAPI = new ChatsAPI();
//        chatAPI.getChatByID("6478b5e136fa475773babe29");


        ImageButton btnSettings = binding.btnSettings;
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);

                startActivity(intent);
            }
        });
        ImageButton btnNewChat = binding.btnNewChat;
        btnNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddChatActivity.class);

                startActivity(intent);
            }
        });


    }

    private void loadChats() {
        List<Chat> temp= chatViewModel.getChats().getValue();
        int x = temp.size();
        chats.addAll(temp);
        adapter.notifyDataSetChanged();

    }
    private void addChatsToLocalDB(){
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

        User otherUser = new User("john_doe", "R.drawable.modric", "John Doe");
        User otherUser2 = new User("user2", "R.drawable.neymar", "neymar");
        Message lastMessage = message5;
        String id1 = "1";
        String id2 = "2";
// Create eight instances of Chat with the same messages
        Chat chat1 = new Chat(id1, messages, otherUser, lastMessage);
        Chat chat2 = new Chat(id2, messages, otherUser2, lastMessage);


// Add the eight instances to the chats list
        dao.insert(chat1);
        dao.insert(chat2);
    }
}
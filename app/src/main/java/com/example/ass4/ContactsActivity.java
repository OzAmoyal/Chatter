package com.example.ass4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.ass4.adapters.ChatsAdapter;
import com.example.ass4.api.LoginAPI;
import com.example.ass4.api.UserAPI;
import com.example.ass4.databinding.ActivityContactsBinding;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.User;
import com.example.ass4.viewModels.ChatViewModel;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
public class ContactsActivity extends AppCompatActivity {
    List<Chat> chats;
    private ActivityContactsBinding binding;
    ChatsAdapter adapter;
    ChatViewModel chatViewModel;

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
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chats= new ArrayList<>();
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        adapter = new ChatsAdapter(this);

        RecyclerView lstChats = binding.rvUserList;
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        try {
            LiveData<List<Chat>> chats = chatViewModel.getChats();
            chats.observe(this, chatsList -> {
                adapter.setChats(chatsList);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView ivProfilePictures = binding.ivProfilePictures;
        User user = MyApplication.getUser();
        if(user == null) {
        }else {
        }
        Bitmap picture = user.getPicture();
        Glide.with(this).load(picture).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).into(ivProfilePictures);
        //ivProfilePictures.setImageBitmap(picture);
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

        ImageButton btnLogout = binding.btnLogout;
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAPI loginAPI = new LoginAPI();
                loginAPI.removeFirebaseToken(MyApplication.getToken());

                MyApplication.setUser(null);
                MyApplication.setToken(null);
                chatViewModel.logout();
               finish();
            }
        });
        binding.refreshLayout.setOnRefreshListener(() -> {
                chatViewModel.reload();
                binding.refreshLayout.setRefreshing(false);
            });
    }
    protected void onResume() {
        super.onResume();
        chatViewModel.reload();
    }

}
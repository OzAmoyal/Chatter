package com.example.ass4.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ass4.ChatDao;
import com.example.ass4.api.ChatsAPI;
import com.example.ass4.entities.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatsRepository {
    private ChatDao chatDao;
    private ChatsAPI chatsAPI;
    private ChatListData chatListData;

    public class ChatListData extends MutableLiveData<List<Chat>> {
        public ChatListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                postValue(chatDao.index());
            }).start();
        }
    }

    public ChatsRepository(ChatDao chatDao) {
        this.chatDao = chatDao;
        this.chatListData = new ChatListData();
        this.chatsAPI = new ChatsAPI();
    }

    public LiveData<List<Chat>> getAllChats() {
        reload();
        List<Chat>newChats=chatDao.index();
        chatListData.setValue(newChats);
        return chatListData;
    }

    public void reload() {
        try {
            new GetChatsTask().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class GetChatsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... x) {
            List<Chat> chats = chatsAPI.getChats();

            for (Chat chat : chats) {
                if (chatDao.get(chat.getId()) == null) {
                    chatDao.insert(chat);
                } else {
                    chatDao.update(chat);
                }
            }
            return null;
        }
    }
}

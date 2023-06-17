package com.example.ass4.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.ass4.ChatDB;
import com.example.ass4.ChatDao;
import com.example.ass4.MyApplication;
import com.example.ass4.api.ChatsAPI;
import com.example.ass4.entities.Chat;

import java.util.List;

public class ChatsRepository {
    private ChatDao chatDao;
    private ChatsAPI chatsAPI;
    private MutableLiveData<List<Chat>> chatListData;

    public ChatsRepository() {
        ChatDB db = Room.databaseBuilder(MyApplication.getContext(), ChatDB.class, "ChatDB").allowMainThreadQueries().build();
        chatDao = db.chatDao();
        chatsAPI = new ChatsAPI();
        chatListData = new MutableLiveData<>();
        chatListData.postValue(chatDao.index());

        reload();
    }

    public LiveData<List<Chat>> getAllChats() {
        return chatListData;
    }

    public void reload() {
        new GetChatsTask().execute();
    }

    private class GetChatsTask extends AsyncTask<Void, Void, List<Chat>> {
        @Override
        protected List<Chat> doInBackground(Void... params) {
            List<Chat> chats = chatsAPI.getChats();

            for (Chat chat : chats) {
                if (chatDao.get(chat.getId()) == null) {
                    chatDao.insert(chat);
                } else {
                    chatDao.update(chat);
                }
            }

            return chats;
        }

        @Override
        protected void onPostExecute(List<Chat> result) {
            chatListData.setValue(result);
        }
    }
}

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
import com.example.ass4.entities.Message;

import java.util.List;

public class ChatsRepository {
    private ChatDao chatDao;
    private ChatsAPI chatsAPI;
    private MutableLiveData<List<Chat>> chatListData;
    private MutableLiveData<Chat> chatData;

    public ChatsRepository() {
        ChatDB db = Room.databaseBuilder(MyApplication.getContext(), ChatDB.class, "ChatDB").allowMainThreadQueries().build();
        chatDao = db.chatDao();
        chatsAPI = new ChatsAPI();
        chatListData = new MutableLiveData<>();
        chatData = new MutableLiveData<>();
        chatListData.postValue(chatDao.index());

        reload();
    }

    public LiveData<List<Chat>> getAllChats() {
        return chatListData;
    }

    public void reload() {
        new GetChatsTask().execute();
    }
    public LiveData<Chat> getChatByID(String id){
        new GetChatByIDTask(id).execute();
        return chatData;
    }
    public void sendMessage(String chatId, String message){
        new SendMessageTask(chatId,message).execute();
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
    private class GetChatByIDTask extends AsyncTask<Void, Void, Chat> {
        private String id;
        public GetChatByIDTask(String id){
            this.id=id;
        }
        @Override
        protected Chat doInBackground(Void... params) {
            Chat chat = chatsAPI.getChatByID(id);
            if(chat!=null){
                chatDao.update(chat);
                return chat;
            }
            //return chatDao.get(id);
            return null;

        }

        @Override
        protected void onPostExecute(Chat chat){
            chatData.setValue(chat);
            chatListData.setValue(chatDao.index());
        }
    }
    private class SendMessageTask extends AsyncTask<Void, Void, Chat> {
        private String chatId;
        private String message;
        public SendMessageTask(String chatId,String message){
            this.chatId=chatId;
            this.message=message;
        }
        @Override
        protected Chat doInBackground(Void... params) {
            Message message = chatsAPI.sendMessage(chatId,this.message);
            if(message!=null){
                Chat chat = chatDao.get(chatId);
                chat.getMessages().add(message);
                chatDao.update(chat);
                return chat;
            }
            //return chatDao.get(id);
            return null;

        }

        @Override
        protected void onPostExecute(Chat chat){
            chatListData.setValue(chatDao.index());
            chatData.setValue(chat);
        }
    }


}

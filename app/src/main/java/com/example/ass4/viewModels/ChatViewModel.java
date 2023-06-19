package com.example.ass4.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ass4.ChatDao;
import com.example.ass4.entities.Chat;
import com.example.ass4.repositories.ChatsRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {

    private ChatsRepository chatsRepository;
    private LiveData<List<Chat>> chats;

    public ChatViewModel(){
        this.chatsRepository = new ChatsRepository();
        this.chats = chatsRepository.getAllChats();
    }
    public LiveData<List<Chat>> getChats(){
        this.chats=chatsRepository.getAllChats();
        return this.chats;
    }
  /*  public void reload(){
        chatsRepository.reload();
    }*/
//    public void add(Chat chat){
//        chatsRepository.add(chat);
//    }
//    public void delete(User user){
//        mRepository.delete(user);
//    }



}


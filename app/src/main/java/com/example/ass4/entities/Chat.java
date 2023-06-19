package com.example.ass4.entities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;
    List<Message> messages;
    User otherUser;
     Message lastMessage;

    public Chat(String id, List<Message> messages, User otherUser, Message lastMessage) {
        this.id = id;
        this.messages = messages;
        this.otherUser = otherUser;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public User getOtherUser() {
        return otherUser;
    }

    public Message getLastMessage(){
        return lastMessage;
    }



    void setLastMessage(Message m){
        lastMessage = m;
    }
}

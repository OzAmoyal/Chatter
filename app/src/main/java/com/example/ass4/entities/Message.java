package com.example.ass4.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity
public class Message {
    @PrimaryKey(autoGenerate = false)
    private String messageID;
    private String content;
    private Date created;
    private boolean isMine;


    public Message(String messageID, String message, Date time, boolean isMine) {
        this.messageID = messageID;
        this.content = message;
        this.created = time;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public Date getCreated() {
        return created;
    }

    public boolean isMine() {
        return isMine;
    }
}

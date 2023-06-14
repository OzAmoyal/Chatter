package com.example.ass4.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class User {
    @PrimaryKey(autoGenerate = false)
    private String userName;
    private int pictureId;
    private String displayName;

    public User(String userName, int pictureId, String displayName) {
        this.userName = userName;
        this.pictureId = pictureId;
        this.displayName = displayName;
    }

    public int getPictureId() {
        return pictureId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
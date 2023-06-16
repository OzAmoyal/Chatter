package com.example.ass4.entities;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayInputStream;


@Entity
public class User {
    @PrimaryKey(autoGenerate = false)
    private String userName;
    private Bitmap profilePic;
    private String displayName;

    public User(String userName, String profilePic, String displayName) {
        this.userName = userName;
        String cleanBase64 = profilePic.replaceAll("data:image/[^;]+;base64,", "");
        byte[] imageData = Base64.decode(cleanBase64, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        this.profilePic = BitmapFactory.decodeStream(inputStream);
        this.displayName = displayName;
    }

    public Bitmap getPicture() {
        return profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
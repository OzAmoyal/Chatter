package com.example.ass4.entities;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


@Entity
public class User {
    @PrimaryKey(autoGenerate = false)
    private String userName;
    private byte[] profilePic;
    private String displayName;

    public User(String userName, String profilePic, String displayName) {
        this.userName = userName;
        String cleanBase64 = profilePic.replaceAll("data:image/[^;]+;base64,", "");
        byte[] imageData = Base64.decode(cleanBase64, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        convertToByteArray(bitmap);
        this.displayName = displayName;
    }

    public Bitmap getPicture() {
        if (profilePic != null) {
            return BitmapFactory.decodeByteArray(profilePic, 0, profilePic.length);
        } else {
            return null;
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName(){
        return displayName;
    }
    public void convertToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        profilePic = stream.toByteArray();
    }
}
package com.example.ass4;
import androidx.room.TypeConverter;
import com.example.ass4.entities.User;
import com.google.gson.Gson;

public class UserConverter {

    @TypeConverter
    public static User fromString(String userJson) {
        Gson gson = new Gson();
        return gson.fromJson(userJson, User.class);
    }

    @TypeConverter
    public static String toString(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

}

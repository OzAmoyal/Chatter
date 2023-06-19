package com.example.ass4;
import androidx.room.TypeConverter;
import com.example.ass4.entities.Message;
import com.google.gson.Gson;

public class MessageConverter {

    @TypeConverter
    public static Message fromString(String messageJson) {
        Gson gson = new Gson();
        return gson.fromJson(messageJson, Message.class);
    }

    @TypeConverter
    public static String toString(Message message) {
        Gson gson = new Gson();
        return gson.toJson(message);
    }

}

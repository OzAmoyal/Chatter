package com.example.ass4;
import androidx.room.TypeConverter;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.Message;
import com.example.ass4.entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ChatConverter {

    @TypeConverter
    public static String fromChat(Chat chat) {
        Gson gson = new Gson();
        return gson.toJson(chat);
    }

    @TypeConverter
    public static Chat toChat(String chatJson) {
        Gson gson = new Gson();
        return gson.fromJson(chatJson, Chat.class);
    }

    @TypeConverter
    public static String fromMessage(Message message) {
        Gson gson = new Gson();
        return gson.toJson(message);
    }

    @TypeConverter
    public static Message toMessage(String messageJson) {
        Gson gson = new Gson();
        return gson.fromJson(messageJson, Message.class);
    }

    @TypeConverter
    public static String fromMessageList(List<Message> messages) {
        Gson gson = new Gson();
        return gson.toJson(messages);
    }

    @TypeConverter
    public static List<Message> toMessageList(String messagesJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>() {}.getType();
        return gson.fromJson(messagesJson, type);
    }

    @TypeConverter
    public static String fromUser(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @TypeConverter
    public static User toUser(String userJson) {
        Gson gson = new Gson();
        return gson.fromJson(userJson, User.class);
    }

}

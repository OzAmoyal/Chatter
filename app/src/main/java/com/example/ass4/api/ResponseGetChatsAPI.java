package com.example.ass4.api;
//GET /api/chats
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.SerializedName;

public class ResponseGetChatsAPI {
    @SerializedName("id")
    private String id;

    @SerializedName("user")
    private User user;

    @SerializedName("lastMessage")
    private Message lastMessage;

    public ResponseGetChatsAPI(String id, User user, Message lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public static class User {
        @SerializedName("id")
        private String id;

        @SerializedName("username")
        private String username;

        @SerializedName("displayName")
        private String displayName;

        @SerializedName("profilePic")
        private String profilePic;

        public User(String id, String username, String displayName, String profilePic) {
            this.id = id;
            this.username = username;
            this.displayName = displayName;
            this.profilePic = profilePic;
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getProfilePic() {
            return profilePic;
        }
    }

    public static class Message {
        @SerializedName("id")
        private String id;

        @SerializedName("created")
        private String created;

        @SerializedName("content")
        private String content;

        public Message(String id, String created, String content) {
            this.id = id;
            this.created = created;
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public String getCreated() {
            return created;
        }

        public String getContent() {
            return content;
        }
    }
}

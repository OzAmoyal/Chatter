package com.example.ass4.api;

import com.google.gson.annotations.SerializedName;

public class ResponseSendMessageAPI {
    @SerializedName("id")
    private String id;

    @SerializedName("created")
    private String created;

    @SerializedName("content")
    private String content;
    @SerializedName("sender")
    private User sender;

    public ResponseSendMessageAPI(String id,User sender,String created, String content) {
        this.id = id;
        this.created = created;
        this.sender=sender;
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
    public String getSenderUsername() {
        return sender.getUsername();
    }
    public static class User {


        @SerializedName("username")
        private String username;

        @SerializedName("displayName")
        private String displayName;

        @SerializedName("profilePic")
        private String profilePic;

        public User(String username, String displayName, String profilePic) {
            this.username = username;
            this.displayName = displayName;
            this.profilePic = profilePic;
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
}

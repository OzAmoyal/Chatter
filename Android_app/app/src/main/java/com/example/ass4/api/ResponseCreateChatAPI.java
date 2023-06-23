package com.example.ass4.api;

import com.google.gson.annotations.SerializedName;

public class ResponseCreateChatAPI {
    @SerializedName("id")
    private String id;
    @SerializedName("user")
    private User user;

    public ResponseCreateChatAPI(String id, User user){
        this.id=id;
        this.user=user;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return user.getUsername();
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
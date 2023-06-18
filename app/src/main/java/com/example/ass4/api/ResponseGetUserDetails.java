package com.example.ass4.api;

public class ResponseGetUserDetails {
    private String username;
    private String displayName;
    private String profilePic;

    // Constructor
    public ResponseGetUserDetails(String username, String displayName, String profilePic) {
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

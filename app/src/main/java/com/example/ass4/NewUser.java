package com.example.ass4;

public class NewUser {
    private String username;
    private String profilePic;
    private String displayName;
    private String password;

    public NewUser(String username, String profilePic, String displayName, String password) {
        this.username = username;
        this.profilePic = profilePic;
        this.displayName = displayName;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }
}



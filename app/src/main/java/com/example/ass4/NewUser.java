package com.example.ass4;

public class NewUser {
    private String userName;
    private int pictureId;
    private String displayName;
    private String password;

    public NewUser(String userName, int pictureId, String displayName, String password) {
        this.userName = userName;
        this.pictureId = pictureId;
        this.displayName = displayName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public int getPictureId() {
        return pictureId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }
}



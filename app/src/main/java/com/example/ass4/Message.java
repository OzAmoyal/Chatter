package com.example.ass4;

public class Message {
    private String message;
    private String time;
    private boolean isMine;

    public Message(String message, String time, boolean isMine) {
        this.message = message;
        this.time = time;
        this.isMine = isMine;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public boolean isMine() {
        return isMine;
    }
}

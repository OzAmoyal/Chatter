package com.example.ass4.api;

public class RequestNewChatAPI {
    String username;
    public RequestNewChatAPI(String username){
        this.username=username;
    }
    public String getUsername(){
        return username;
    }
}

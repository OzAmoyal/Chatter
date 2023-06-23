package com.example.ass4.api;
// POST api/tokens
public class RequestGetTokenAPI {
    private String username;
    private String password;

    public RequestGetTokenAPI(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

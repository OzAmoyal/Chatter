package com.example.ass4;

public interface LoginCallback {
    void onLoginSuccess(String username);
    void onLoginFailure();
}
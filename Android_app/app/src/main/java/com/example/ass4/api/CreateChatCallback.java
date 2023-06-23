package com.example.ass4.api;

public interface CreateChatCallback {
    void onSuccess(String chatId);

    void onFailure(String errorMessage);
}
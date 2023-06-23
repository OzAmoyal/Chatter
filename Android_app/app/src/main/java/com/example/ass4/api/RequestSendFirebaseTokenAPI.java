package com.example.ass4.api;

import com.google.gson.annotations.SerializedName;

public class RequestSendFirebaseTokenAPI {
    @SerializedName("firebaseToken")
    String firebaseToken;
    RequestSendFirebaseTokenAPI(String firebaseToken){
        this.firebaseToken = firebaseToken;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }
}

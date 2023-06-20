package com.example.ass4.api;

public class RequestSendMessageAPI {
    String msg;
    public RequestSendMessageAPI(String msg){
        this.msg=msg;
    }
    public String getMsg(){
        return msg;
    }
}

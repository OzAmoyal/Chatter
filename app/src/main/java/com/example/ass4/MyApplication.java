package com.example.ass4;

import android.app.Application;
import android.content.Context;

import com.example.ass4.entities.User;

public class MyApplication extends Application{
    public static Context context;
    public static String token;
    public static User user;
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        token="Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InVzZXIxIiwiaWF0IjoxNjg2OTI2MTMzLCJleHAiOjE2ODY5Mjk3MzN9.Z-cu0JWlT85bMwobwnIITsfp3Q__UX7tCRjR-IoFVO8";
        user = new User("user1","","oz");
    }
    public static Context getContext(){
        return context;
    }
    public static void setToken(String getToken){
        token=getToken;
    }
    public static void setUser(User getUser){
        user=getUser;
    }
    public static Boolean isThatMe(String username){
        return username.equals(user.getUserName());
    }
    public static String getToken(){
        return token;
    }
    public static boolean isTokenSet(){
        return token!=null;
    }
}

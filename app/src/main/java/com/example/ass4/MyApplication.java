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
        token= null;
        user = null;
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
    public static boolean isUserSet(){
        return user!=null;
    }
    public static User getUser(){
        return user;
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

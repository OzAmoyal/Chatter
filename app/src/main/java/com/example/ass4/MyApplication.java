package com.example.ass4;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.ass4.entities.User;
import com.example.ass4.repositories.ChatsRepository;
import com.google.gson.Gson;

public class MyApplication extends Application{
    public static Context context;
    public static String firebaseToken;
    public static String token;
    public static User user;
    private static SharedPreferences sharedPreferences;
    public static ChatsRepository chatsRepository;
    public static String serverUrl;
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        sharedPreferences = getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
        // Restore token and user from SharedPreferences
        token = sharedPreferences.getString("Token", null);
        String userJson = sharedPreferences.getString("User", null);
        if (userJson != null) {
            user = new Gson().fromJson(userJson, User.class);
        }
        String prefUrl=sharedPreferences.getString("ServerUrl",null);
        if(prefUrl!=null){
            serverUrl=prefUrl;
        }
        else{
            serverUrl=getResources().getString(R.string.BaseUrl);
        }
        System.out.println("ServerUrl: "+serverUrl);

        chatsRepository = new ChatsRepository();
        token= null;
        user = null;
    }
    public static ChatsRepository getChatRepository(){
        return chatsRepository;
    }
    public static void setServerUrl(String url){
        serverUrl=url;
    }

    public static String getServerUrl() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ServerUrl", serverUrl);
        editor.apply();
        return serverUrl;
    }

    public static Context getContext(){
        return context;
    }
    public static void setToken(String getToken){
        token=getToken;
        // Save token to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", getToken);
        editor.apply();
    }
    public static void setUser(User getUser){
        user=getUser;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("User", new Gson().toJson(getUser));
        editor.apply();
    }
    public static boolean isUserSet(){
        return user!=null;
    }
    public static User getUser(){
        return user;
    }
    public static String getFirebaseToken(){
        return firebaseToken;
    }
    public static void setFirebaseToken(String token){
        firebaseToken=token;
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

package com.example.ass4.api;

import com.example.ass4.MyApplication;
import com.example.ass4.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    public LoginAPI(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
    public Boolean getToken(String username, String password){
        //LatchCallback loginCallback = new LatchCallback(latch);
        CountDownLatch latch = new CountDownLatch(1);
        RequestGetTokenAPI requestGetTokenAPI = new RequestGetTokenAPI(username, password);
//        Gson gson= new Gson();
//        String json = gson.toJson(requestGetTokenAPI);
        Call<String> call = webServiceAPI.getToken(requestGetTokenAPI);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyApplication.setToken("Bearer "+response.body());
                    latch.countDown();
                }
                else{
                    latch.countDown();
                }
        }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return MyApplication.isTokenSet();
    }
    public Void sendFirebaseToken(){
        CountDownLatch latch = new CountDownLatch(1);
        RequestSendFirebaseTokenAPI requestSendFirebaseTokenAPI = new RequestSendFirebaseTokenAPI(MyApplication.getFirebaseToken());
        Call<Void> call = webServiceAPI.sendFirebaseToken(requestSendFirebaseTokenAPI,MyApplication.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                latch.countDown();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Void removeFirebaseToken(String token){
        Call<Void> call = webServiceAPI.removeFirebaseToken(token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

        return null;
    }
}

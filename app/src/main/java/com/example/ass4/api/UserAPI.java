package com.example.ass4.api;

import com.example.ass4.MyApplication;
import com.example.ass4.NewUser;
import com.example.ass4.R;
import com.example.ass4.entities.User;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getContext().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

        public boolean getUserDetails(String username){
            CountDownLatch latch = new CountDownLatch(1);
            Call<ResponseGetUserDetails> call = webServiceAPI.getUser(username, MyApplication.getToken());
            call.enqueue(new Callback<ResponseGetUserDetails>() {
                @Override
                public void onResponse(Call<ResponseGetUserDetails> call, Response<ResponseGetUserDetails> response) {
                    if (response.isSuccessful()) {
                        ResponseGetUserDetails responseGetUserDetails = response.body();
                        User user = new User(responseGetUserDetails.getUsername(), responseGetUserDetails.getProfilePic(), responseGetUserDetails.getDisplayName());
                        MyApplication.setUser(user);
                        latch.countDown();
                    }
                    else{
                        latch.countDown();
                    }
                }

                @Override
                public void onFailure(Call<ResponseGetUserDetails> call, Throwable t) {
                    latch.countDown();
                }
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return MyApplication.isUserSet();
        }

    public boolean signUpNewUser(String username,String password, String profilePic, String displayName) {
        NewUser user = new NewUser(username,profilePic,displayName,password);
        CountDownLatch latch = new CountDownLatch(1);
        Call<Void> call = webServiceAPI.createUser(user);
        final Boolean[] conflict = {false};
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200) {

                    latch.countDown();
                }
                else if(response.code()==409){
                    conflict[0] =true;
                    latch.countDown();
                }
                else {
                    latch.countDown();
                }
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
        return !conflict[0];
    }


    }



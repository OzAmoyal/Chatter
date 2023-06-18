package com.example.ass4.api;

import android.os.AsyncTask;

import com.example.ass4.MyApplication;
import com.example.ass4.R;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.User;

import java.io.IOException;
import java.util.List;

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

   /* private class GetUserDetailsTask extends AsyncTask<Void, Void, User> {
        private String username;

        public GetUserDetailsTask(String username) {
            this.username = username;
        }

        @Override
        protected User doInBackground(Void... voids) {
            try {
                System.out.println(MyApplication.getToken());
                retrofit2.Response<ResponseGetUserDetails> response = webServiceAPI.getUser(username, MyApplication.getToken()).execute();
                if (response.isSuccessful()) {
                    ResponseGetUserDetails responseGetUserDetails = response.body();
                    if (responseGetUserDetails != null) {
                        return new User(responseGetUserDetails.getUsername(), responseGetUserDetails.getProfilePic(), responseGetUserDetails.getDisplayName());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            System.out.println(user.getUserName());
            MyApplication.setUser(user);
        }
    }*/

        public boolean getUserDetails(String username) {
            Call<ResponseGetUserDetails> call = webServiceAPI.getUser(MyApplication.getToken(), username);
            call.enqueue(new Callback<ResponseGetUserDetails>() {
                @Override
                public void onResponse(Call<ResponseGetUserDetails> call, Response<ResponseGetUserDetails> response) {
                    if (response.isSuccessful()) {
                        ResponseGetUserDetails responseGetUserDetails = response.body();
                        User user = new User(responseGetUserDetails.getUsername(), responseGetUserDetails.getProfilePic(), responseGetUserDetails.getDisplayName());
                        MyApplication.setUser(user);
                    }
                }

                @Override
                public void onFailure(Call<ResponseGetUserDetails> call, Throwable t) {
                }
            });

            return MyApplication.isUserSet();
        }

}


package com.example.ass4.api;

import com.example.ass4.NewUser;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.Message;
import com.example.ass4.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
 @GET("Chats")
 Call<List<ResponseGetChatsAPI>> getChats(@Header("Authorization") String token);
 @GET("Chats/{id}")
 Call<ResponseGetChatByIDAPI> getChatById(@Path("id") String id,@Header("Authorization") String token);
 @GET("Chats/{id}/Messages")
 Call<List<Message>> getMessagesByChatId(@Path("id") String id,@Header("Authorization") String token);
 @POST("Chats/{id}/Messages")
 Call<Void> createMessage(@Path("id") String id, @Body Message message,@Header("Authorization") String token);
 @POST("Chats")
 Call<Void> createChat(@Body Chat chat,@Header("Authorization") String token);
 @DELETE("Chats/{id}")
 Call<Void> deleteChat(@Path("id") String id,@Header("Authorization") String token);
 @GET("tokens")
 Call<String> getToken();
 @GET("Users")
 Call<List<User>> getUsers(@Header("Authorization") String token);
 @POST("Users")
 Call<Void> createUser(@Body NewUser user);
 }

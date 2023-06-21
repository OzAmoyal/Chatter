package com.example.ass4.api;

import static java.lang.Thread.sleep;

import androidx.lifecycle.MutableLiveData;

import com.example.ass4.MyApplication;
import com.example.ass4.R;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.Message;
import com.example.ass4.entities.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsAPI {
//private MutableLiveData<List<Chat>> ChatListData;
//private PostDao dao;
Retrofit retrofit;
WebServiceAPI webServiceAPI;
Chat tempChat;
String errorMessage;
Message tempMessage;
List<Chat> tempChatList;
     public ChatsAPI() { //MutableLiveData<List<Chat>> postListData, PostDao dao
     /*
     this.postListData = postListData;
     this.dao = dao;
    */
      retrofit = new Retrofit.Builder()
      .baseUrl(MyApplication.getContext().getString(R.string.BaseUrl))
      .addConverterFactory(GsonConverterFactory.create())
      .build();
      webServiceAPI = retrofit.create(WebServiceAPI.class);
      }

    public List<Chat> getChats() {
        List<Chat> tempChatList = new ArrayList<>();
        Call<List<ResponseGetChatsAPI>> call = webServiceAPI.getChats(MyApplication.getToken());
        Response<List<ResponseGetChatsAPI>> response;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return tempChatList; // Return empty list if an error occurs
        }

        if (!response.isSuccessful()) {
            return tempChatList; // Return empty list if the response is not successful
        }

        List<ResponseGetChatsAPI> chats = response.body();
        if (chats != null) {
            for (ResponseGetChatsAPI chat : chats) {
                User user = new User(chat.getUser().getUsername(), chat.getUser().getProfilePic(), chat.getUser().getDisplayName());

                ResponseGetChatsAPI.Message last = chat.getLastMessage();

                if(last == null) {
                    Message lastMessage = null;
                    Chat chat1 = new Chat(chat.getId(), new ArrayList<>(), user, lastMessage);
                    tempChatList.add(chat1);
                    continue;
                }
                Message lastMessage = new Message(last.getId(), last.getContent(), getDate(last.getCreated()), true);
                Chat chat1 = new Chat(chat.getId(), new ArrayList<>(), user, lastMessage);
                tempChatList.add(chat1);
            }
        }

        return tempChatList;
    }
    public String createNewChat(String username){
        CountDownLatch latch = new CountDownLatch(1);
        RequestNewChatAPI requestNewChatAPI = new RequestNewChatAPI(username);
        Call<ResponseCreateChatAPI> call = webServiceAPI.createChat(requestNewChatAPI,MyApplication.getToken());
        call.enqueue(new Callback<ResponseCreateChatAPI>() {
            @Override
            public void onResponse(Call<ResponseCreateChatAPI> call, Response<ResponseCreateChatAPI> response) {
                if(response.code()==400) {
                    errorMessage = "User not found";
                    latch.countDown();
                }
                else{
                    ResponseCreateChatAPI chat = response.body();
                    errorMessage = chat.getId();
                    latch.countDown();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateChatAPI> call, Throwable t) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String errorMessage1 = errorMessage;
        errorMessage= null;
        return errorMessage1;
    }
    public Message sendMessage(String chatId, String message){
        CountDownLatch latch = new CountDownLatch(1);
        RequestSendMessageAPI requestSendMessageAPI = new RequestSendMessageAPI(message);
        Call<ResponseSendMessageAPI> call = webServiceAPI.createMessage(chatId,requestSendMessageAPI,MyApplication.getToken());
        call.enqueue(new Callback<ResponseSendMessageAPI>() {
            @Override
            public void onResponse(Call<ResponseSendMessageAPI> call, Response<ResponseSendMessageAPI> response) {
                if(!response.isSuccessful()){
                    latch.countDown();
                    return;
                }
                ResponseSendMessageAPI message = response.body();
                tempMessage = new Message(message.getId(), message.getContent(), getDate(message.getCreated()),MyApplication.isThatMe(message.getSenderUsername()));
                latch.countDown();
            }

            @Override
            public void onFailure(Call<ResponseSendMessageAPI> call, Throwable t) {
                latch.countDown();
            }
        });
        try {
            latch.await();
            if(tempMessage!=null){
                Message tempMessage1 = tempMessage;
                tempMessage=null;
                return tempMessage1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Chat getChatByID(String id) {
         CountDownLatch latch = new CountDownLatch(1);
      Call<ResponseGetChatByIDAPI> call = webServiceAPI.getChatById(id,MyApplication.getToken());
        call.enqueue(new Callback<ResponseGetChatByIDAPI>() {
                @Override
                public void onResponse(Call<ResponseGetChatByIDAPI> call, Response<ResponseGetChatByIDAPI> response) {
                if(!response.isSuccessful()){
                    latch.countDown();
                    return;
                }
                ResponseGetChatByIDAPI chat = response.body();
                List< Message> messages = new ArrayList<>();
                List<ResponseGetChatByIDAPI.Message> messages1 = chat.getMessages();
               for (ResponseGetChatByIDAPI.Message m : messages1){
                   Date date = getDate(m.getCreated());

                    Message message = new Message(m.getId(), m.getContent(), date,MyApplication.isThatMe(m.getSender().getUsername()));
                    messages.add(message);
                }
                ResponseGetChatByIDAPI.User otherUser = null;

                List<ResponseGetChatByIDAPI.User> users = chat.getUsers();
                for (ResponseGetChatByIDAPI.User user : users) {
                    if (!MyApplication.isThatMe(user.getUsername())) {
                        otherUser = user;
                        break; // Assuming you only want the first user that meets the condition
                    }
                }
                ResponseGetChatByIDAPI.Message last= chat.getLastMessage();
                if(last==null){
                    Message lastMessage = null;
                    User user = new User(otherUser.getUsername(), otherUser.getProfilePic(), otherUser.getDisplayName());
                    tempChat = new Chat(chat.getId(), messages,user,lastMessage);
                    latch.countDown();
                    return;
                }else {
                    Message lastMessage = new Message(last.getId(), last.getContent(), getDate(last.getCreated()), MyApplication.isThatMe(last.getSender().getUsername()));
                    User user = new User(otherUser.getUsername(), otherUser.getProfilePic(), otherUser.getDisplayName());
                    tempChat = new Chat(chat.getId(), messages, user, lastMessage);
                    latch.countDown();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetChatByIDAPI> call, Throwable t) {
                System.out.println("Failed to get posts");
                latch.countDown();
            }
        });
        try{
            latch.await();
        }catch (InterruptedException e) {
        //
        }
         return tempChat;
        }
        public static Date getDate(String dateString){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
                return date;
                // Use the parsed date object as needed
            } catch (ParseException e) {
                return null;
            }
        }
     }

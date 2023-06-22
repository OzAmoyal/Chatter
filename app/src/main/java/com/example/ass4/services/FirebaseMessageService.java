package com.example.ass4.services;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ass4.MyApplication;
import com.example.ass4.R;
import com.example.ass4.repositories.ChatsRepository;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        System.out.println("NEW_TOKEN"+token);
        MyApplication.setFirebaseToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String chatID = remoteMessage.getData().get("chatID");
        System.out.println("NEW_MESSAGE"+chatID);
        ChatsRepository chatsRepository= MyApplication.getChatRepository();
            chatsRepository.reload();
            if(chatsRepository.isChatLoaded(chatID)){
                chatsRepository.getChatByID(chatID);
            }
//
//        }
        //Log.d("NEW_MESSAGE",remoteMessage.getNotification().getBody());
        if (remoteMessage.getNotification() != null) {
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            try {
                notificationManager.notify(1, builder.build());
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }

    }
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1","My channel", importance);
            channel.setDescription("Demo chanel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}

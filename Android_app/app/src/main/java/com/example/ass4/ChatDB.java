package com.example.ass4;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ass4.entities.Chat;
@Database(entities = {Chat.class}, version = 1)
@TypeConverters(ChatConverter.class)


 public abstract class ChatDB extends RoomDatabase {
 public abstract ChatDao chatDao();
 }


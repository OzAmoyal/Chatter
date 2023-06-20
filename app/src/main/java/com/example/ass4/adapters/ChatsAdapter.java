package com.example.ass4.adapters;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.ass4.ChatActivity;
import com.example.ass4.R;
import com.example.ass4.entities.Chat;
import com.example.ass4.entities.Message;
import com.example.ass4.entities.User;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTime;
        private final TextView tvUsername;
        private final TextView tvLastMessage;
        private final ImageView ivPic;

        private ChatViewHolder(View itemView){
            super(itemView);
            tvTime = itemView.findViewById(R.id.time);
            tvUsername = itemView.findViewById(R.id.user_name);
            tvLastMessage = itemView.findViewById(R.id.last_massage);
            ivPic = itemView.findViewById(R.id.profile_image);
        }
    }

    private final LayoutInflater minflater;
    private List<Chat> chats;

    public ChatsAdapter(Context context) {
        minflater = LayoutInflater.from(context);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View itemView = minflater.inflate(R.layout.user_list_item, parent,false);
        return new ChatViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position){
        if(chats !=null) {
            final Chat current = chats.get(position);
            System.out.println(current.getLastMessage().getContent().toString());
            Message lastMessage= current.getLastMessage();
            if(lastMessage==null){
                holder.tvTime.setText("");
                holder.tvLastMessage.setText("");
            }else{
                holder.tvTime.setText(current.getLastMessage().getCreated().toLocaleString());
                holder.tvLastMessage.setText(current.getLastMessage().getContent());
            }
            holder.tvUsername.setText(current.getOtherUser().getDisplayName());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap profilePictureBitmap = current.getOtherUser().getPicture();
            User user = current.getOtherUser();
            //profilePictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            holder.ivPic.setImageBitmap(profilePictureBitmap);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("userName", current.getOtherUser().getDisplayName());
                    intent.putExtra("chatId", current.getId());
                    Bitmap profilePictureBitmap = current.getOtherUser().getPicture();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    profilePictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] profilePictureByteArray = byteArrayOutputStream.toByteArray();
                    intent.putExtra("profilePicture", profilePictureByteArray);
                    context.startActivity(intent);
                }
            });
        }
    }
    public void setChats(List<Chat> s){
        chats = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(chats !=null){
            return chats.size();
        }
        else{
            return 0;
        }
    }

    public List<Chat> getChats(){
        return chats;
    }

}

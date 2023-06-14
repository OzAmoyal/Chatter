package com.example.ass4.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.ass4.R;
import com.example.ass4.entities.Chat;

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

            holder.tvTime.setText(current.getLastMessage().getCreated().toString());
            holder.tvUsername.setText(current.getOtherUser().getUserName());
            holder.tvLastMessage.setText(current.getLastMessage().getContent());
            holder.ivPic.setImageResource(current.getOtherUser().getPictureId());
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

package com.example.ass4.adapters;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            //System.out.println(current.getLastMessage().getContent().toString());
            Message lastMessage= current.getLastMessage();
            if(lastMessage==null){
                holder.tvTime.setText("");
                holder.tvLastMessage.setText("");
            }else{
                holder.tvTime.setText(formatMessageDate(lastMessage.getCreated()));
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
                    int size = 160;
                    int quality = 80;
                    byte[] compressedByteArray = compressBitmap(profilePictureBitmap, size, quality);
                    intent.putExtra("profilePicture", compressedByteArray);
                    context.startActivity(intent);
                }
            });
        }
    }
    public void setChats(List<Chat> s){
        chats = s;
        if (chats!=null) {
            chats.sort((o1, o2) -> {
                if (o1.getLastMessage() == null)
                    return -1;
                if (o2.getLastMessage() == null)
                    return -1;
                return o2.getLastMessage().getCreated().compareTo(o1.getLastMessage().getCreated());
            });
        }
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
    public static String formatMessageDate(Date date) {
        Calendar currentDate = Calendar.getInstance();
        Calendar messageDate = Calendar.getInstance();
        messageDate.setTime(date);

        boolean isToday = messageDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                messageDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                messageDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());

        if (isToday) {
            return timeFormat.format(date);
        } else {
            return dateFormat.format(date);
        }
    }
    public static byte[] compressBitmap(Bitmap bitmap, int size, int quality) {

        try {
            int currentWidth = bitmap.getWidth();
            int currentHeight = bitmap.getHeight();

            // Calculate the scale factor to resize the bitmap
            float scaleFactor = Math.min((float) size / currentWidth, (float) size / currentHeight);

            // Calculate the new dimensions based on the scale factor
            int newWidth = Math.round(currentWidth * scaleFactor);
            int newHeight = Math.round(currentHeight * scaleFactor);
            // Resize the bitmap to the desired dimensions
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap,newWidth,newHeight, true);

            // Compress the resized bitmap to reduce file size
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

            // Get the compressed byte array
            return outputStream.toByteArray();
        } catch (Exception e) {
            Log.e("ImageUtils", "Error compressing bitmap: " + e.getMessage());
            return null;
        }
    }

}

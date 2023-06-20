package com.example.ass4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ass4.R;
import com.example.ass4.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;

    public MessageAdapter(Context ctx, List<Message> MessagesList) {
        super(ctx, R.layout.message_mine_item, MessagesList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message message = getItem(position);

        if (convertView == null || (Boolean)convertView.getTag() != message.isMine()) {
            if (message.isMine()) {
                convertView = inflater.inflate(R.layout.message_mine_item, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.message_yours_item, parent, false);
            }
            convertView.setTag(message.isMine());
        }

        TextView messageText = convertView.findViewById(R.id.tvMessageText);
        TextView messageTime = convertView.findViewById(R.id.tvMessageTime);
        messageText.setText(message.getContent());
        messageTime.setText(message.getCreated().toString());

        return convertView;
    }

}

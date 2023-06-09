package com.example.ass4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;

    public MessageAdapter(Context ctx, ArrayList<Message> MessagesArrayList) {
        super(ctx, R.layout.message_mine_item, MessagesArrayList);

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
        messageText.setText(message.getMessage());
        messageTime.setText(message.getTime());

        return convertView;
    }

}

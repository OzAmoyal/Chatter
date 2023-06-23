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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        messageTime.setText(formatMessageDate(message.getCreated()));

        return convertView;
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

}

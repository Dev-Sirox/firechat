package com.davidhoeck.firechat.interfaces;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidhoeck.firechat.R;
import com.davidhoeck.firechat.models.Message;

/**
 * Created by David Höck on 18.06.2016.
 */
public class ChatMessageHolder extends RecyclerView.ViewHolder{

    TextView userName;
    TextView userMessage;


    public ChatMessageHolder(View itemView) {
        super(itemView);

        userName = (TextView) itemView.findViewById(R.id.userName);

        userMessage = (TextView) itemView.findViewById(R.id.userMessage);

    }


    public void bindToMessage(Message message){
        userName.setText(message.parentUserName);
        userMessage.setText(message.message);
    }

}
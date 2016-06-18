package com.davidhoeck.firechat.interfaces;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidhoeck.firechat.R;
import com.davidhoeck.firechat.models.Message;
import com.squareup.picasso.Picasso;

/**
 * Created by David Höck on 18.06.2016.
 */
public class ChatMessageHolder extends RecyclerView.ViewHolder{

    TextView userName;
    TextView userMessage;
    ImageView userPhoto;

    public ChatMessageHolder(View itemView) {
        super(itemView);

        userName = (TextView) itemView.findViewById(R.id.userName);

        userMessage = (TextView) itemView.findViewById(R.id.userMessage);

        userPhoto = (ImageView) itemView.findViewById(R.id.userPhoto);

    }


    public void bindToMessage(Message message, Context context){
        userName.setText(message.parentUserName);
        userMessage.setText(message.message);
        Picasso.with(context).load(message.parentUserPhotoUrl).into(userPhoto);
    }

}
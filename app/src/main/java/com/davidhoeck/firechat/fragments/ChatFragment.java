package com.davidhoeck.firechat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.davidhoeck.firechat.R;
import com.davidhoeck.firechat.activities.MainActivity;
import com.davidhoeck.firechat.interfaces.ChatMessageHolder;
import com.davidhoeck.firechat.models.Message;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private String TAG = "Firechat";

    private View fView;
    private RecyclerView rvChat;
    private FirebaseRecyclerAdapter<Message,ChatMessageHolder> mMessageAdapter;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mMessagesRef;

    //UI
    private EditText mMessageInput;
    private Button mSendButton;

    //Auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public ChatFragment newInstance(){
        return new ChatFragment();
    }

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fView = inflater.inflate(R.layout.fragment_chat, container, false);

        mAuth = FirebaseAuth.getInstance();



        //Send inputs
        mSendButton = (Button) fView.findViewById(R.id.sendButton);
        mMessageInput = (EditText) fView.findViewById(R.id.messageInput);



        //Firebase Ref
        mDatabase = FirebaseDatabase.getInstance();
        mMessagesRef = mDatabase.getReference("messages");

        //Setup the recyclerView
        rvChat = (RecyclerView) fView.findViewById(R.id.rvChat);



        mMessageAdapter = new FirebaseRecyclerAdapter<Message, ChatMessageHolder>(
                Message.class,
                R.layout.message_card_view,
                ChatMessageHolder.class,
                mMessagesRef
        ) {
            @Override
            protected void populateViewHolder(ChatMessageHolder viewHolder, Message model, int position) {

                viewHolder.bindToMessage(model,getActivity());
            }
        };

        rvChat.setAdapter(mMessageAdapter);
        rvChat.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.scrollToPosition(llm.findLastVisibleItemPosition());
        rvChat.setLayoutManager(llm);


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser() != null){

                    String photoUrl = mAuth.getCurrentUser().getPhotoUrl().toString();
                    Message message = new Message(
                            mMessageInput.getText().toString(),
                            mAuth.getCurrentUser().getUid(),
                            mAuth.getCurrentUser().getDisplayName(),
                            photoUrl
                    );
                    mMessagesRef.push().setValue(message);
                    mMessageAdapter.notifyDataSetChanged();
                    llm.scrollToPosition(llm.findLastVisibleItemPosition() + 1);
                    mMessageInput.setText("");

                }
            }
        });
        return fView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMessageAdapter.cleanup();
    }
}

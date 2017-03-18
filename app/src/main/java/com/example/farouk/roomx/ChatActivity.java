package com.example.farouk.roomx;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.farouk.roomx.FireChatHelper.ExtraIntent;
import com.example.farouk.roomx.model.ChatMessage;
import com.example.farouk.roomx.model.UserMassage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends Activity {

    private static final String TAG = ChatActivity.class.getSimpleName();

    RecyclerView mChatRecyclerView;
     EditText mUserMessageChatText ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String mRecipientId;
    private String mCurrentUserId;
    private MessageChatAdapter messageChatAdapter;
    private DatabaseReference messageChatDatabase;
    private ChildEventListener messageChatListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mChatRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_chat);
        mUserMessageChatText =  (EditText)findViewById(R.id.edit_text_message);

        bindButterKnife();
        setDatabaseInstance();
        setUsersId();
        setChatRecyclerView();
    }

    private void bindButterKnife() {
        ButterKnife.bind(this);
    }
    private void setDatabaseInstance() {
        String chatRef = getIntent().getStringExtra(ExtraIntent.EXTRA_CHAT_REF);
        messageChatDatabase = FirebaseDatabase.getInstance().getReference().child(chatRef);
    }

    private void setUsersId() {
        mRecipientId = getIntent().getStringExtra(ExtraIntent.EXTRA_RECIPIENT_ID);
        mCurrentUserId = getIntent().getStringExtra(ExtraIntent.EXTRA_CURRENT_USER_ID);
    }

    private void setChatRecyclerView() {
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatRecyclerView.setHasFixedSize(true);
        messageChatAdapter = new MessageChatAdapter(new ArrayList<ChatMessage>());
        mChatRecyclerView.setAdapter(messageChatAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        messageChatListener = messageChatDatabase.limitToFirst(20).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {

                if(dataSnapshot.exists()){
                    ChatMessage newMessage = dataSnapshot.getValue(ChatMessage.class);
                    if(newMessage.getSender().equals(mCurrentUserId)){
                        newMessage.setRecipientOrSenderStatus(MessageChatAdapter.SENDER);
                    }else{
                        newMessage.setRecipientOrSenderStatus(MessageChatAdapter.RECIPIENT);
                    }
                    messageChatAdapter.refillAdapter(newMessage);
                    mChatRecyclerView.scrollToPosition(messageChatAdapter.getItemCount()-1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();

        if(messageChatListener != null) {
            messageChatDatabase.removeEventListener(messageChatListener);
        }
        messageChatAdapter.cleanUp();

    }

    @OnClick(R.id.btn_send_message)
    public void btnSendMsgListener(View sendButton){

        String senderMessage = mUserMessageChatText.getText().toString().trim();

        if(!senderMessage.isEmpty()){

            ChatMessage newMessage = new ChatMessage(senderMessage,mCurrentUserId,mRecipientId);
            messageChatDatabase.push().setValue(newMessage);

            mUserMessageChatText.setText("");
        }
    }



    public void setnewMassage_user(String userId , String rseverId){

/*
        UserMassage user = new  UserMassage(userId,rseverId);

        this.mDatabase.child("User_massage").child(userId).setValue(user);*/



    }

    }




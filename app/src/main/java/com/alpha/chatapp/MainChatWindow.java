package com.alpha.chatapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by praveen on 11-04-2016.
 */
public class MainChatWindow  extends ActionBarActivity {

    MessageDB messageDB;
    private EditText messageInput;
    private Button sendButton;
    private ChatArrayAdapter adp;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);
        Intent in = getIntent();

        messageInput = (EditText) findViewById(R.id.message_input);
        list = (ListView) findViewById(R.id.listview1);


        sendButton = (Button) findViewById(R.id.send_button);
        adp = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_people);
        messageDB = new MessageDB(this,null,null,1);
        messageDB.databaseToString();

        for (int i = 0; i< messageDB.message.size(); i++){
            ChatMessage chatDBMessage=new ChatMessage();
            chatDBMessage.setMessage(messageDB.message.get(i));
            chatDBMessage.setDate(messageDB.date.get(i));
            chatDBMessage.setMe(messageDB.isme.get(i) == 1);
            adp.add(chatDBMessage);
        }

        reciveChatMessage();

        messageInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(TextUtils.isEmpty(messageInput.getText().toString())){
                        return false;
                    }
                   else{
                        return sendChatMessage();
                    }
                }
                return false;
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(messageInput.getText().toString())){
                }
                else{
                    sendChatMessage();
                }

            }
        });
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                list.setSelection(adp.getCount()-1);

            }
        });
    }

    private void reciveChatMessage() {
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setId(122);
        chatMessage.setMessage("hi bro");
        chatMessage.setDate(java.text.DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage.setMe(false);
        adp.add(chatMessage);

        ChatMessage chatMessage2=new ChatMessage();
        chatMessage2.setId(122);
        chatMessage2.setMessage("what are you doing?");
        chatMessage2.setDate(java.text.DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage2.setMe(false);
        adp.add(chatMessage2);

    }

    private boolean sendChatMessage() {
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setId(122);
        chatMessage.setMessage(messageInput.getText().toString());
        chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
         chatMessage.setMe(true);
        adp.add(chatMessage);
        messageDB.addNewMessage(chatMessage);
        messageInput.setText("");

        return true;
    }

}


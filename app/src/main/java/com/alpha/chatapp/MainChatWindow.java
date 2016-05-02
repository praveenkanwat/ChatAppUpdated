package com.alpha.chatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by praveen on 11-04-2016.
 */
public class MainChatWindow  extends ActionBarActivity {


    private EditText messageInput;
    private Button sendButton;
    private ChatArrayAdapter adp;
    private ListView list;
    String HisUsrId,MineUsrId;
    String chatmsg;
    String jsonchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);
        messageInput = (EditText) findViewById(R.id.message_input);
        jsonchange="";
        list = (ListView) findViewById(R.id.listview1);
        Intent intent = getIntent();
        HisUsrId = intent.getStringExtra(Config.USR_ID);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.com_alpha_chatapp_settings), Context.MODE_PRIVATE);
        int uid = sharedPref.getInt(getString(R.string.uid), 0);
        MineUsrId=String.valueOf(uid);
        sendButton = (Button) findViewById(R.id.send_button);
        adp = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_people);



        reciveChatMessage();

        final Handler h = new Handler();
        final int delay = 10000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                //do something
                getMessages();
                h.postDelayed(this, delay);
            }
        }, delay);

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
        getMessages();
    }

    private boolean sendChatMessage() {
        chatmsg = messageInput.getText().toString();
        addMessage();
        getMessages();
        messageInput.setText("");
        return true;
    }
    private void getMessages(){

        class GetMessages extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals(jsonchange)){

                }
                else {
                    adp.clearList();
                    showMessages(s);
                }
                jsonchange=s;


            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParamMessages(Config.URL_RECV_MSG,MineUsrId,HisUsrId);
                return s;
            }
        }
        GetMessages ge = new GetMessages();
        ge.execute();
    }
    private void showMessages(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_MSG_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_MSG_ID);
                String sender = jo.getString(Config.TAG_MSG_SEND_ID);
                String recive= jo.getString(Config.TAG_RECV_MSG_ID);
                String message= jo.getString(Config.TAG_MSG);
                String samay= jo.getString(Config.TAG_TIME);
                String isme=jo.getString("isMine");


                ChatMessage chatMessage=new ChatMessage();
                chatMessage.setId(id);
                chatMessage.setMessage(message);
                chatMessage.setDate(samay);
                chatMessage.setMe(isme.equals("1"));
                adp.add(chatMessage);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void addMessage(){


        class AddMessage extends AsyncTask<Void,Void,String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_MSG_SEND_ID,MineUsrId);
                params.put(Config.KEY_RECV_MSG_ID,HisUsrId);
                params.put(Config.KEY_MSG,chatmsg);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_SEND_MSG, params);
                return res;
            }
        }

        AddMessage ae = new AddMessage();
        ae.execute();
    }


}


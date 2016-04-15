package com.alpha.chatapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen on 12-04-2016.
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatMessage>{

    private TextView chatText,datentime;
    private List<ChatMessage> MessageList=new ArrayList<ChatMessage>();
    private LinearLayout layout1,layout2;

    public ChatArrayAdapter(Context applicationContext, int chat_people) {
        super(applicationContext,chat_people);
    }

    public void add(ChatMessage object) {
    MessageList.add(object);
    super.add(object);
    }
    public int getCount(){
        return this.MessageList.size();

    }
    public  ChatMessage getItem(int index){
        return this.MessageList.get(index);
    }
    public View getView(int position, View ConvertView, ViewGroup parent){


        View v= ConvertView;
        if(v==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.chat_people,parent,false);
        }
    layout1=(LinearLayout)v.findViewById(R.id.message1);
        ChatMessage messageobj =getItem(position);
        chatText=(TextView)v.findViewById(R.id.SingleMessage);
        chatText.setText(messageobj.getMessage());
        chatText.setBackgroundResource(messageobj.getIsme() ? R.drawable.in_message_bg:R.drawable.out_message_bg);
        layout1.setGravity(messageobj.getIsme() ? Gravity.RIGHT:Gravity.LEFT);


         layout2=(LinearLayout)v.findViewById(R.id.message2);
        datentime=(TextView)v.findViewById(R.id.SingleMessage2);
        datentime.setText(messageobj.getDate());
    return v;
}
    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}

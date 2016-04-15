package com.alpha.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by praveen on 13-04-2016.
 */
public class MessageDB extends SQLiteOpenHelper{
    ChatArrayAdapter adp;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<Integer> isme = new ArrayList<>();

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME="message.db";
    private static final String TABLE_NAME="message";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_Message="message_content";
    private static final String COLUMN_DATE="date";
    private static final String COLUMN_ISME="who";

    public MessageDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE " + TABLE_NAME + "("+
                COLUMN_ID + " " + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_Message + " " + "TEXT," +
                COLUMN_DATE + " " + "TEXT," +
                COLUMN_ISME + " " + "INTEGER" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void addNewMessage(ChatMessage chatMessage){
        ContentValues values = new ContentValues();
        values.put(COLUMN_Message,chatMessage.getMessage());
        values.put(COLUMN_DATE,chatMessage.getDate());
        values.put(COLUMN_ISME,chatMessage.getIsme()?1:0);
        SQLiteDatabase db =getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
       public void databaseToString(){
         int i=0;
           ChatMessage chatMessage=new ChatMessage();

           SQLiteDatabase sqLiteDatabase = getWritableDatabase();
           String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1"; //Select every column, select every row.
           Cursor cursor = sqLiteDatabase.rawQuery(query, null); //Cursor point to a location in results.

              cursor.moveToFirst(); //Move to the first row in results.
               while (!cursor.isAfterLast()) {

               if (cursor.getString(cursor.getColumnIndex("message_content")) != null) {

                 message.add(cursor.getString(cursor.getColumnIndex("message_content")));
                   date.add(cursor.getString(cursor.getColumnIndex("date")));
                   isme.add(cursor.getInt(cursor.getColumnIndex("who")));
                   i++;
               }
               cursor.moveToNext();
               }

           cursor.close();

           sqLiteDatabase.close();

       }


}

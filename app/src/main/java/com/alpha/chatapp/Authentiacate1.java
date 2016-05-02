package com.alpha.chatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Authentiacate1 extends ActionBarActivity {


    private EditText editTextName;
    private EditText editTextMob;


    private Button buttonAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentiacate1);


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextMob = (EditText) findViewById(R.id.editTextMobile);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPeople();
            }
        });

    }



    private void addPeople(){

        final String name = editTextName.getText().toString().trim();
        final String mob = editTextMob.getText().toString().trim();

        class AddPeople extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Authentiacate1.this,"Starting...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Authentiacate1.this,s,Toast.LENGTH_LONG).show();
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.com_alpha_chatapp_settings), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                try {
                    JSONObject j = new JSONObject(s);
                    editor.putInt(getString(R.string.uid), j.getInt("uid"));
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent i=new Intent(Authentiacate1.this,ListOfChat.class);
                startActivity(i);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_NAME,name);
                params.put(Config.KEY_MOB,mob);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddPeople ae = new AddPeople();
        ae.execute();
    }
}

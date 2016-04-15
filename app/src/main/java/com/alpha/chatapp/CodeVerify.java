package com.alpha.chatapp;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by praveen on 15-04-2016.
 */
public class CodeVerify extends ActionBarActivity{
    Button codevf;
    EditText etcode;
    Intent i;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_code);
        codevf=(Button)findViewById(R.id.verifybt);
        etcode=(EditText)findViewById(R.id.editTextcode);
        codevf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    i=new Intent(CodeVerify.this,MainChatWindow.class);
                    etcode.setText("");
                    startActivity(i);
                    etcode.setText("");

            }
        });
    }
}

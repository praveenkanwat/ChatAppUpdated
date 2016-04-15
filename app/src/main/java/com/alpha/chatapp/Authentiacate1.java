package com.alpha.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Authentiacate1 extends ActionBarActivity{
    Button mobileno;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentiacate1);
        mobileno=(Button)findViewById(R.id.button);
        mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(Authentiacate1.this,CodeVerify.class);
                startActivity(i);

            }
        });
    }
}

package com.example.medapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class landingpage extends AppCompatActivity {
    private Button mLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);
        Button mLogout =(Button)findViewById(R.id.logout);
        mLogout.setOnClickListener(new View.OnClickListener(){ //action for logout button
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Log.i("clicks","You clicked logout in home");
                Intent i = new Intent(
                        landingpage.this, //Directs to Login page
                        login.class
                );
                startActivity(i);
                finish();
            }
        });
    }

}
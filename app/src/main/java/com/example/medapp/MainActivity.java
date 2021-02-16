package com.example.medapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){ // If already logged in, directs to Home page
            Intent i = new Intent(
                    MainActivity.this,
                    landingpage.class
            );
            startActivity(i);
            finish();
        }
        Button btn_sign =(Button)findViewById(R.id.sign);
        Button btn_login =(Button)findViewById(R.id.login);
        btn_sign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ // On cicking signup button, directs to Sign up page
                Log.i("clicks","You clicked");
                Intent i = new Intent(
                        MainActivity.this,
                        signup.class
                );
                startActivity(i);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener(){ // On clicking login button, directs to Login page
            @Override
            public void onClick(View v){
                Log.i("clicks","You clicked login");
                Intent i = new Intent(
                        MainActivity.this,
                        login.class
                );
                startActivity(i);
            }
        });
    }
}
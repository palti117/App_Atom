package com.example.medapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText mEmail,mPassword;
    private Button land;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = (EditText)findViewById(R.id.editTextTextEmailAddress);
        mPassword = (EditText)findViewById(R.id.pwd);
        fAuth= FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){  // if already logged in, directs to home page
            Intent i = new Intent(
                    login.this,
                    landingpage.class
            );
            startActivity(i);
            finish();
        }
        Button btn_sign =(Button)findViewById(R.id.button_sign);
        land =(Button)findViewById(R.id.login_submit);
        btn_sign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i("clicks","You clicked signup in login");
                Intent i = new Intent(
                        login.this,
                        signup.class
                );
                startActivity(i);
            }
        });
        land.setOnClickListener(new View.OnClickListener(){ // On clicking Login button, directs to home page if credentials are true
            @Override
            public void onClick(View v){
                String email=mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){  // Shows error that email is empty
                    mEmail.setError("Email is required!");
                    mEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ // checks whether email is valid
                    mEmail.setError("Please provide a valid Email");
                    mEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){ // Shows error that Password is empty
                    mPassword.setError("Password is required!");
                    mPassword.requestFocus();
                    return;
                }
                if(password.length()<6){ // Checks whether password is greater than 6 characters
                    mPassword.setError("Password must be greater than 6 characters");
                    mPassword.requestFocus();
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //Signing in
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { // If email and password match
                        if(task.isSuccessful()){
                            Toast.makeText(login.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),landingpage.class));
                        }
                        else{//If it is not successful
                            Toast.makeText(login.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
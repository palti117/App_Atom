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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    private EditText mEmail,mPassword;
    private Button msignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){ // If already logged in, directs to home page
            Intent i = new Intent(
                    signup.this,
                    landingpage.class
            );
            startActivity(i);
            finish();
        }
        Button btn_login =(Button)findViewById(R.id.login_exis);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ // Directs to login page on clicked
                Log.i("clicks","You clicked");
                Intent i = new Intent(
                        signup.this,
                        login.class
                );
                startActivity(i);
            }
        });
        msignup = (Button)findViewById(R.id.sign_submit);
        mEmail = (EditText)findViewById(R.id.editTextTextEmailAddress);
        mPassword = (EditText)findViewById(R.id.pwd);

        msignup.setOnClickListener(new View.OnClickListener() { // On clicking signup after entering fields
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){  // Shows error that email is empty
                    mEmail.setError("Email is required!");
                    mEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ // Checks whether the email is valid
                    mEmail.setError("Please provide a valid Email");
                    mEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){ // Shows error that password is empty
                    mPassword.setError("Password is required!");
                    mPassword.requestFocus();
                    return;
                }
                if(password.length()<6){ // Checks whether the password is greater than 6 characters
                    mPassword.setError("Password must be greater than 6 characters");
                    mPassword.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { // Creating User in Firestore
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ // If user is not there already, succesfully registers
                            //User user = new User(email);
                            Toast.makeText(signup.this,"User created successfully",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(
                                    signup.this,
                                    landingpage.class
                            );
                            startActivity(i);
                        }
                        else{ // If task is not successful
                            Toast.makeText(signup.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
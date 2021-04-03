package com.example.semester_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button gohomejava,logintosignupjava;
    EditText emailloginjava,passwordloginjava;
    private ProgressBar bar;
    //create auth var
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find button
        gohomejava = findViewById(R.id.loginbutton);
        logintosignupjava = findViewById(R.id.logintosignup);

        //find edtitext
        emailloginjava = findViewById(R.id.loginemail);
        passwordloginjava = findViewById(R.id.loginpassword);

        bar = findViewById(R.id.proBarLogin);

        //init auth
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,nav.class));
            finish();
        }
        //login to home
        gohomejava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailLjava = emailloginjava.getText().toString();
                String passwordLjava = passwordloginjava.getText().toString();
                if (TextUtils.isEmpty(emailLjava)||TextUtils.isEmpty(passwordLjava)){
                    Toast.makeText(MainActivity.this,"fill details",Toast.LENGTH_SHORT).show();
                }
                else {
                    bar.setVisibility(View.VISIBLE);
                    loginAuth(emailLjava,passwordLjava);
                }
            }
        });


        //login to regitration
        logintosignupjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, registrationpage.class));
                finish();
            }
        });
    }

    private void loginAuth(String emailLjava1, String passwordLjava1) {
        mAuth.signInWithEmailAndPassword(emailLjava1,passwordLjava1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Authentication success..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, nav.class));
                bar.setVisibility(View.INVISIBLE);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
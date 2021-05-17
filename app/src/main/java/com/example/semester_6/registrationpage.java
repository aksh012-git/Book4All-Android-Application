package com.example.semester_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registrationpage extends AppCompatActivity {
    private ImageView gohomesigupjava;
    EditText namesignupjava,emailsignupjava,passwordsignupjava,conformpasswordsignupjava,phonesignupjava;
    private ProgressBar bar;
    //create auth var
    private FirebaseAuth mAuth;
    private TextView signuptologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);

        //find edit text
        namesignupjava = findViewById(R.id.signupname);
        emailsignupjava = findViewById(R.id.signupemail);
        passwordsignupjava = findViewById(R.id.signuppassword);
        conformpasswordsignupjava = findViewById(R.id.signupconformpassword);
        phonesignupjava = findViewById(R.id.signupPhone);
        bar = findViewById(R.id.proBarRegistration);

        //init auth
        mAuth = FirebaseAuth.getInstance();

        //find button
        gohomesigupjava = findViewById(R.id.signupbutton);
        signuptologin = findViewById(R.id.signuptologin);

        //fach data from edtitext


        //signup to home
        gohomesigupjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailsignupjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                passwordsignupjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                conformpasswordsignupjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                phonesignupjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                namesignupjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                String emailSjava = emailsignupjava.getText().toString();
                String passwordSjava = passwordsignupjava.getText().toString().trim();
                String conformpasswordSjava = conformpasswordsignupjava.getText().toString().trim();
                String phoneSjava = phonesignupjava.getText().toString().trim();
                String nameSjava = namesignupjava.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (emailsignupjava.getText().toString().trim().isEmpty()||passwordsignupjava.getText().toString().trim().isEmpty()||
                        conformpasswordsignupjava.getText().toString().trim().isEmpty()||phonesignupjava.getText().toString().trim().isEmpty()||
                        namesignupjava.getText().toString().trim().isEmpty()){
                    Toast.makeText(registrationpage.this,"fill all details properly",Toast.LENGTH_SHORT).show();
                }
                else if(!emailsignupjava.getText().toString().trim().matches(emailPattern)){
                    Toast.makeText(registrationpage.this,"Enter valid email!!",Toast.LENGTH_SHORT).show();
                }
                else if(passwordsignupjava.getText().toString().trim().length()<6 ||conformpasswordsignupjava.getText().toString().trim().length()<6){
                    Toast.makeText(registrationpage.this,"password to short",Toast.LENGTH_SHORT).show();
                }
                else if(!passwordSjava.equals(conformpasswordSjava)){
                    Toast.makeText(registrationpage.this,"password doesn't match with confirm password",Toast.LENGTH_SHORT).show();
                }
                else {
                        bar.setVisibility(View.VISIBLE);
                        registrUser(emailSjava,passwordSjava,nameSjava,phoneSjava);
                }
            }
        });

        //signup to login page
        signuptologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registrationpage.this, loginactivity.class));
                finish();
            }
        });
    }
    
    //registration methods
    public void registrUser(String emailSjava, String passwordSjava, String nameSjava, String phoneSjava ){
        mAuth.createUserWithEmailAndPassword(emailSjava,passwordSjava)
                .addOnCompleteListener(registrationpage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(registrationpage.this, "mail has been sent to your account: "+emailSjava, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            HashMap<String , Object> map = new HashMap<>();
                            map.put("email",emailSjava);
                            map.put("name",nameSjava);
                            map.put("phone",phoneSjava);
                            map.put("password",passwordSjava);
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String myUID = currentUser.getUid();
                            firebaseDatabase.getReference("books4All").child("userData").child(myUID).updateChildren(map);
                            Intent i = new Intent(registrationpage.this, loginactivity.class);
                            i.putExtra("flag",true);
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(i);
                            finish();
                            bar.setVisibility(View.INVISIBLE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(registrationpage.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}
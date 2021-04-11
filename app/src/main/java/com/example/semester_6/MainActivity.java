package com.example.semester_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailloginjava,passwordloginjava;
    private ProgressBar bar;
    //create auth var
    private FirebaseAuth mAuth;
    private TextView logintosignupjava;
    ImageView gohomejava;
    private TextView verifymessage,verifiyButtonText;
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

        verifymessage = findViewById(R.id.verifymessage);
        verifiyButtonText = findViewById(R.id.verifiybuttonText);
        Intent i = new Intent(getIntent());
        boolean xx = i.getBooleanExtra("flag",false);
        if (xx==true){
            verifymessage.setVisibility(View.VISIBLE);
            verifiyButtonText.setVisibility(View.VISIBLE);
        }
        else {
            verifymessage.setVisibility(View.INVISIBLE);
            verifiyButtonText.setVisibility(View.INVISIBLE);
        }

        if(mAuth.getCurrentUser()!=null){
            if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(MainActivity.this, nav.class));
                finish();
            }else {
                Toast.makeText(MainActivity.this, "User Is not Verifyd!!!", Toast.LENGTH_SHORT).show();
            }
        }

        verifiyButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "mail has been sent to your account", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //login to home
        gohomejava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for keybord Hide
                emailloginjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                passwordloginjava.onEditorAction(EditorInfo.IME_ACTION_DONE);

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
        mAuth.signInWithEmailAndPassword(emailLjava1,passwordLjava1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                        Toast.makeText(MainActivity.this, "Authentication success..", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, nav.class));
                        bar.setVisibility(View.INVISIBLE);
                        finish();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "User Is not Verify!!!", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    bar.setVisibility(View.INVISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("-->>",e.getMessage());
                bar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
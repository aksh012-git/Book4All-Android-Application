package com.example.semester_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

public class loginactivity extends AppCompatActivity {
    EditText emailloginjava,passwordloginjava;
    private ProgressBar bar;
    //create auth var
    private FirebaseAuth mAuth;
    private TextView logintosignupjava,forgotpassjava;
    ImageView gohomejava;
    private TextView verifymessage,verifiyButtonText,verifiytext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginxml);

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
        verifiytext = findViewById(R.id.verifiytext);

        forgotpassjava = findViewById(R.id.forgotpass);

        Intent i = new Intent(getIntent());
        boolean xx = i.getBooleanExtra("flag",false);
        if (xx==true){
            verifymessage.setVisibility(View.VISIBLE);
            verifiyButtonText.setVisibility(View.VISIBLE);
            verifiytext.setVisibility(View.VISIBLE);
        }
        else {
            verifymessage.setVisibility(View.GONE);
            verifiyButtonText.setVisibility(View.GONE);
            verifiytext.setVisibility(View.GONE);
        }

        if(mAuth.getCurrentUser()!=null){
            if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(loginactivity.this, nav.class));
                finish();
            }else {
                Toast.makeText(loginactivity.this, "User Is not Verifyd!!!", Toast.LENGTH_SHORT).show();
                verifymessage.setVisibility(View.VISIBLE);
                verifiyButtonText.setVisibility(View.VISIBLE);
                verifiytext.setVisibility(View.VISIBLE);
            }
        }

        forgotpassjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(loginactivity.this);
                LayoutInflater inflater = loginactivity.this.getLayoutInflater();
                View view1 = inflater.inflate(R.layout.forgotpassword,null);
                EditText editmail = view1.findViewById(R.id.editemail);
                editmail.setText(emailloginjava.getText().toString());
                builder.setView(view1).setPositiveButton("reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editmail.getText().toString().isEmpty()){
                            Toast.makeText(loginactivity.this,"Please enter email", Toast.LENGTH_LONG).show();
                        }
                        else {
                            mAuth.sendPasswordResetEmail(editmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(loginactivity.this, "Password reset link sent to your email account", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(loginactivity.this, "Somthin went wrong! " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        verifiyButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(loginactivity.this, "mail has been sent to your account", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(loginactivity.this,"fill details",Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(loginactivity.this, registrationpage.class));
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
                        Toast.makeText(loginactivity.this, "Authentication success..", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(loginactivity.this, nav.class));
                        bar.setVisibility(View.INVISIBLE);
                        verifymessage.setVisibility(View.GONE);
                        verifiyButtonText.setVisibility(View.GONE);
                        verifiytext.setVisibility(View.GONE);
                        finish();
                    }
                    else {
                        Toast.makeText(loginactivity.this, "User Is not Verify!!!", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.INVISIBLE);
                        verifymessage.setVisibility(View.VISIBLE);
                        verifiyButtonText.setVisibility(View.VISIBLE);
                        verifiytext.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(loginactivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    bar.setVisibility(View.INVISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginactivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                bar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
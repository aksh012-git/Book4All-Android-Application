package com.example.semester_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static int Splash_screen = 5000;
    Animation topanim,bottomanim,leftanim,rightanim;
    TextView bottomtxt,toptxt,lefttxt,righttxt;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity);

        bottomtxt = findViewById(R.id.bottomtxt);
        toptxt = findViewById(R.id.toptxt);
        lefttxt = findViewById(R.id.lefttxt);
        righttxt = findViewById(R.id.righttxt);

        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        leftanim = AnimationUtils.loadAnimation(this,R.anim.left_anim);
        rightanim = AnimationUtils.loadAnimation(this,R.anim.right_anim);

        toptxt.setAnimation(topanim);
        bottomtxt.setAnimation(bottomanim);
        lefttxt.setAnimation(leftanim);
        righttxt.setAnimation(rightanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, loginactivity.class));
                finish();
            }
        },Splash_screen);
    }
}
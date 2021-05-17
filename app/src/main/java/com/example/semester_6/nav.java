package com.example.semester_6;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class nav extends AppCompatActivity {
    Button sellbuttonjava,logouthomejava;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView myEmailNav;
    private FirebaseAuth mAuth;
    private String profileimg;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private ImageView profile_imagedrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);


        mAuth = FirebaseAuth.getInstance();
        //get currentUser
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myUIDnav = currentUser.getUid();
        DatabaseReference reference= firebaseDatabase.getReference("books4All").child("userData").child(myUIDnav);

        String EmailHeader = currentUser.getEmail();
        if(mAuth.getCurrentUser()==null){
            startActivity(new Intent(nav.this,MainActivity.class));
            finish();
        }






        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //----------Nav Header Emai Set--------------------------
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView t1 = header.findViewById(R.id.navEmailText);
        t1.setText(EmailHeader);
        profile_imagedrawer = header.findViewById(R.id.profile_imagedrawer);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileimg = snapshot.child("profileurl").getValue(String.class);
                if(profileimg!=null) {
                    Glide.with(profile_imagedrawer.getContext()).load(profileimg).into(profile_imagedrawer);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        profile_imagedrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        profileimg = snapshot.child("profileurl").getValue(String.class);
                        if(profileimg!=null) {
                            Glide.with(profile_imagedrawer.getContext()).load(profileimg).into(profile_imagedrawer);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



        //------------------------------------------
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.history,R.id.aboutapp)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //logout button
        logouthomejava = findViewById(R.id.logoutHome);
        View fragment = findViewById(R.id.nav_gallery);
        logouthomejava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(nav.this);
                builder.setTitle("Tap to Logout");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(nav.this, loginactivity.class));
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.nav, menu);
        menu.findItem(R.id.my_cart).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(nav.this,myCart.class));
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
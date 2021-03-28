package com.example.semester_6.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.semester_6.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class itemDetails extends AppCompatActivity {
    private TextView bookNameFullDetailsjava,bookAuthorFullDetailsjava,bookTypeFullDetailsjava,userNameJava,userEmailJava,userPhoneJava,
            bookRentingFullDetailsJava,bookSellingFullDetailsJava,bookAdressFullDetailsJava,bookZipcodeFullDetailsJava;
    private Intent intent;
    ImageView bookimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        bookAuthorFullDetailsjava = findViewById(R.id.bookAuthorFullDetails);
        bookNameFullDetailsjava = findViewById(R.id.bookNameFullDetails);
        bookTypeFullDetailsjava = findViewById(R.id.bookTypeFullDetails);
        bookRentingFullDetailsJava = findViewById(R.id.bookRentingFullDetails);
        bookSellingFullDetailsJava = findViewById(R.id.bookSellingFullDetails);
        bookAdressFullDetailsJava = findViewById(R.id.bookAdressFullDetails);
        bookZipcodeFullDetailsJava = findViewById(R.id.bookZipcodeFullDetails);
        userNameJava = findViewById(R.id.userName);
        userEmailJava = findViewById(R.id.userEmail);
        userPhoneJava = findViewById(R.id.userPhone);
        bookimg = findViewById(R.id.bookImgfullDetails);

        intent = new Intent(getIntent());
        String mykey = intent.getStringExtra("key");
        String MyUID = intent.getStringExtra("myUID");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("booksDetails").child(mykey);
       reference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){
                   String name = snapshot.child("bookname").getValue(String.class);
                   String bookauth = snapshot.child("authorname").getValue(String.class);
                   String booktype = snapshot.child("booktype").getValue(String.class);
                   String bookRentPrice = snapshot.child("rentingprice").getValue(String.class);
                   String bookSellPrice = snapshot.child("sellingprice").getValue(String.class);
                   String bookAdrress = snapshot.child("address").getValue(String.class);
                   String bookZip = snapshot.child("zipcode").getValue(String.class);
                   String bookimgx = snapshot.child("imgUrl").getValue(String.class);

                   bookNameFullDetailsjava.setText(name);
                   bookAuthorFullDetailsjava.setText(bookauth);
                   bookTypeFullDetailsjava.setText(booktype);
                   bookRentingFullDetailsJava.setText(bookRentPrice);
                   bookSellingFullDetailsJava.setText(bookSellPrice);
                   bookAdressFullDetailsJava.setText(bookAdrress);
                   bookZipcodeFullDetailsJava.setText(bookZip);
                   Glide.with(bookimg.getContext()).load(bookimgx).into(bookimg);
               }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        DatabaseReference reference2 =  firebaseDatabase.getReference("books4All").child("userData").child(MyUID);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userName = snapshot.child("name").getValue(String.class);
                    String userEmail = snapshot.child("email").getValue(String.class);
                    String userPhone = snapshot.child("phone").getValue(String.class);
                    userNameJava.setText(userName);
                    userEmailJava.setText(userEmail);
                    userPhoneJava.setText(userPhone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

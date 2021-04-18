package com.example.semester_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class itemdetails_mysold extends AppCompatActivity {
    private TextView bookNameFullDetailsjava,bookAuthorFullDetailsjava,bookTypeFullDetailsjava,userNameJava,userEmailJava,userPhoneJava,
            bookRentingFullDetailsJava,bookSellingFullDetailsJava,bookAdressFullDetailsJava,bookZipcodeFullDetailsJava,renttimeFullDetailsJava;
    private Intent intent;
    private ImageView bookimg;
    private   String userPhone;
    private String mykeySold,MyUIDSold,myUIDForUserSold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails_mysold);
        bookAuthorFullDetailsjava = findViewById(R.id.bookAuthorFullDetailsSold);
        bookNameFullDetailsjava = findViewById(R.id.bookNameFullDetailsSold);
        bookTypeFullDetailsjava = findViewById(R.id.bookTypeFullDetailsSold);
        bookRentingFullDetailsJava = findViewById(R.id.bookRentingFullDetailsSold);
        bookSellingFullDetailsJava = findViewById(R.id.bookSellingFullDetailsSold);
        bookAdressFullDetailsJava = findViewById(R.id.bookAdressFullDetailsSold);
        bookZipcodeFullDetailsJava = findViewById(R.id.bookZipcodeFullDetailsSold);
        renttimeFullDetailsJava = findViewById(R.id.renttimeFullDetailsSold);
        userNameJava = findViewById(R.id.userNameSold);
        userEmailJava = findViewById(R.id.userEmailSold);
        userPhoneJava = findViewById(R.id.userPhoneSold);
        bookimg = findViewById(R.id.bookImgfullDetailsSold);

        intent = new Intent(getIntent());
        mykeySold = intent.getStringExtra("keyForSold");
        MyUIDSold = intent.getStringExtra("myUIDForSold");
        myUIDForUserSold = intent.getStringExtra("myUIDForUserForSold");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("booksDetails").child(mykeySold);

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
                    String retntime = snapshot.child("renttime").getValue(String.class);

                    String y = "₹ " + bookSellPrice;
                    String x = "₹ " + bookRentPrice;

                    renttimeFullDetailsJava.setText(retntime);
                    bookNameFullDetailsjava.setText(name);
                    bookAuthorFullDetailsjava.setText("Author: "+bookauth);
                    bookTypeFullDetailsjava.setText("Book Type: "+booktype);
                    bookRentingFullDetailsJava.setText("Renting Price: " + x);
                    bookSellingFullDetailsJava.setText("Selling Price :" + y);
                    bookZipcodeFullDetailsJava.setText(bookAdrress +","+ bookZip);
                    Glide.with(bookimg.getContext()).load(bookimgx).into(bookimg);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(itemdetails_mysold.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        });
        DatabaseReference reference2 =  firebaseDatabase.getReference("books4All").child("userData").child(myUIDForUserSold);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userName = snapshot.child("name").getValue(String.class);
                    String userEmail = snapshot.child("email").getValue(String.class);
                    userPhone = snapshot.child("phone").getValue(String.class);
                    userNameJava.setText(userName);
                    userEmailJava.setText(userEmail);
                    userPhoneJava.setText(userPhone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(itemdetails_mysold.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
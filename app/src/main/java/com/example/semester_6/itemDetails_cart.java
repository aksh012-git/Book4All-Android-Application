package com.example.semester_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class itemDetails_cart extends AppCompatActivity {
    private TextView bookNameFullDetailsjava,bookAuthorFullDetailsjava,bookTypeFullDetailsjava,userNameJava,userEmailJava,userPhoneJava,
            bookRentingFullDetailsJava,bookSellingFullDetailsJava,bookAdressFullDetailsJava,bookZipcodeFullDetailsJava;
    private Intent intent;
    private ImageView bookimg;
    private Button chatBtn;
    private   String userPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_cart);

        bookAuthorFullDetailsjava = findViewById(R.id.bookAuthorFullDetailsCart);
        bookNameFullDetailsjava = findViewById(R.id.bookNameFullDetailsCart);
        bookTypeFullDetailsjava = findViewById(R.id.bookTypeFullDetailsCart);
        bookRentingFullDetailsJava = findViewById(R.id.bookRentingFullDetailsCart);
        bookSellingFullDetailsJava = findViewById(R.id.bookSellingFullDetailsCart);
        bookAdressFullDetailsJava = findViewById(R.id.bookAdressFullDetailsCart);
        bookZipcodeFullDetailsJava = findViewById(R.id.bookZipcodeFullDetailsCart);
        userNameJava = findViewById(R.id.userNameCart);
        userEmailJava = findViewById(R.id.userEmailCart);
        userPhoneJava = findViewById(R.id.userPhoneCart);
        bookimg = findViewById(R.id.bookImgfullDetailsCart);

        chatBtn = findViewById(R.id.chatWpCart);

        intent = new Intent(getIntent());
        String mykeyCart = intent.getStringExtra("key");
        String MyUIDCart = intent.getStringExtra("myUID");
        String myUIDForUserCart = intent.getStringExtra("myUIDForUser");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("Cart").child(MyUIDCart).child(mykeyCart);
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
                Toast.makeText(itemDetails_cart.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        });
        DatabaseReference reference2 =  firebaseDatabase.getReference("books4All").child("userData").child(myUIDForUserCart);
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
                Toast.makeText(itemDetails_cart.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean insalled = appInstallorNot("com.whatsapp");
                if (insalled){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://api.WhatsApp.com/send?phone="+"+91"+userPhone));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(itemDetails_cart.this, "whatsapp not!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean appInstallorNot(String s) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        packageManager.getPackageArchiveInfo(s, PackageManager.GET_ACTIVITIES);
        app_installed  = true;
        return app_installed;
    }
}
